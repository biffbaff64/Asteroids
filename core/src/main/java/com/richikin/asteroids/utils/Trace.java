package com.richikin.asteroids.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.richikin.asteroids.config.Stats;
import com.richikin.asteroids.config.SystemMeters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Trace
{
    private static final String debugTag = "Debug";
    private static final String infoTag  = "Info";
    private static final String errorTag = "ERROR";

    private static File    logFile;
    private static boolean isWriteFileEnabled;

    /**
     * Write a debug string to logcat or console.
     * The string can contain format options.
     *
     * @param formatString The string, or format string, to display.
     * @param args         Optional extra argumnts for use in format strings.
     */
    public static void dbg( String formatString, Object... args )
    {
        if ( Gdx.app.getLogLevel() == Application.LOG_DEBUG )
        {
            String str = createMessage( formatString, new Throwable().getStackTrace(), args );

            Gdx.app.debug( debugTag, str );

            writeToFile( str );
        }
    }

    /**
     * Writes debug information without a string message.
     * Useful for just writing out data.
     *
     * @param args Optional extra argumnts for use in format strings.
     */
    public static void dbg( Object... args )
    {
        if ( Gdx.app.getLogLevel() == Application.LOG_DEBUG )
        {
            String str = createMessage( null, new Throwable().getStackTrace(), args );

            Gdx.app.debug( debugTag, str );

            writeToFile( str );
        }
    }

    /**
     * Writes a Debug message, but adds a divider line before
     * and after the message.
     *
     * @param formatString The string to write.
     * @param args         Optional extra argumnts for use in format strings.
     */
    public static void boxedDbg( String formatString, Object... args )
    {
        if ( Gdx.app.getLogLevel() == Application.LOG_DEBUG )
        {
            divider();

            String str = createMessage( formatString, new Throwable().getStackTrace(), args );

            Gdx.app.debug( debugTag, str );

            writeToFile( str );

            divider();
        }
    }

    /**
     * Write an error string to logcat or console.
     *
     * @param formatString The string to write.
     * @param args         Optional extra argumnts for use in format strings.
     */
    public static void err( String formatString, Object... args )
    {
        if ( Gdx.app.getLogLevel() == Application.LOG_ERROR )
        {
            String str = createMessage( formatString, new Throwable().getStackTrace(), args );

            Gdx.app.debug( errorTag, str );

            writeToFile( str );
        }
    }

    /**
     * Write a messagew to logcat or console if the supplied condition is TRUE.
     *
     * @param condition    The condition to evaluate.
     * @param formatString The string to write.
     * @param args         Optional extra argumnts for use in format strings.
     */
    public static void _assert( boolean condition, String formatString, Object... args )
    {
        if ( condition )
        {
            String str = createMessage( formatString, new Throwable().getStackTrace(), args );

            Gdx.app.debug( errorTag, str );

            writeToFile( str );
        }
    }

    /**
     * Writes a debug message consisting solely of the following:-
     * - Current time and date.
     * - Calling Class/method/line number information.
     */
    public static void checkPoint()
    {
        if ( Gdx.app.getLogLevel() == Application.LOG_DEBUG )
        {
            StringBuilder sb = new StringBuilder();

            sb.append( getTimeStampInfo() );
            sb.append( " : " );
            sb.append( getFileInfo( new Throwable().getStackTrace() ) );

            Gdx.app.debug( debugTag, sb.toString() );

            writeToFile( sb.toString() );
        }
    }

    /**
     * This method does the same as {@link Trace#dbg(String, Object...)}
     * but does not output time and date information.
     */
    public static void info( String formatString, Object... args )
    {
        if ( Gdx.app.getLogLevel() == Application.LOG_DEBUG )
        {
            StringBuilder sb = new StringBuilder();

            sb.append( formatString );

            if ( args.length > 0 )
            {
                for ( Object arg : args )
                {
                    sb.append( " " );
                    sb.append( arg );
                }
            }

            Gdx.app.debug( infoTag, sb.toString() );

            writeToFile( sb.toString() );
        }
    }

    private static String createMessage( String formatString, StackTraceElement[] ste, Object... args )
    {
        StringBuilder sb = new StringBuilder( getTimeStampInfo() );

        sb.append( " : " );
        sb.append( getFileInfo( ste ) );
        sb.append( " : " );

        if ( ( formatString != null ) && !formatString.isEmpty() )
        {
            sb.append( formatString );
        }

        if ( args.length > 0 )
        {
            for ( Object arg : args )
            {
                sb.append( " " );
                sb.append( arg );
            }
        }

        return sb.toString();
    }

    private static String getTimeStampInfo()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        StringBuilder     sb       = new StringBuilder();

        sb.append( calendar.get( Calendar.HOUR_OF_DAY ) ).append( ":" );
        sb.append( calendar.get( Calendar.MINUTE ) ).append( ":" );
        sb.append( calendar.get( Calendar.SECOND ) ).append( ":" );
        sb.append( calendar.get( Calendar.MILLISECOND ) );

        return sb.toString();
    }

    private static String getFileInfo( StackTraceElement[] ste )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( ste[ 1 ].getFileName() ).append( "::" );
        sb.append( ste[ 1 ].getMethodName() ).append( "()::" );
        sb.append( ste[ 1 ].getLineNumber() );

        return sb.toString();
    }

    /**
     * Writes a divider line to logcat or console.
     */
    public static void divider()
    {
        divider( 100 );
    }

    /**
     * Writes a divider line to logcat or console.
     *
     * @param _length The length of the divider.
     */
    public static void divider( int _length )
    {
        divider( '-', _length );
    }

    /**
     * Writes a divider line to logcat or console.
     *
     * @param _char The character to use for the divider.
     */
    public static void divider( char _char )
    {
        divider( _char, 100 );
    }

    /**
     * Writes a divider line to logcat or console.
     *
     * @param _length The length of the divider.
     * @param _char   The character to use for the divider.
     */
    public static void divider( char _char, int _length )
    {
        StringBuilder sb = new StringBuilder();

        for ( int i = 0; i < _length; i++ )
        {
            sb.append( _char );
        }

        Gdx.app.debug( debugTag, sb.toString() );

        writeToFile( sb.toString() );
    }

    /**
     * Enables or Disables write to file.
     */
    public static void enableWriteToFile( boolean enable )
    {
        isWriteFileEnabled = enable;
    }

    /**
     * Opens a physical file for writing copies of debug messages to.
     * Note: Only messages output via Trace#dbg() are written to file.
     * Note: An option to specify target directory is to be added.
     *
     * @param fileName       - The filename. This should be filename only,
     *                       - and the file will be created in the working directory.
     * @param deleteExisting - True to delete existing copies of the file.
     *                       - False to append to existing file.
     */
    public static void openDebugFile( String fileName, boolean deleteExisting )
    {
        boolean isSuccess = true;

        if ( ( Gdx.app.getType() == Application.ApplicationType.Desktop )
                && isWriteFileEnabled )
        {
            logFile = new File( fileName );

            if ( logFile.exists() )
            {
                if ( deleteExisting )
                {
                    if ( logFile.delete() )
                    {
                        Gdx.app.debug( debugTag, "Existing logfile deleted." );
                    }
                }
            }

            if ( !logFile.exists() )
            {
                try
                {
                    isSuccess = logFile.createNewFile();

                    if ( isSuccess )
                    {
                        Gdx.app.debug( debugTag, "LOGGER: Logfile created: " + logFile.getName() );
                    }
                }
                catch ( IOException ioe )
                {
                    err( "Cannot create log file: " + logFile );
                    Stats.incMeter( SystemMeters._IO_EXCEPTION.get() );
                    isSuccess = false;
                }
            }

            if ( isSuccess )
            {
                Date              date     = new Date();
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime( date );

                writeToFile( "-----------------------------------------------------------" );
                writeToFile( "Filename: " + logFile.toString() );
                writeToFile( "Created: " + calendar.getTime() );
                writeToFile( "-----------------------------------------------------------" );
            }
        }
    }

    /**
     * Writes text to the logFile, if it exists.
     *
     * @param text String holding the text to write.
     */
    public static void writeToFile( String text )
    {
        if ( isWriteFileEnabled && ( logFile != null ) && logFile.exists() )
        {
            try
            {
                //
                // BufferedWriter for performance.
                // Pass TRUE to set append to file flag.
                BufferedWriter bw = new BufferedWriter( new FileWriter( logFile, true ) );

                bw.append( text );
                bw.write( '\r' );
                bw.close();
            }
            catch ( IOException ioe )
            {
                Stats.incMeter( SystemMeters._IO_EXCEPTION.get() );
            }
        }
    }
}
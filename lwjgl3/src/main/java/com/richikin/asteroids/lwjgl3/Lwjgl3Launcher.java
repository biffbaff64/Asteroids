package com.richikin.asteroids.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.richikin.asteroids.core.MainGame;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.graphics.TextureAtlasBuilder;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class Lwjgl3Launcher
{
    public static void main( String[] args )
    {
        if ( StartupHelper.startNewJvmIfRequired() )
        {
            return; // This handles macOS support and helps on Windows.
        }

        TextureAtlasBuilder.buildTextureAtlases();

        new Lwjgl3Application( new MainGame(), getDefaultConfiguration() );
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration()
    {
        Gfx.setDesktopDimensions();

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle( "Asteroids" );
        configuration.useVsync( true );

        //// Limits FPS to the refresh rate of the currently active monitor.
        configuration.setForegroundFPS( Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate );

        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        configuration.setWindowedMode( Gfx.DESKTOP_WINDOW_WIDTH, Gfx.DESKTOP_WINDOW_HEIGHT );
        configuration.setWindowIcon( "libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png" );

        return configuration;
    }
}
package com.richikin.asteroids.core;

import com.badlogic.gdx.Screen;
import com.richikin.asteroids.config.AppConfig;
import com.richikin.asteroids.scenes.SplashScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class MainGame extends com.badlogic.gdx.Game
{
    private SplashScreen splashScreen;

    public MainGame()
    {
    }

    @Override
    public void create()
    {
        App.setMainGame( this );

        splashScreen = new SplashScreen();
        splashScreen.setup( null );

        //
        // Initialise all essential objects required before
        // the main screen is initialised.
        App.setAppConfig( new AppConfig() );
        App.getAppConfig().setup();
    }

    @Override
    public void render()
    {
        if ( splashScreen.isAvailable )
        {
            if ( !App.getAppConfig().isStartupDone() )
            {
                App.getAppConfig().startApp();
            }

            splashScreen.update();
            splashScreen.render();

            if ( !splashScreen.isAvailable )
            {
                App.getAppConfig().closeStartup();
                splashScreen.dispose();
            }
        }
        else
        {
            super.render();
        }
    }

    /**
     *
     */
    @Override
    public void pause()
    {
        super.pause();
    }

    /**
     * Actions to perform when exiting Pause
     */
    @Override
    public void resume()
    {
        super.resume();
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize( int width, int height )
    {
        super.resize( width, height );
    }

    /**
     * @param screen
     */
    @Override
    public void setScreen( Screen screen )
    {
        super.setScreen( screen );
    }

    /**
     * @return
     */
    @Override
    public Screen getScreen()
    {
        return super.getScreen();
    }

    @Override
    public void dispose()
    {
    }
}

package com.richikin.asteroids.core;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.richikin.asteroids.config.AppConfig;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class MainGame extends com.badlogic.gdx.Game
{
    public MainGame()
    {
    }

    @Override
    public void create()
    {
        App.mainGame = this;

        //
        // Initialise all essential objects required before
        // the main screen is initialised.
        App.appConfig = new AppConfig();
        App.appConfig.setup();
    }

    @Override
    public void render()
    {
        ScreenUtils.clear( 0, 0, 0, 1 );

        super.render();
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

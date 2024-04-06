package com.richikin.asteroids.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.utils.StopWatch;
import com.richikin.asteroids.utils.Trace;

import java.util.concurrent.TimeUnit;

public class SplashScreen implements Disposable
{
    public boolean isAvailable;

    private SpriteBatch batch;
    private Texture     background;
    private StopWatch   stopWatch;

    // ------------------------------------------------------------------------

    public void setup( final String imageName )
    {
        Trace.checkPoint();

        if ( imageName != null )
        {
            batch      = new SpriteBatch();
            background = new Texture( imageName );
        }

        stopWatch = new StopWatch();

        isAvailable = true;
    }

    public void update()
    {
        if ( stopWatch.time( TimeUnit.MILLISECONDS ) > 1500 )
        {
            isAvailable = false;
        }
    }

    public void render()
    {
        if ( isAvailable && ( background != null ) )
        {
            Gdx.gl.glClearColor( 0, 0, 0, 1 );
            Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

            batch.begin();
            batch.draw
                (
                    background,
                    0,
                    0,
                    Gdx.graphics.getWidth(),
                    Gdx.graphics.getHeight()
                );
            batch.end();
        }
    }

    @Override
    public void dispose()
    {
        Trace.checkPoint();

        if ( batch != null )
        {
            batch.dispose();
        }

        if ( background != null )
        {
            background.dispose();
        }

        stopWatch  = null;
        batch      = null;
        background = null;
    }
}
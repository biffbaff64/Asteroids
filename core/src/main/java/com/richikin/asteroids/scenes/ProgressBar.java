package com.richikin.asteroids.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.utils.ItemF;
import com.richikin.asteroids.utils.StopWatch;

import java.util.concurrent.TimeUnit;

public class ProgressBar extends ItemF implements Disposable
{
    private static final int _DEFAULT_BAR_HEIGHT = 26;
    private static final int _DEFAULT_INTERVAL   = 100;

    public       boolean justEmptied;
    public final boolean isAutoRefilling;
    public       Vector2 position;

    private int       subInterval;
    private int       addInterval;
    private float     speed;
    private float     height;
    private float     scale;
    private NinePatch ninePatch;
    private NinePatch ninePatchBase;
    private StopWatch stopWatch;

    public ProgressBar( float _speed, int size, int maxSize, String texture, boolean hasBase )
    {
        ninePatch = new NinePatch( App.getAssets().getObjectRegion( texture ), 1, 1, 1, 1 );

        if ( hasBase )
        {
            ninePatchBase = new NinePatch( App.getAssets().getObjectRegion( texture ), 1, 1, 1, 1 );
            ninePatchBase.setColor( Color.BLACK );
        }

        this.minimum         = 0;
        this.maximum         = maxSize;
        this.refillAmount    = 0;
        this.stopWatch       = new StopWatch();
        this.total           = size;
        this.height          = _DEFAULT_BAR_HEIGHT;
        this.position        = new Vector2();
        this.refillAmount    = maxSize;
        this.justEmptied     = false;
        this.isAutoRefilling = false;
        this.scale           = 1;
        this.speed           = _speed;
        this.addInterval     = _DEFAULT_INTERVAL;
        this.subInterval     = _DEFAULT_INTERVAL;
    }

    public void draw( SpriteBatch spriteBatch )
    {
        if ( total > 0 )
        {
            if ( ninePatchBase != null )
            {
                ninePatchBase.draw( spriteBatch, position.x, position.y, maximum * scale, height );
            }

            ninePatch.draw( spriteBatch, position.x, position.y, total * scale, height );
        }
    }

    public void updateSlowDecrement()
    {
        justEmptied = false;

        if ( total > 0 )
        {
            if ( stopWatch.time( TimeUnit.MILLISECONDS ) >= subInterval )
            {
                total -= speed;

                if ( isEmpty() )
                {
                    justEmptied = true;
                }

                stopWatch.reset();
            }
        }
    }

    public void updateSlowDecrementWithWrap( int wrap )
    {
        justEmptied = false;

        if ( total > 0 )
        {
            if ( stopWatch.time( TimeUnit.MILLISECONDS ) >= subInterval )
            {
                total -= speed;
                total = Math.max( 0, total );

                if ( isEmpty() )
                {
                    total = wrap;
                }

                stopWatch.reset();
            }
        }
    }

    public boolean updateSlowIncrement()
    {
        if ( total < maximum )
        {
            if ( stopWatch.time( TimeUnit.MILLISECONDS ) >= addInterval )
            {
                total += speed;

                stopWatch.reset();
            }
        }

        return isFull();
    }

    public void setHeightColorScale( float height, Color color, float scale )
    {
        this.height = height;
        this.ninePatch.setColor( color );
        this.scale = scale;
    }

    public void setHeight( float _height )
    {
        height = _height;
    }

    public void setPosition( int x, int y )
    {
        position.x = x;
        position.y = y;
    }

    public boolean hasRefillRoom()
    {
        return hasRoom() && ( total < ( getMax() - 10 ) );
    }

    public void setColor( Color color )
    {
        this.ninePatch.setColor( color );
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed( float _speed )
    {
        this.speed = _speed;
    }

    public void setSubInterval( int _subInterval )
    {
        subInterval = _subInterval;
    }

    public void setAddInterval( int _addInterval )
    {
        addInterval = _addInterval;
    }

    @Override
    public void dispose()
    {
        ninePatchBase = null;
        ninePatch     = null;
        stopWatch     = null;
    }
}
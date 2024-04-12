package com.richikin.asteroids.graphics.camera;

import com.richikin.asteroids.physics.Movement;

public class Zoom
{
    public static final float _DEFAULT_ZOOM = 1.0f;
    public static final float _INITIAL_ZOOM = 0.0f;
    private final boolean bounce;
    private       int     direction;
    private       float   zoomValue;
    private       float   resetValue;
    private       float   target;

    public Zoom()
    {
        this.zoomValue  = _INITIAL_ZOOM;
        this.resetValue = _INITIAL_ZOOM;
        this.target     = _DEFAULT_ZOOM;
        this.bounce     = false;
        this.direction  = Movement.DIRECTION_STILL;
    }

    public boolean update( float zoom )
    {
        boolean done = false;

        if ( direction == Movement.DIRECTION_UP )
        {
            if ( zoomValue < target )
            {
                zoomValue += zoom;
            }
            else
            {
                if ( bounce )
                {
                    direction = Movement.DIRECTION_DOWN;
                }
                else
                {
                    direction = Movement.DIRECTION_STILL;
                }

                done = true;
            }
        }
        else if ( direction == Movement.DIRECTION_DOWN )
        {
            if ( zoomValue > target )
            {
                zoomValue -= zoom;
            }
            else
            {
                if ( bounce )
                {
                    direction = Movement.DIRECTION_UP;
                }
                else
                {
                    direction = Movement.DIRECTION_STILL;
                }

                done = true;
            }
        }

        return done;
    }

    public void stop()
    {
        zoomValue = resetValue;
    }

    public void in( float zoom )
    {
        zoomValue += zoom;
    }

    public void out( float zoom )
    {
        zoomValue -= zoom;
    }

    public float getZoomValue()
    {
        return zoomValue;
    }

    public void setZoomValue( float _zoom )
    {
        zoomValue = _zoom;
    }

    public void setTarget( final float _target )
    {
        this.target = _target;
    }

    public void setResetValue( float _reset )
    {
        resetValue = _reset;
    }
}

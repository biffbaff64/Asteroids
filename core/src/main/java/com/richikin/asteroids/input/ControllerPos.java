package com.richikin.asteroids.input;

import com.richikin.asteroids.physics.Movement;

public enum ControllerPos
{
    _LEFT( "LEFT", Movement.DIRECTION_LEFT ),
    _RIGHT( "RIGHT", Movement.DIRECTION_RIGHT ),
    _HIDDEN( "HIDDEN", Movement.DIRECTION_STILL );

    final int    value;
    final String text;

    ControllerPos( String _text, int _value )
    {
        text  = _text;
        value = _value;
    }

    public String getText()
    {
        return text;
    }

    public int getValue()
    {
        return value;
    }
}

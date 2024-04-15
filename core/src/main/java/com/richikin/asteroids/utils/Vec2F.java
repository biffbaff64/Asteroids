package com.richikin.asteroids.utils;

import org.jetbrains.annotations.NotNull;

public class Vec2F
{
    public float x;
    public float y;

    public Vec2F()
    {
    }

    public Vec2F( float x, float y )
    {
        this.x = x;
        this.y = y;
    }

    public Vec2F( @NotNull Vec2F vec2 )
    {
        this.x = vec2.x;
        this.y = vec2.y;
    }

    public void set( float x, float y )
    {
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty()
    {
        return x == 0 && y == 0;
    }

    @Override
    public String toString()
    {
        return "x: " + x + ", y: " + y;
    }
}

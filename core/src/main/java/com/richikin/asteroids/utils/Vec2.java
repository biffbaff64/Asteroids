package com.richikin.asteroids.utils;

import org.jetbrains.annotations.NotNull;

public class Vec2
{
    public int x;
    public int y;

    public Vec2()
    {
    }

    public Vec2( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    public Vec2( @NotNull Vec2 vec2 )
    {
        this.x = vec2.x;
        this.y = vec2.y;
    }

    public void set( int x, int y )
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

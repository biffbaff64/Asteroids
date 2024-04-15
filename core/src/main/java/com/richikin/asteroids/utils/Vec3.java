package com.richikin.asteroids.utils;

import org.jetbrains.annotations.NotNull;

public class Vec3
{
    public int x;
    public int y;
    public int z;

    public Vec3()
    {
    }

    public Vec3( int x, int y, int z )
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3( @NotNull Vec3 vec3 )
    {
        this.x = vec3.x;
        this.y = vec3.y;
        this.z = vec3.z;
    }

    public void set( int x, int y, int z )
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isEmpty()
    {
        return x == 0 && y == 0 && z == 0;
    }

    @Override
    public String toString()
    {
        return "x: " + x + ", y: " + y + ", z: " + z;
    }
}

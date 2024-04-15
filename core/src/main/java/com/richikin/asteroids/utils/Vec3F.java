package com.richikin.asteroids.utils;

import org.jetbrains.annotations.NotNull;

public class Vec3F
{
    public float x;
    public float y;
    public float z;

    public Vec3F()
    {
    }

    public Vec3F( float x, float y, float z )
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3F( @NotNull Vec3F vec3 )
    {
        this.x = vec3.x;
        this.y = vec3.y;
        this.z = vec3.z;
    }

    public void set( float x, float y, float z )
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

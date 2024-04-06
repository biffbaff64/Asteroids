package com.richikin.asteroids.utils;

import java.util.concurrent.TimeUnit;

public class StopWatch
{
    private long starts;

    public StopWatch()
    {
        reset();
    }

    public void reset()
    {
        starts = System.currentTimeMillis();
    }

    public long time()
    {
        long ends = System.currentTimeMillis();

        return ends - starts;
    }

    public long time( TimeUnit unit )
    {
        return unit.convert( time(), TimeUnit.MILLISECONDS );
    }
}

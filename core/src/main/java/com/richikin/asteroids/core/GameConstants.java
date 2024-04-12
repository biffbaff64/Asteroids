package com.richikin.asteroids.core;

public abstract class GameConstants
{
    public static final int MAX_LIVES              = 5;
    public static final int MIN_LEVEL              = 1;
    public static final int MAX_LEVEL              = 20;
    public static final int MAX_PROGRESSBAR_LENGTH = 100;
    public static final int MAX_SCORE              = 99_999_999;
    public static final int MAX_STRENGTH           = 100;

    public static final short _TOP    = 0x01;
    public static final short _BOTTOM = 0x02;
    public static final short _LEFT   = 0x04;
    public static final short _RIGHT  = 0x08;
}
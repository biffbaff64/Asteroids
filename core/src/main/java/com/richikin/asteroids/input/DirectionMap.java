package com.richikin.asteroids.input;

import com.richikin.asteroids.physics.Dir;
import com.richikin.asteroids.physics.DirectionValue;
import com.richikin.asteroids.physics.Movement;

public abstract class DirectionMap
{
    public static final DirectionValue[] map =
        {
/* 00 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_UP, Dir._UP ),
/* 01 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_UP, Dir._UP ),
/* 02 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_UP, Dir._UP ),
/* 03 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_UP, Dir._UP_RIGHT ),
/* 04 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_UP, Dir._UP_RIGHT ),
/* 05 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_UP, Dir._UP_RIGHT ),
/* 06 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_STILL, Dir._RIGHT ),
/* 07 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_STILL, Dir._RIGHT ),
/* 08 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_STILL, Dir._RIGHT ),
/* 09 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_STILL, Dir._RIGHT ),
/* 10 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_STILL, Dir._RIGHT ),
/* 11 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_STILL, Dir._RIGHT ),
/* 12 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_DOWN, Dir._DOWN_RIGHT ),
/* 13 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_DOWN, Dir._DOWN_RIGHT ),
/* 14 */    new DirectionValue( Movement.DIRECTION_RIGHT, Movement.DIRECTION_DOWN, Dir._DOWN_RIGHT ),
/* 15 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_DOWN, Dir._DOWN ),
/* 16 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_DOWN, Dir._DOWN ),
/* 17 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_DOWN, Dir._DOWN ),
/* 18 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_DOWN, Dir._DOWN ),
/* 19 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_DOWN, Dir._DOWN ),
/* 20 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_DOWN, Dir._DOWN ),
/* 21 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_DOWN, Dir._DOWN_LEFT ),
/* 22 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_DOWN, Dir._DOWN_LEFT ),
/* 23 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_DOWN, Dir._DOWN_LEFT ),
/* 24 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_STILL, Dir._LEFT ),
/* 25 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_STILL, Dir._LEFT ),
/* 26 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_STILL, Dir._LEFT ),
/* 27 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_STILL, Dir._LEFT ),
/* 28 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_STILL, Dir._LEFT ),
/* 29 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_STILL, Dir._LEFT ),
/* 30 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_UP, Dir._UP_LEFT ),
/* 31 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_UP, Dir._UP_LEFT ),
/* 32 */    new DirectionValue( Movement.DIRECTION_LEFT, Movement.DIRECTION_UP, Dir._UP_LEFT ),
/* 33 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_UP, Dir._UP ),
/* 34 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_UP, Dir._UP ),
/* 35 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_UP, Dir._UP ),
/* 36 */    new DirectionValue( Movement.DIRECTION_STILL, Movement.DIRECTION_STILL, Dir._STILL ),
        };
}

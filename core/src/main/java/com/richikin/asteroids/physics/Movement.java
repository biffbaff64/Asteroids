package com.richikin.asteroids.physics;

public enum Movement
{
    ;
    public static final int HORIZONTAL       = 1;
    public static final int VERTICAL         = 2;
    public static final int DIRECTION_IN     = 1;
    public static final int DIRECTION_OUT    = -1;
    public static final int FORWARDS         = 1;
    public static final int BACKWARDS        = -1;
    public static final int DIRECTION_RIGHT  = 1;
    public static final int DIRECTION_LEFT   = -1;
    public static final int DIRECTION_UP     = 1;
    public static final int DIRECTION_DOWN   = -1;
    public static final int DIRECTION_STILL  = 0;
    public static final int DIRECTION_CUSTOM = 2;

    private static final String[][] aliases =
        {
            { "LEFT ", "STILL", "RIGHT" },
            { "DOWN ", "STILL", "UP   " },
        };

    public static String getAliasX( int value )
    {
        return aliases[ 0 ][ value + 1 ];
    }

    public static String getAliasY( int value )
    {
        return aliases[ 1 ][ value + 1 ];
    }

    public static Dir translateDirection( Direction direction )
    {
        final DirectionValue[] translateTable =
            {
                new DirectionValue( DIRECTION_STILL, DIRECTION_STILL, Dir._STILL ),
                new DirectionValue( DIRECTION_LEFT, DIRECTION_STILL, Dir._LEFT ),
                new DirectionValue( DIRECTION_RIGHT, DIRECTION_STILL, Dir._RIGHT ),
                new DirectionValue( DIRECTION_STILL, DIRECTION_UP, Dir._UP ),
                new DirectionValue( DIRECTION_STILL, DIRECTION_DOWN, Dir._DOWN ),
                new DirectionValue( DIRECTION_LEFT, DIRECTION_UP, Dir._UP_LEFT ),
                new DirectionValue( DIRECTION_RIGHT, DIRECTION_UP, Dir._UP_RIGHT ),
                new DirectionValue( DIRECTION_LEFT, DIRECTION_DOWN, Dir._DOWN_LEFT ),
                new DirectionValue( DIRECTION_RIGHT, DIRECTION_DOWN, Dir._DOWN_RIGHT ),
            };

        Dir translatedDir = Dir._STILL;

        for ( DirectionValue directionValue : translateTable )
        {
            if ( ( directionValue.dirX == direction.x ) && ( directionValue.dirY == direction.y ) )
            {
                translatedDir = directionValue.translated;
            }
        }

        return translatedDir;
    }
}

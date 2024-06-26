package com.richikin.asteroids.graphics;

import com.badlogic.gdx.math.Vector2;

public class Gfx
{
    //
    // Entity collision types. These are checked against an entity's
    // 'collidesWith' property to see if collision is allowed.
    // i.e. If EntityA.bodyCategory is set to CAT_PLAYER,
    // and EntityB.collidesWith has the bit set for CAT_PLAYER, then
    // EntityA and EntityB will register collisions.
    //
    public static final short CAT_NOTHING       = 0x0000;   // - 00 (0     )
    public static final short CAT_PLAYER        = 0x0001;   // - 01 (1     )
    public static final short CAT_MOBILE_ENEMY  = 0x0002;   // - 02 (2     )
    public static final short CAT_PLAYER_WEAPON = 0x0004;   // - 03 (4     )
    public static final short CAT_WEAPON        = 0x0008;   // - 04 (8     )
    public static final short CAT_COLLECTIBLE   = 0x0010;   // - 05 (16    )
    public static final short UNDEFINED_06      = 0x0020;   // - 06 (32    )
    public static final short UNDEFINED_07      = 0x0040;   // - 07 (64    )
    public static final short UNDEFINED_08      = 0x0080;   // - 08 (128   )
    public static final short UNDEFINED_09      = 0x0100;   // - 09 (256   )
    public static final short UNDEFINED_10      = 0x0200;   // - 10 (512   )
    public static final short UNDEFINED_11      = 0x0400;   // - 11 (1024  )
    public static final short UNDEFINED_12      = 0x0800;   // - 12 (2048  )
    public static final short UNDEFINED_13      = 0x1000;   // - 13 (4096  )
    public static final short UNDEFINED_14      = 0x2000;   // - 14 (8192  )
    public static final short UNDEFINED_15      = 0x4000;   // - 15 (16384 )
    public static final short CAT_ALL           = 0x7fff;   // - 16 (32767 )

    // ------------------------------------------------------------------------

    // Maximum Z-sorting depth for sprites
    public static final int MAXIMUM_Z_DEPTH = 20;

    // The desired Frame Rate
    public static final float FPS     = 60f;
    public static final float MIN_FPS = 30f;

    // Values for Box2D.step()
    public static final float   STEP_TIME           = ( 1.0f / 60f );
    public static final int     VELOCITY_ITERATIONS = 8;
    public static final int     POSITION_ITERATIONS = 3;
    public static final Vector2 WORLD_GRAVITY       = new Vector2( 0, -9.8f );
    public static final float   FALL_GRAVITY        = 9.8f;
    public static final float   LERP_SPEED          = 0.075f;
    public static final Vector2 pixelDimensions     = new Vector2();
    public static final Vector2 meterDimensions     = new Vector2();
    public static       float   DEFAULT_ZOOM        = 1.0f;

    public static int   TERMINAL_VELOCITY;
    public static float PIXELS_TO_METERS;

    public static float HUD_SCENE_WIDTH;
    public static float HUD_SCENE_HEIGHT;
    public static float GAME_SCENE_WIDTH;
    public static float GAME_SCENE_HEIGHT;

    // Pixels Per Meter in the Box2D World, usually the length of a single TiledMap tile.
    public static float PPM;

    // Width, Height of the HUD in pixels
    public static int   HUD_WIDTH;
    public static int   HUD_HEIGHT;

    // Width, Height of the Game Window in pixels
    public static int   DESKTOP_WINDOW_WIDTH;
    public static int   DESKTOP_WINDOW_HEIGHT;

    // Width, Height of the Game View in pixels
    // This can be different to the Game Window, which allows for varying resolutions.
    public static int   GAME_VIEW_WIDTH;
    public static int   GAME_VIEW_HEIGHT;

    // -----------------------------------------------------------
    // Code
    // -----------------------------------------------------------

    public static void initialise()
    {
        setPPM( 16.0f );

        TERMINAL_VELOCITY = ( int ) ( PPM * FALL_GRAVITY );
        PIXELS_TO_METERS  = ( 1.0f / PPM );

        setSceneDimensions();
    }

    public static void setPPM( final float newPPM )
    {
        if ( newPPM != PPM )
        {
            PPM              = newPPM;
            PIXELS_TO_METERS = ( 1.0f / PPM );

            setSceneDimensions();
        }
    }

    public static void setSceneDimensions()
    {
        HUD_SCENE_WIDTH       = ( HUD_WIDTH / PPM );
        HUD_SCENE_HEIGHT      = ( HUD_HEIGHT / PPM );
        GAME_SCENE_WIDTH      = ( GAME_VIEW_WIDTH / PPM );
        GAME_SCENE_HEIGHT     = ( GAME_VIEW_HEIGHT / PPM );
    }

    public static Vector2 getScreenSizeInMeters()
    {
        meterDimensions.set( GAME_VIEW_WIDTH * PIXELS_TO_METERS, GAME_VIEW_HEIGHT * PIXELS_TO_METERS );

        return meterDimensions;
    }

    public static Vector2 getScreenSizeInPixels()
    {
        pixelDimensions.set( GAME_VIEW_WIDTH, GAME_VIEW_HEIGHT );

        return pixelDimensions;
    }

    public static float PixelsToMeters( float pixels )
    {
        return pixels * PIXELS_TO_METERS;
    }

    /**
     * Sets the viewport and app window dimensions for Android builds.
     */
    public static void setAndroidDimensions()
    {
        DESKTOP_WINDOW_WIDTH  = 1152;
        DESKTOP_WINDOW_HEIGHT = 650;
        GAME_VIEW_WIDTH       = 1280;
        GAME_VIEW_HEIGHT      = 720;
        HUD_WIDTH             = 1280;
        HUD_HEIGHT            = 720;
    }

    /**
     * Sets the viewport and app window dimensions for LWJGL2 and LWJGL3 (Desktop) builds.
     */
    public static void setDesktopDimensions()
    {
        DESKTOP_WINDOW_WIDTH  = 1152;
        DESKTOP_WINDOW_HEIGHT = 650;
        GAME_VIEW_WIDTH       = 1280;
        GAME_VIEW_HEIGHT      = 720;
        HUD_WIDTH             = 1280;
        HUD_HEIGHT            = 720;
    }
}

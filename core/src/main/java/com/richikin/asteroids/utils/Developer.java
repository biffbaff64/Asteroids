package com.richikin.asteroids.utils;

import com.richikin.asteroids.config.Settings;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.input.ControllerType;

public class Developer
{
    private static StateID developerPanelState = StateID._STATE_DISABLED;
    private static boolean isAndroidOnDesktop  = false;
    private static boolean isGodMode           = false;
    private static boolean isDevMode           = false;

    public static void setTempDeveloperSettings()
    {
        if ( isDevMode() )
        {
            Trace.divider();
            Trace.dbg( "Temporary Development Settings" );

            setAndroidOnDesktop( false );
            setGodMode( false );

            final String[] disableList =
                {
                    Settings._SHOW_FPS,
                    Settings._USING_ASHLEY_ECS,
                    Settings._CULL_SPRITES,
                    Settings._BUTTON_BOXES,
                    Settings._SHADER_PROGRAM,
                    Settings._TILE_BOXES,
                    Settings._SPRITE_BOXES,
                    Settings._LEVEL_SELECT,
                    Settings._INTRO_PANEL,
                    Settings._MENU_SCENE,
                    Settings._SCROLL_DEMO,
                    Settings._SHOW_DEBUG,
                };

            final String[] enableList =
                {
                    Settings._BOX2D_PHYSICS,
                    Settings._VIBRATIONS,
                    Settings._INSTALLED,
                };

            for ( String str : disableList )
            {
                App.getSettings().disable( str );
            }

            for ( String str : enableList )
            {
                App.getSettings().enable( str );
            }

            Trace.divider();
        }
    }

    /**
     * Reads the environment variable '_DEV_MODE', and
     * sets 'isDevMode' accordingly.
     * Note: Android builds default to _DEV_MODE = false.
     */
    public static void setDeveloperModeState()
    {
        if ( App.getAppConfig().isDesktopApp() )
        {
            isDevMode = "TRUE".equalsIgnoreCase( System.getenv( "DEV_MODE" ) );
        }
        else
        {
            isDevMode = false;
        }

        Trace.dbg( "Developer Mode is ", isDevMode ? "ENABLED." : "DISABLED." );
    }

    /**
     * Sets 'isDevMode' flag to the specified state.
     *
     * @param _state TRUE or FALSE.
     */
    public static void setDevMode( boolean _state )
    {
        isDevMode = _state;
    }

    /**
     * Sets 'isGodMode' flag to the specified state.
     *
     * @param _state TRUE or FALSE.
     */
    public static void setGodMode( boolean _state )
    {
        isGodMode = _state;
    }

    /**
     * If enabled, this flag allows testing of android related
     * tests on desktop builds.
     */
    public static void setAndroidOnDesktop( boolean _state )
    {
        isAndroidOnDesktop = _state;
    }

    public static boolean isAndroidOnDesktop()
    {
        return isAndroidOnDesktop;
    }

    /**
     * 'Developer Mode' is only allowed on Desktop builds
     *
     * @return TRUE if enabled.
     */
    public static boolean isDevMode()
    {
        return App.getAppConfig().isDesktopApp() && isDevMode;
    }

    /**
     * @return TRUE if enabled.
     */
    public static boolean isGodMode()
    {
        return isGodMode;
    }

    /**
     * Enables or disables the Developer Settings Panel.
     * The only valid states are:-
     * StateID._STATE_DISABLED
     * StateID._STATE_ENABLED
     * All other states will default to _STATE_DISABLED.
     *
     * @param state The panel State.
     */
    public static void setDeveloperPanelState( StateID state )
    {
        switch ( state )
        {
            case _STATE_ENABLED:
            case _STATE_DISABLED:
            {
                developerPanelState = state;
            }
            break;

            default:
            {
                developerPanelState = StateID._STATE_DISABLED;
            }
            break;
        }
    }

    /**
     * Returns the current state of the DeveloperPanel
     *
     * @return Either _STATE_ENABLED or _STATE_DISABLED.
     * ALl other states are invalid.
     */
    public static StateID getDeveloperPanelState()
    {
        return developerPanelState;
    }

    /**
     *
     */
    public static void configReport()
    {
        if ( isDevMode() )
        {
            Trace.divider();
            Trace.dbg( "Android App         : " + App.getAppConfig().isAndroidApp() );
            Trace.dbg( "Desktop App         : " + App.getAppConfig().isDesktopApp() );
            Trace.divider();
            Trace.dbg( "isDevMode()         : " + isDevMode() );
            Trace.dbg( "isGodMode()         : " + isGodMode() );
            Trace.divider();
            Trace.dbg( "DESKTOP_WIDTH      : " + Gfx.DESKTOP_WINDOW_WIDTH );
            Trace.dbg( "DESKTOP_HEIGHT     : " + Gfx.DESKTOP_WINDOW_HEIGHT );
            Trace.dbg( "VIEW_WIDTH         : " + Gfx.GAME_VIEW_WIDTH );
            Trace.dbg( "VIEW_HEIGHT        : " + Gfx.GAME_VIEW_HEIGHT );
            Trace.dbg( "HUD_WIDTH          : " + Gfx.HUD_WIDTH );
            Trace.dbg( "HUD_HEIGHT         : " + Gfx.HUD_HEIGHT );
            Trace.dbg( "GAME_SCENE_WIDTH   : " + Gfx.GAME_SCENE_WIDTH );
            Trace.dbg( "GAME_SCENE_HEIGHT  : " + Gfx.GAME_SCENE_HEIGHT );
            Trace.dbg( "HUD_SCENE_WIDTH    : " + Gfx.HUD_SCENE_WIDTH );
            Trace.dbg( "HUD_SCENE_HEIGHT   : " + Gfx.HUD_SCENE_HEIGHT );
            Trace.divider();
            Trace.dbg( "_PPM                : " + Gfx.PPM );
            Trace.divider();
            Trace.dbg( "_VIRTUAL?           : " + App.getAppConfig().availableInputs.contains( ControllerType._JOYSTICK, true ) );
            Trace.dbg( "_EXTERNAL?          : " + App.getAppConfig().availableInputs.contains( ControllerType._EXTERNAL, true ) );
            Trace.dbg( "_KEYBOARD?          : " + App.getAppConfig().availableInputs.contains( ControllerType._KEYBOARD, true ) );
            Trace.dbg( "controllerPos       : " + App.getAppConfig().virtualControllerPos );
            Trace.dbg( "controllersFitted   : " + App.getAppConfig().controllersFitted );
            Trace.dbg( "usedController      : " + App.getAppConfig().currentController );
            Trace.divider();
        }
    }
}

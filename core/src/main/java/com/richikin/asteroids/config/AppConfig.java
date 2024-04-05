package com.richikin.asteroids.config;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.richikin.asteroids.enums.ScreenID;
import com.richikin.asteroids.input.ControllerPos;
import com.richikin.asteroids.input.ControllerType;
import com.richikin.asteroids.input.Switch;

public class AppConfig
{
    public boolean                 isShuttingMainScene;        // Game over, back to menu screen
    public boolean                 forceQuitToMenu;            // Quit to main menu, forced via pause mode for example.
    public boolean                 gamePaused;                 // TRUE / FALSE Game Paused flag
    public boolean                 camerasReady;               // TRUE when all cameras have been created.
    public boolean                 shutDownActive;             // TRUE if game is currently processing EXIT request.
    public boolean                 entitiesExist;              // Set true when all entities have been created
    public boolean                 controllersFitted;          // TRUE if external controllers are fitted/connected.
    public boolean                 gameButtonsReady;           // TRUE When all game buttons have been defined
    public ScreenID                currentScreenID;            // ID of the currently active screeen
    public String                  currentController;          // The name of the controller being used
    public ControllerPos           virtualControllerPos;       // Virtual (on-screen) joystick position (LEFT or RIGHT)
    public Array< ControllerType > availableInputs;            // ...
    public Switch                  systemBackButton;           // ...
    public ImageButton             backButton;                 // ...

    // ------------------------------------------------------------------------

    public void setup()
    {
    }

    // ------------------------------------------------------------------------

    /**
     *
     */
    public boolean gameScreenActive()
    {
        return currentScreenID == ScreenID._GAME_SCREEN;
    }

    /**
     * @return
     */
    public boolean isUsingOnScreenControls()
    {
        return ( availableInputs.contains( ControllerType._JOYSTICK, true )
            || availableInputs.contains( ControllerType._DPAD, true )
            || availableInputs.contains( ControllerType._BUTTONS, true ) );
    }

    /**
     * @return TRUE If an external controller is fitted
     */
    public boolean isControllerFitted()
    {
        return controllersFitted;
    }

    /**
     * @return TRUE if the app is running on Desktop
     */
    public boolean isDesktopApp()
    {
        return ( Gdx.app.getType() == Application.ApplicationType.Desktop );
    }

    /**
     * @return TRUE if the app is running on Android
     */
    public boolean isAndroidApp()
    {
        return ( Gdx.app.getType() == Application.ApplicationType.Android );
    }
}

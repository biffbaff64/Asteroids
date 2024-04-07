package com.richikin.asteroids.config;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.enums.ScreenID;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.input.ControllerPos;
import com.richikin.asteroids.input.ControllerType;
import com.richikin.asteroids.input.Switch;
import com.richikin.asteroids.scenes.MainScene;
import com.richikin.asteroids.utils.Developer;
import com.richikin.asteroids.utils.Trace;

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

    private StateID startupState;

    // ------------------------------------------------------------------------

    public AppConfig()
    {
        Trace.checkPoint();

        startupState = StateID._STATE_BEGIN_STARTUP;
    }

    public void setup()
    {
        Trace.checkPoint();

        App.createEssentialObjects();

        // ------------------------------------------------
        Developer.setDeveloperModeState();
        Developer.setTempDeveloperSettings();
        // ------------------------------------------------

        Gfx.initialise();

        isShuttingMainScene = false;
        forceQuitToMenu     = false;
        gamePaused          = false;
        camerasReady        = false;
        shutDownActive      = false;
        entitiesExist       = false;
        controllersFitted   = false;
        gameButtonsReady    = false;
        currentController   = "None";
        availableInputs     = new Array<>();

        if ( isDesktopApp() )
        {
            Gdx.graphics.setWindowedMode( Gfx.DESKTOP_WIDTH, Gfx.DESKTOP_HEIGHT );
        }

        virtualControllerPos = ControllerPos._HIDDEN;

        setControllerTypes();

        systemBackButton = new Switch();

        Stats.setup( "com.richikin.asteroids.meters" );

        //
        // These essential objects have now been created.
        // Setup/Initialise for any essential objects required
        // before TitleScene can be created is mostly
        // performed in startApp().
    }

    public void startApp()
    {
        Trace.checkPoint();

        App.getBox2DHelper().createWorld();
        App.getAssets().initialise();
        App.getSettings().freshInstallCheck();
        App.getSettings().debugReport();

        // ------------------------------------------------------------------
        // Google Play Services setup - Android only.
        if ( isAndroidApp() )
        {
            Trace.dbg( "Initialising Google Play Services." );
        }
        // ------------------------------------------------------------------

        App.getGameRenderer().createCameras();
        App.createStage( App.getGameRenderer().getHudGameCamera().viewport );
        App.getBox2DHelper().createB2DRenderer();

        startupState = StateID._STATE_END_STARTUP;

        Trace.divider();
    }

    public void closeStartup()
    {
        Developer.configReport();

        // ======================================
        // TEMP
        App.createMainSceneObjects();
        App.setMainScene( new MainScene() );
        App.getMainScene().reset();
        App.getMainGame().setScreen( App.getMainScene() );
        // ======================================
    }

    public boolean isStartupDone()
    {
        return ( startupState == StateID._STATE_END_STARTUP );
    }

    // ------------------------------------------------------------------------

    public boolean gameScreenActive()
    {
        return currentScreenID == ScreenID._GAME_SCREEN;
    }

    public void setControllerTypes()
    {
        if ( isAndroidApp() || Developer.isAndroidOnDesktop() )
        {
            Trace.dbg( "Enabling _VIRTUAL controller." );

            availableInputs.add( ControllerType._BUTTONS );

            if ( Developer.isAndroidOnDesktop() )
            {
                availableInputs.add( ControllerType._KEYBOARD );
            }
        }
        else
        {
            Trace.dbg( "Enabling _EXTERNAL controller." );
            Trace.dbg( "Enabling _KEYBOARD controller." );

            availableInputs.add( ControllerType._EXTERNAL );
            availableInputs.add( ControllerType._KEYBOARD );
        }
    }

    public boolean isUsingOnScreenControls()
    {
        return ( availableInputs.contains( ControllerType._JOYSTICK, true )
            || availableInputs.contains( ControllerType._DPAD, true )
            || availableInputs.contains( ControllerType._BUTTONS, true ) );
    }

    public boolean isExternalControllerFitted()
    {
        return controllersFitted;
    }

    public void addBackButton( String _default, String _pressed )
    {
//        Scene2DUtils scene2DUtils = new Scene2DUtils();
//
//        backButton = scene2DUtils.addButton( _default, _pressed, 0, 0 );
    }

    public void showAndEnableBackButton()
    {
        if ( backButton != null )
        {
            backButton.setVisible( true );
            backButton.setDisabled( false );
        }
    }

    public void hideAndDisableBackButton()
    {
        if ( backButton != null )
        {
            backButton.setVisible( false );
            backButton.setDisabled( true );
        }
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

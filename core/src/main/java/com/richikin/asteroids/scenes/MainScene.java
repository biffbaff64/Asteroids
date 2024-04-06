package com.richikin.asteroids.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.enums.CamID;
import com.richikin.asteroids.enums.ScreenID;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.graphics.camera.OrthoGameCamera;
import com.richikin.asteroids.utils.Trace;

public class MainScene extends AbstractBaseScene
{
    public boolean firstTime;

    public MainScene()
    {
        super();

        firstTime = true;
    }

    @Override
    public void initialise()
    {
    }

    @Override
    public void update()
    {
        switch ( App.getAppState().peek() )
        {
            // These are here in case there is a lag between appState
            // being set to these values and control being passed
            // to a different scene.
            case _STATE_MAIN_MENU:
            case _STATE_CLOSING:
            {
            }
            break;

            // All relevant states which apply
            // to this scene.
            case _STATE_SETUP:
            case _STATE_GET_READY:
            case _STATE_WELCOME_PANEL:
            case _STATE_DEVELOPER_PANEL:
            case _STATE_PAUSED:
            case _STATE_GAME:
            case _STATE_MESSAGE_PANEL:
            case _STATE_PREPARE_LEVEL_RETRY:
            case _STATE_LEVEL_RETRY:
            case _STATE_PREPARE_LEVEL_FINISHED:
            case _STATE_LEVEL_FINISHED:
            case _STATE_PREPARE_GAME_OVER_MESSAGE:
            case _STATE_GAME_OVER:
            case _STATE_GAME_FINISHED:
            case _STATE_END_GAME:
            {
            }
            break;

            default:
            {
                Trace.dbg( "Unsupported game state: " + App.getAppState().peek() );
            }
            break;
        }
    }

    @Override
    public void render( float delta )
    {
        super.update();

        if ( App.getAppConfig().gameScreenActive() )
        {
            update();

            super.render( delta );

            App.getBox2DHelper().worldStep();
        }
    }

    public void draw( final SpriteBatch spriteBatch, OrthoGameCamera camera )
    {
    }

    @Override
    public void show()
    {
        Trace.checkPoint();

        super.show();

        App.getAppConfig().currentScreenID = ScreenID._GAME_SCREEN;
        App.getGameRenderer().enableCamera( CamID._BACKGROUND );

        App.getBox2DHelper().activate();

        initialise();

        App.getAppConfig().hideAndDisableBackButton();

        App.getAppState().set( StateID._STATE_SETUP );
    }

    @Override
    public void hide()
    {
        Trace.checkPoint();

        super.hide();

        App.getBox2DHelper().deActivate();
    }

    /**
     * Resets this scene for re-use.
     */
    public void reset()
    {
        firstTime = true;
    }

    @Override
    public void loadImages()
    {
    }

    @Override
    public void dispose()
    {
    }
}

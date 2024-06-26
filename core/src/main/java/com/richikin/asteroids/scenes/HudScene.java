package com.richikin.asteroids.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.core.GameConstants;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.graphics.FontUtils;
import com.richikin.asteroids.graphics.GameAssets;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.graphics.camera.OrthoGameCamera;
import com.richikin.asteroids.input.Switch;
import com.richikin.asteroids.utils.Scene2DUtils;
import com.richikin.asteroids.utils.Trace;

import java.util.Locale;

public class HudScene implements Disposable
{
    public static final int _VERY_LARGE_FONT_SIZE = 48;
    public static final int _LARGE_FONT_SIZE      = 35;
    public static final int _SMALL_FONT_SIZE      = 25;

    public static final int _ROTATE_LEFT  = 0;
    public static final int _ROTATE_RIGHT = 1;
    public static final int _THRUST       = 2;
    public static final int _SHOOT        = 3;
    public static final int _SCORE        = 4;
    public static final int _LIVES        = 5;

    private static final int _X      = 0;
    private static final int _Y      = 1;
    private static final int _WIDTH  = 2;
    private static final int _HEIGHT = 3;

    //@formatter:off
    public final int[][] displayPos =
        {
            {  959,   97,   96,   96 },             // ROTATE LEFT
            { 1060,  186,   96,   96 },             // ROTATE RIGHT
            { 1157,   97,   96,   96 },             // THRUST
            { 1060,   14,   96,   96 },             // SHOOT

            // ----------------------------------------
            // Y is distance from the TOP of the screen
            {   42,   20,    0,    0 },             // Score
            {   42,   88,   21,   31 },             // Lives
        };
    //@formatter:on

    public float   hudOriginX;
    public float   hudOriginY;
    public StateID hudStateID;
    public Switch  buttonRotateLeft;
    public Switch  buttonRotateRight;
    public Switch  buttonThrust;
    public Switch  buttonShoot;
    public Switch  buttonPause;

    private Image[]    smallMan;
    private BitmapFont smallFont;
    private BitmapFont midFont;
    private BitmapFont bigFont;

    // ----------------------------------------------------------------
    // Code
    // ----------------------------------------------------------------

    public HudScene()
    {
        Trace.checkPoint();
    }

    public void createHud()
    {
        Trace.checkPoint();

        createHudFonts();

        Scene2DUtils scene2DUtils = new Scene2DUtils();

        smallMan = new Image[ GameConstants.MAX_LIVES ];

        for ( int i = 0; i < GameConstants.MAX_LIVES; i++ )
        {
            smallMan[ i ] = scene2DUtils.createImage
                (
                    GameAssets.SMALL_SHIP,
                    App.getAssets().getObjectsLoader()
                );

            smallMan[ i ].setPosition
                (
                    ( displayPos[ _LIVES ][ _X ] + ( i * ( displayPos[ _LIVES ][ _WIDTH ] + 10 ) ) ),
                    ( Gfx.HUD_HEIGHT - displayPos[ _LIVES ][ _Y ] )
                );

            smallMan[ i ].setVisible( true );

            App.getStage().addActor( smallMan[ i ] );
        }

        createHUDButtons();

        hudStateID = StateID._STATE_PANEL_START;
    }

    public void update()
    {
        switch ( hudStateID )
        {
            case _STATE_PANEL_START:
            {
                if ( App.getGameRenderer().getHudGameCamera().getCameraZoom() != 1.0f )
                {
                    App.getGameRenderer().getHudGameCamera().updateZoom( 1.0f, 0.02f );
                }
                else
                {
                    hudStateID = StateID._STATE_PANEL_UPDATE;
                }
            }
            break;

            case _STATE_PANEL_UPDATE:
            {
                updateScoreDisplay();
                updateLivesDisplay();
                checkButtons();
            }
            break;

            default:
            {
                Trace.err( "Unsupported HUD State: ", hudStateID );
            }
            break;
        }
    }

    private void updateScoreDisplay()
    {
    }

    /**
     * Display the number of available lives.
     */
    private void updateLivesDisplay()
    {
        if ( App.getPlayer() != null )
        {
            for ( int i = 0; i < GameConstants.MAX_LIVES; i++ )
            {
                smallMan[ i ].setVisible( i < App.getGameManager().lives.getTotal() );
            }
        }
    }

    private void checkButtons()
    {
    }

    public void render( OrthoGameCamera camera, boolean canDrawControls )
    {
        // I'm pretty sure I shouldn't need to be doing this. Surely the HUD
        // just needs to be displayed at 0, 0?
        hudOriginX = camera.getPosition().x - ( Gfx.HUD_WIDTH / 2f );
        hudOriginY = camera.getPosition().y - ( Gfx.HUD_HEIGHT / 2f );

        drawPanels();
        drawItems();
        drawMessages();

        String str = "[RL:" + buttonRotateLeft.isPressed() + "]"
            + "[RR:" + buttonRotateRight.isPressed() + "]"
            + "[SH:" + buttonShoot.isPressed() + "]"
            + "[TH:" + buttonThrust.isPressed() + "]";

        smallFont.setColor( Color.WHITE );
        smallFont.draw( App.getSpriteBatch(), str, 20, 20 );
    }

    public void refillItems()
    {
        // The player starts each level with full strength, there's
        // no sense in starting a new level with 1% strength!
        App.getPlayer().strength = GameConstants.MAX_STRENGTH;
    }

    public void setStateID( StateID state )
    {
        hudStateID = state;
    }

    private void drawPanels()
    {
    }

    private void drawItems()
    {
        bigFont.setColor( Color.WHITE );
        bigFont.draw( App.getSpriteBatch(),
            String.format( Locale.UK, "%08d", App.getGameManager().score.getTotal() ),
            displayPos[ _SCORE ][ _X ],
            Gfx.HUD_HEIGHT - displayPos[ _SCORE ][ _Y ] );
    }

    /**
     * Draws the in-game 'message panels' that pop up.
     * 'Get Ready', "Game Over' etc.
     */
    private void drawMessages()
    {
    }

    public void showControls( boolean visible )
    {
    }

    /**
     * Show or Hide the onscreen pause button.
     * Only valid for ControllerType._VIRTUAL mode.
     */
    public void showPauseButton( boolean visibility )
    {
        if ( App.getAppConfig().isUsingOnScreenControls() )
        {
            // TODO: 22/02/2022
        }
    }

    public void releaseDirectionButtons()
    {
    }

    public void enableHUDButtons()
    {
    }

    private void createHudFonts()
    {
        FontUtils fontUtils = new FontUtils();

        bigFont   = fontUtils.createFont( GameAssets.MODENINE_FONT, _VERY_LARGE_FONT_SIZE );
        midFont   = fontUtils.createFont( GameAssets.MODENINE_FONT, _LARGE_FONT_SIZE );
        smallFont = fontUtils.createFont( GameAssets.MODENINE_FONT, _SMALL_FONT_SIZE );
    }

    /**
     * Creates any buttons used for the HUD.
     * HUD buttons are just switches so that they can be set/unset
     * by keyboard OR on-screen virtual buttons.
     */
    private void createHUDButtons()
    {
        buttonRotateLeft  = new Switch();
        buttonRotateRight = new Switch();
        buttonThrust      = new Switch();
        buttonShoot       = new Switch();
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose()
    {
        hudStateID = null;

        buttonRotateLeft  = null;
        buttonRotateRight = null;
        buttonThrust      = null;
        buttonShoot       = null;

        for ( int i = 0; i < GameConstants.MAX_LIVES; i++ )
        {
            smallMan[ i ].addAction( Actions.removeActor() );
            smallMan[ i ] = null;
        }

        smallMan = null;

        smallFont = null;
        midFont   = null;
        bigFont   = null;
    }
}

package com.richikin.asteroids.core;

import com.richikin.asteroids.config.Settings;
import com.richikin.asteroids.enums.ActionStates;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.graphics.GameAssets;
import com.richikin.asteroids.input.ControllerType;
import com.richikin.asteroids.utils.Developer;
import com.richikin.asteroids.utils.Trace;

public class MainGameHandler
{
    private boolean isWaitingForPlayer;

    public MainGameHandler()
    {
        Trace.checkPoint();
    }

    public void update()
    {
        // Initialise the current level.
        // If the level is restarting, that will
        // also be handled here.
        //
        // Display and update the 'Get Ready' message.
        //
        // The main game 'loop'
        //
        // Player lost a life.
        // Trying again.
        //
        // Back to TitleScene.
        switch ( App.getAppState().peek() )
        {
            case _STATE_SETUP:
            {
                stateSetup();
            }
            break;

            case _STATE_GET_READY:
            {
                stateGetReady();
            }
            break;

            case _STATE_DEVELOPER_PANEL:
            case _STATE_WELCOME_PANEL:
            case _STATE_SETTINGS_PANEL:
            case _STATE_GAME:
            {
                stateGame();
            }
            break;

            case _STATE_PAUSED:
            {
                statePaused();
            }
            break;

            case _STATE_MESSAGE_PANEL:
            {
                stateMessagePanel();
            }
            break;

            case _STATE_PREPARE_LEVEL_RETRY:
            case _STATE_LEVEL_RETRY:
            {
                stateSetForRetry();
            }
            break;

            case _STATE_PREPARE_LEVEL_FINISHED:
            case _STATE_LEVEL_FINISHED:
            {
                stateSetForLevelFinished();
            }
            break;

            case _STATE_PREPARE_GAME_OVER_MESSAGE:
            {
                stateSetForGameOverMessage();
            }
            break;

            case _STATE_GAME_OVER:
            case _STATE_GAME_FINISHED:
            {
                stateWaitForGameOverMessage();
            }
            break;

            case _STATE_END_GAME:
            {
                stateSetForEndGame();
            }
            break;

            default:
            {
                Trace.dbg( "Unsupported gameState: " + App.getAppState().peek() );
            }
            break;
        }
    }

    /**
     * Initialise the current level.
     * If the level is restarting, that will also be handled here.
     * _STATE_SETUP
     */
    private void stateSetup()
    {
        Trace.dbg( "_STATE_SETUP: firstTime = ", App.getMainScene().firstTime );

        App.getGameManager().prepareCurrentLevel( App.getMainScene().firstTime );

        if ( App.getMainScene().firstTime )
        {
            App.getGameRenderer().enableAllCameras();
            App.getGameRenderer().disableLerping();

//            App.getAudio().playGameTune( true );
        }

        App.getPanelManager().addZoomPanel( GameAssets.GETREADY_MSG, 1500 );

        App.getAppState().set( StateID._STATE_GET_READY );
        App.getGameManager().isGameSetupDone = true;

        isWaitingForPlayer = true;

        Trace.dbg( "Setup done." );
    }

    /**
     * Display and update the 'Get Ready' message.
     * _STATE_GET_READY
     */
    private void stateGetReady()
    {
        App.getHud().update();

        //
        // If there is no 'Get Ready' message on screen then setup
        // flow control to play the game.
        if ( !App.getPanelManager().panelExists( GameAssets.GETREADY_MSG ) )
        {
            Trace.dbg( "----- START GAME (GET READY) -----" );

            App.getAppState().set( StateID._STATE_GAME );
            App.getHud().setStateID( StateID._STATE_PANEL_UPDATE );

            // If game has virtual/onscreen controls...
            if ( App.getAppConfig().availableInputs.contains( ControllerType._JOYSTICK, true ) )
            {
                App.getHud().showControls( true );
                App.getHud().showPauseButton( true );
            }

            App.getMainScene().firstTime = false;

            if ( Developer.isDevMode() )
            {
                App.getGameRenderer().getBackgroundCamera().debug();
                App.getGameRenderer().getSpriteGameCamera().debug();
                App.getGameRenderer().getHudGameCamera().debug();
            }
        }
    }

    /**
     * Update the game for states:-
     * _STATE_WELCOME_PANEL
     * _STATE_DEVELOPER_PANEL
     * _STATE_SETTINGS_PANEL
     * _STATE_GAME
     */
    private void stateGame()
    {
        App.getHud().update();

        switch ( App.getAppState().peek() )
        {
            case _STATE_DEVELOPER_PANEL:
            {
                if ( Developer.getDeveloperPanelState() == StateID._STATE_DISABLED )
                {
                    App.getAppState().set( StateID._STATE_GAME );
                    App.getHud().setStateID( StateID._STATE_PANEL_UPDATE );
                }
                break;
            }

            // Stay here until the HUD changes the AppState.
            case _STATE_WELCOME_PANEL:
            {
                App.getEntityManager().updateSprites();
                App.getEntityManager().tidySprites();
            }
            break;
            // ---------------------------------------------

            default:
            {
                if ( isWaitingForPlayer && ( App.getPlayer().getActionState() == ActionStates._STANDING ) )
                {
                    if ( App.getSettings().isEnabled( Settings._INTRO_PANEL ) )
                    {
//                        App.getHud().introPanel = new IntroPanel();
//                        App.getHud().introPanel.create();

                        App.getHud().setStateID( StateID._STATE_WELCOME_PANEL );
                        App.getAppState().set( StateID._STATE_WELCOME_PANEL );
                        App.getPlayer().setActionState( ActionStates._WAITING );
                    }

                    isWaitingForPlayer = false;
                }
                // ---------------------------------------------
                else
                {
                    App.getEntityManager().updateSprites();
                    App.getEntityManager().tidySprites();

                    // Check for game ending
                    if ( !App.getGameManager().updateEndgame() )
                    {
                        // Tasks to perform if the game has not ended
                        if ( App.getAppState().peek() == StateID._STATE_PAUSED )
                        {
                            if ( !App.getAppConfig().gamePaused )
                            {
                                App.getAppState().set( StateID._STATE_GAME );
                            }
                        }
                    }
                }
            }
            break;
        }
    }

    /**
     * Handles game actions, if any, during pause mode.
     * _STATE_PAUSED
     */
    private void statePaused()
    {
        App.getHud().update();
    }

    /**
     * Handles the message panel which appears when the
     * player speaks to a villager/guide.
     * _STATE_MESSAGE_PANEL
     */
    private void stateMessagePanel()
    {
        App.getHud().update();
//        App.getMapData().update();

//        if ( !App.getConversationManager().update() )
//        {
//            App.getAppState().set( StateID._STATE_GAME );
//
//            App.getConversationManager().dispose();
//
//            App.getHud().showControls( true );
//        }
    }

    /**
     * Handles the preparation for retrying the current
     * level, after Player loses a life.
     * _STATE_PREPARE_LEVEL_RETRY
     * _STATE_LEVEL_RETRY
     */
    private void stateSetForRetry()
    {
        App.getHud().update();

        if ( App.getAppState().peek() == StateID._STATE_PREPARE_LEVEL_RETRY )
        {
//            try
//            {
//                badLuckMessage = MathUtils.random(GameAssets.badLuckMessages.length - 1);
//            }
//            catch (ArrayIndexOutOfBoundsException boundsException)
//            {
//                badLuckMessage = 0;
//            }
//
//            App.getHud().getPanelManager().addZoomPanel(GameAssets.badLuckMessages[badLuckMessage], 2500);

            App.getAppState().set( StateID._STATE_LEVEL_RETRY );
        }
        else
        {
//            if (!App.getHud().getPanelManager().panelExists(GameAssets.badLuckMessages[badLuckMessage]))
            {
                App.getAppState().set( StateID._STATE_SETUP );
            }
        }

        App.getMainScene().retryDelay = null;
    }

    /**
     * Handles finishing the current level and
     * moving on to the next one.
     * _STATE_PREPARE_LEVEL_FINISHED:
     * _STATE_LEVEL_FINISHED:
     */
    private void stateSetForLevelFinished()
    {
        App.getGameManager().closeCurrentLevel();
        App.getHud().update();

        App.getMainScene().reset();

        App.getAppState().set( StateID._STATE_SETUP );
        App.getHud().setStateID( StateID._STATE_PANEL_START );
    }

    /**
     * Initialise the 'Game Over' message.
     * _STATE_PREPARE_GAME_OVER_MESSAGE
     */
    private void stateSetForGameOverMessage()
    {
        App.getPanelManager().addZoomPanel( GameAssets.GAMEOVER_MSG, 3000 );

//        App.getAudio().startSound( AudioData.SFX_LOST );

        App.getAppState().set( StateID._STATE_GAME_OVER );
    }

    /**
     * Game Over, due to all levels being completed.
     * Game Over, due to losing all lives.
     * (Waits for the 'Game Over' message to disappear.)
     * _STATE_GAME_OVER
     * _STATE_GAME_FINISHED
     */
    private void stateWaitForGameOverMessage()
    {
        App.getHud().update();

        if ( !App.getPanelManager().panelExists( GameAssets.GAMEOVER_MSG ) )
        {
            App.getAppState().set( StateID._STATE_END_GAME );
        }
    }

    /**
     * Game Ended, hand control back to MainMenuScreen.
     * Control is also passed to here if forceQuitToMenu or quitToMainMenu are set.
     * _STATE_END_GAME
     */
    private void stateSetForEndGame()
    {
        Trace.boxedDbg( "***** GAME OVER *****" );

//        App.getAudio().playGameTune( false );

        App.getMainGame().setScreen( App.getTitleScene() );
    }
}

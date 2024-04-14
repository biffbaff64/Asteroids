package com.richikin.asteroids.core;

import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.enums.ActionStates;
import com.richikin.asteroids.enums.CamID;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.utils.Item;
import com.richikin.asteroids.utils.NumberUtils;
import com.richikin.asteroids.utils.Trace;

public class GameManager implements Disposable
{
    public Item    lives;
    public Item    score;
    public boolean isLevelCompleted;
    public boolean isGameCompleted;
    public boolean isRestarting;
    public boolean isGameOver;
    public boolean isGameSetupDone;

    private int     livesStack;
    private int     scoreStack;
    private boolean isFirstTime;
    private float   difficulty;

    // ------------------------------------------------------------------------

    public GameManager()
    {
        isFirstTime     = true;
        isGameSetupDone = false;
        isGameCompleted = false;

        lives = new Item( 0, GameConstants.MAX_LIVES, GameConstants.MAX_LIVES );
        score = new Item( 0, GameConstants.MAX_SCORE, 0 );

        livesStack = 0;
        scoreStack = 0;

        difficulty = 0.0f;
    }

    public void update()
    {
        switch ( App.getAppState().peek() )
        {
            case _STATE_PAUSED:
            case _STATE_GAME:
            case _STATE_PREPARE_LEVEL_FINISHED:
            case _STATE_MESSAGE_PANEL:
            {
                updateStacks();
                updateDifficulty();
            }
            break;

            default:
            {
            }
            break;
        }
    }

    /**
     * Performs all necessary checks for either level completion
     * or game completion.
     */
    public boolean updateEndgame()
    {
        //
        // Player is dead, no lives left
        //
        if ( ( ( App.getPlayer() != null ) && ( App.getPlayer().getActionState() == ActionStates._DEAD ) )
            || App.getAppConfig().forceQuitToMenu )
        {
            Trace.boxedDbg( "PLAYER IS DEAD, NO LIVES LEFT" );

            App.getAppState().set( StateID._STATE_PREPARE_GAME_OVER_MESSAGE );

            App.getAppConfig().isShuttingMainScene = true;

            return true;
        }
        else
        {
            //
            // Waheyy!! All levels completed!
            //
            if ( isGameCompleted )
            {
                Trace.boxedDbg( "GAME COMPLETED" );

//                App.getMainScene().gameCompletedPanel = new GameCompletedPanel();
//                App.getMainScene().gameCompletedPanel.setup();

                App.getHud().setStateID( StateID._STATE_GAME_FINISHED );
                App.getAppState().set( StateID._STATE_GAME_FINISHED );

                return true;
            }
            else
            {
                //
                // Current level completed
                //
                if ( isLevelCompleted )
                {
                    Trace.boxedDbg( "LEVEL COMPLETED" );

                    App.getHud().setStateID( StateID._STATE_PANEL_UPDATE );
                    App.getAppState().set( StateID._STATE_PREPARE_LEVEL_FINISHED );

                    return true;
                }
                else
                {
                    //
                    // Restarting due to life lost and player is resetting...
                    //
//                    if ( App.getGameProgress().isRestarting )
//                    {
//                        assert App.getPlayer() != null;
//
//                        if ( App.getPlayer().getActionState() == ActionStates._RESETTING )
//                        {
//                            Trace.boxedDbg( "LIFE LOST - TRY AGAIN" );
//
//                            App.getMainScene().retryDelay = new StopWatch();
//                            App.getAppState().set( StateID._STATE_PREPARE_LEVEL_RETRY );
//                        }
//
//                        return true;
//                    }
                }
            }
        }

        return false;
    }

    /**
     * Pushes the supplied amount onto the update stack for the specified Stack ID.
     * This value will then be added onto the relevant counter over the next few
     * frames in the private method {@link #updateStacks()}.
     */
    public void stackPush( Stack stack, int amount )
    {
        switch ( stack )
        {
            case LIVES:
                livesStack += amount;
                break;

            case SCORE:
                scoreStack += amount;
                break;

            default:
                break;
        }
    }

    public boolean stacksAreEmpty()
    {
        return livesStack == 0 && scoreStack == 0;
    }

    public float getDifficulty()
    {
        return difficulty;
    }

    /**
     * Set up everything necessary for a new game,
     * called in MainScene#initialise.
     */
    public void prepareNewGame()
    {
        Trace.checkPoint();

        if ( isFirstTime )
        {
            Trace.checkPoint();

            //
            // Make sure all progress counters are initialised.
            lives.setToMaximum();
            score.setToMinimum();
            livesStack = 0;
            scoreStack = 0;

            // Only the HUD camera is enabled initially.
            App.getGameRenderer().enableCamera( CamID._HUD );

            App.getEntityManager().initialise();

            // Score, Lives display etc.
            App.getHud().createHud();
        }

        isFirstTime = false;
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Prepare the current level by setting up maps, entities
     * and any relevant flags/variables.
     */
    public void prepareCurrentLevel( boolean firstTime )
    {
        Trace.checkPoint();

        if ( App.getGameManager().isRestarting )
        {
            restartCurrentLevel();
        }
        else if ( firstTime || App.getGameManager().isLevelCompleted )
        {
            setupForNewLevel();
        }

        App.getAppConfig().gamePaused = false;

        App.getHud().refillItems();

        isLevelCompleted = false;
        isRestarting     = false;
        isGameOver       = false;
    }

    /**
     * Sets up the map and entities for a new level.
     */
    public void setupForNewLevel()
    {
        Trace.checkPoint();

//        App.getRoomManager().setRoom( App.getLevel(), 0 );
//
//        App.getMapParser().initialiseLevelMap();        // Load tiled map and create renderer
//        App.getMapParser().createPositioningData();     // Process the tiled map data

        App.getEntityManager().initialiseForLevel();
    }

    /**
     * Actions to perform when a level has been completed. Remove all entities
     * from the level, but make sure that the main player is untouched, except
     * for its PhysicsBody, which will be rebuilt.
     */
    public void closeCurrentLevel()
    {
        Trace.checkPoint();

        App.getEntityUtils().killAllExcept( GraphicID.G_PLAYER );
        App.getEntityData().getEntityMap().setSize( 1 );
//        App.getPlayer().killBody();
//        App.getMapUtils().destroyBodies();
//        App.getMapData().placementTiles.clear();
//        App.getMapParser().getCurrentMap().dispose();

        Trace.dbg( "EntityMap Size: ", App.getEntityData().getEntityMap().size );
    }

    /**
     * Reset all entity positions, and re-init the main player,
     * ready to replay the current level.
     */
    private void restartCurrentLevel()
    {
        Trace.checkPoint();

        // TODO: 25/09/2022 - This is incorrect. It should take into account items that have been
        //                  - collected, as these should not reappear if a level restarts.

        App.getEntityUtils().killAllExcept( GraphicID.G_PLAYER );
        App.getEntityData().getEntityMap().setSize( 1 );
        App.getEntityManager().initialiseForLevel();
    }

    private void updateStacks()
    {
        if ( scoreStack > 0 )
        {
            int amount = NumberUtils.getCount( scoreStack );

            score.add( amount );
            scoreStack -= amount;
        }

        if ( livesStack > 0 )
        {
            int amount = NumberUtils.getCount( livesStack );

            lives.add( amount );
            livesStack -= amount;
        }
    }

    // ------------------------------------------------------------------------

    private void updateDifficulty()
    {
        // TODO: 13/04/2024
        difficulty += 0.001f;
    }

    @Override
    public void dispose()
    {
        lives = null;
        score = null;
    }

    // ------------------------------------------------------------------------

    public enum Stack
    {
        LIVES,
        SCORE,
    }
}

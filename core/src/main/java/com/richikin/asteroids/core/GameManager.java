package com.richikin.asteroids.core;

import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.enums.CamID;
import com.richikin.asteroids.utils.Item;
import com.richikin.asteroids.utils.Trace;

public class GameManager implements Disposable
{
    public enum Stack
    {
        LIVES,
        SCORE,
    }

    public Item lives;
    public Item score;

    public boolean isLevelCompleted;
    public boolean isRestarting;
    public boolean isGameOver;

    private int     livesStack;
    private int     scoreStack;
    private boolean isFirstTime;
    private float   difficulty;

    public GameManager()
    {
        isFirstTime = true;

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

    // ------------------------------------------------------------------------

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

    public void prepareCurrentLevel()
    {
        Trace.checkPoint();

        App.getAppConfig().gamePaused = false;

        App.getHud().refillItems();

        isLevelCompleted = false;
        isRestarting     = false;
        isGameOver       = false;
    }

    public void setupForNewLevel()
    {
        Trace.checkPoint();
    }

    // ------------------------------------------------------------------------

    private void updateStacks()
    {
    }

    private void updateDifficulty()
    {
    }

    // ------------------------------------------------------------------------

    @Override
    public void dispose()
    {
        lives = null;
        score = null;
    }
}

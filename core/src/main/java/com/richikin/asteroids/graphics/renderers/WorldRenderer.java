package com.richikin.asteroids.graphics.renderers;

import com.richikin.asteroids.core.App;

public class WorldRenderer
{
    public void render()
    {
        if ( !App.getAppConfig().shutDownActive )
        {
            switch ( App.getAppState().peek() )
            {
                case _STATE_SETUP:
                case _STATE_GET_READY:
                case _STATE_PAUSED:
                case _STATE_LEVEL_RETRY:
                case _STATE_PREPARE_LEVEL_FINISHED:
                case _STATE_LEVEL_FINISHED:
                case _STATE_GAME:
                case _STATE_SETTINGS_PANEL:
                case _STATE_WELCOME_PANEL:
                case _STATE_DEBUG_HANG:
                {
                    App.getEntityManager().drawSprites();
                }
                break;

                case _STATE_MAIN_MENU:
                case _STATE_CLOSING:
                case _STATE_GAME_OVER:
                case _STATE_END_GAME:
                default:
                    break;
            }
        }
    }
}

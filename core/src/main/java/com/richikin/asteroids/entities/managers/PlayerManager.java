package com.richikin.asteroids.entities.managers;

import com.richikin.asteroids.core.App;
import com.richikin.asteroids.entities.actors.MainPlayer;
import com.richikin.asteroids.entities.objects.SpriteDescriptor;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.utils.Trace;

public class PlayerManager extends BasicEntityManager
{
    public int playerTileX;
    public int playerTileY;

    private SpriteDescriptor descriptor;

    public PlayerManager()
    {
        super( GraphicID._PLAYER_MANAGER );
    }

    @Override
    public void init()
    {
        Trace.checkPoint();

        super.init();

        setSpawnPoint();
        createPlayer();
    }

    public void createPlayer()
    {
        Trace.checkPoint();

        App.getEntityManager().playerIndex = 0;
        App.getEntityManager().playerReady = false;

        App.setPlayer( new MainPlayer() );
        App.getPlayer().initialise( descriptor );
        App.getEntityData().addEntity( App.getPlayer() );

        App.getEntityManager().updateEntityMapIndexes();
        App.getEntityManager().playerIndex = descriptor._INDEX;
        App.getEntityManager().playerReady = true;
    }

    public void setSpawnPoint()
    {
        Trace.checkPoint();

        playerTileX = Gfx.GAME_VIEW_WIDTH / 2;
        playerTileY = Gfx.GAME_VIEW_HEIGHT / 2;

        descriptor             = App.getEntities().getDescriptor( GraphicID.G_PLAYER );
        descriptor._POSITION.x = playerTileX;
        descriptor._POSITION.y = playerTileY;
        descriptor._INDEX      = App.getEntityData().getEntityMap().size;
    }
}
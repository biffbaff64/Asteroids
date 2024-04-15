package com.richikin.asteroids.entities.managers;

import com.badlogic.gdx.math.MathUtils;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.entities.actors.Asteroid;
import com.richikin.asteroids.entities.objects.SpriteDescriptor;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.physics.Movement;
import com.richikin.asteroids.utils.Trace;

public class AsteroidManager extends BasicEntityManager
{
    private SpriteDescriptor descriptor;

    public AsteroidManager()
    {
        super( GraphicID._ASTEROID_MANAGER );
    }

    @Override
    public void init()
    {
        Trace.checkPoint();

        activeCount = 0;
        maxCount    = 10;
    }

    @Override
    public void update()
    {
        if ( activeCount < maxCount )
        {
            create();
        }
    }

    @Override
    public void create()
    {
        Trace.checkPoint();

        descriptor = App.getEntities().getDescriptor( GraphicID.G_BIG_ASTEROID );

        spawn();

        Asteroid asteroid = new Asteroid( descriptor._GID );
        asteroid.initialise( descriptor );

        App.getEntityData().addEntity( asteroid );

        activeCount++;
    }

    private void spawn()
    {
        if ( MathUtils.random( 100 ) < 50 )
        {
            descriptor._POSITION.x = -descriptor._SIZE.x;
            descriptor._DIR.x      = Movement.DIRECTION_RIGHT;
        }
        else
        {
            descriptor._POSITION.x = ( int ) ( Gfx.GAME_SCENE_WIDTH * Gfx.PPM );
            descriptor._DIR.x      = Movement.DIRECTION_LEFT;
        }

        descriptor._POSITION.y = ( int ) ( MathUtils.random( ( int ) Gfx.GAME_SCENE_HEIGHT ) * Gfx.PPM );

        if ( descriptor._POSITION.y < ( Gfx.GAME_SCENE_HEIGHT / 2 ) )
        {
            descriptor._DIR.y = Movement.DIRECTION_UP;
        }
        else
        {
            descriptor._DIR.y = Movement.DIRECTION_DOWN;
        }

        descriptor._DIST.x = Gfx.GAME_VIEW_WIDTH;
        descriptor._DIST.y = Gfx.GAME_VIEW_HEIGHT;

        descriptor._SPEED.x = MathUtils.random( 8 ) + 3;
        descriptor._SPEED.y = MathUtils.random( 8 ) + 3;

        descriptor.debug();
    }
}

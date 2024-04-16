package com.richikin.asteroids.entities.managers;

import com.badlogic.gdx.math.MathUtils;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.entities.actors.Ufo;
import com.richikin.asteroids.entities.objects.SpriteDescriptor;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.physics.Movement;
import com.richikin.asteroids.utils.StopWatch;
import com.richikin.asteroids.utils.Trace;

import java.util.concurrent.TimeUnit;

public class UfoManager extends BasicEntityManager
{
    private SpriteDescriptor descriptor;
    private StopWatch        activateTimer;
    private int              activateDelay;

    public UfoManager()
    {
        super( GraphicID._UFO_MANAGER );
    }

    @Override
    public void init()
    {
        Trace.checkPoint();

        super.init();

        activateTimer = new StopWatch();
        activateDelay = 30000 + ( MathUtils.random( 100 ) * 10 );
    }

    @Override
    public void update()
    {
        if (( activeCount < maxCount )
            && ( activateTimer.time( TimeUnit.MILLISECONDS ) > activateDelay ) )
        {
            create();

            activateDelay = 30000 + ( MathUtils.random( 100 ) * 10 );
            activateTimer.reset();
        }
    }

    @Override
    public void create()
    {
        Trace.checkPoint();

        descriptor = App.getEntities().getDescriptor( GraphicID.G_UFO );

        spawn();

        Ufo ufo = new Ufo();
        ufo.initialise( descriptor );

        App.getEntityData().addEntity( ufo );

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

        descriptor._DIR.y = Movement.DIRECTION_STILL;

        descriptor._DIST.x = Gfx.GAME_VIEW_WIDTH;
        descriptor._DIST.y = 0;

        descriptor._SPEED.x = MathUtils.random( 2.0f ) + 2.5f;
        descriptor._SPEED.y = 0;
    }
}

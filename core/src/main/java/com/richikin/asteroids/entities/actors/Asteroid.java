package com.richikin.asteroids.entities.actors;

import com.richikin.asteroids.core.App;
import com.richikin.asteroids.core.GameConstants;
import com.richikin.asteroids.entities.objects.GdxSprite;
import com.richikin.asteroids.entities.objects.SpriteDescriptor;
import com.richikin.asteroids.enums.ActionStates;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.physics.Direction;
import com.richikin.asteroids.physics.Movement;
import com.richikin.asteroids.physics.PhysicsBodyType;
import com.richikin.asteroids.utils.Trace;

public class Asteroid extends GdxSprite
{
    public boolean isMovingX;
    public boolean isMovingY;
    public int     asteroidType;    // 0, 1, or 2

    private Direction previousDirection;

    public Asteroid( GraphicID gid )
    {
        super( gid );
    }

    @Override
    public void initialise( SpriteDescriptor descriptor )
    {
        Trace.checkPoint();

        create( descriptor );

        bodyCategory = Gfx.CAT_PLAYER;
        collidesWith = Gfx.CAT_COLLECTIBLE;

        createBody( PhysicsBodyType.KINEMATIC );

        previousDirection = new Direction();

        setup( true );
    }

    public void setup( boolean isSpawning )
    {
        Trace.checkPoint();

        direction.set( Movement.DIRECTION_STILL, Movement.DIRECTION_STILL );
        lookingAt.set( Movement.DIRECTION_RIGHT, Movement.DIRECTION_STILL );
        previousDirection.set( lookingAt );

        strength = GameConstants.MAX_STRENGTH;

        isMovingX  = false;
        isMovingY  = false;
        isRotating = false;
        isFlippedX = false;
        isFlippedY = false;
        canFlip    = false;
        isDrawable = true;

        sprite.setRotation( 0 );

        isSetupCompleted = true;

        App.getHud().enableHUDButtons();

        if ( isSpawning )
        {
            setActionState( ActionStates._SPAWNING );
        }
        else
        {
            setActionState( ActionStates._STANDING );
        }
    }

    @Override
    public void update()
    {
        if ( App.getAppState().peek() == StateID._STATE_PAUSED )
        {
            setActionState( ActionStates._PAUSED );
        }

        updateAsteroid();

        animate();

        updateCommon();
    }

    private void updateAsteroid()
    {
    }
}

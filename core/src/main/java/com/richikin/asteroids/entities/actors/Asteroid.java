package com.richikin.asteroids.entities.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
import com.richikin.asteroids.utils.StopWatch;
import com.richikin.asteroids.utils.Trace;

import java.util.concurrent.TimeUnit;

public class Asteroid extends GdxSprite
{
    public boolean isMovingX;
    public boolean isMovingY;
    public int     asteroidType;    // 0, 1, or 2

    private Direction previousDirection;
    private StopWatch pauseTimer;
    private int       pauseDelay;

    public Asteroid( GraphicID gid )
    {
        super( gid );
    }

    @Override
    public void initialise( SpriteDescriptor descriptor )
    {
        Trace.checkPoint();

        create( descriptor );

        bodyCategory      = Gfx.CAT_MOBILE_ENEMY;
        collidesWith      = Gfx.CAT_PLAYER_WEAPON;
        previousDirection = new Direction();
        asteroidType      = MathUtils.random( 2 );
        pauseTimer        = new StopWatch();
        pauseDelay        = 1000 + ( MathUtils.random( 50 ) * 10 );

        createBody( PhysicsBodyType.DYNAMIC );

        getPhysicsBody().body.setGravityScale( 0 );

        setup( true, descriptor );
    }

    public void setup( boolean isSpawning, SpriteDescriptor descriptor )
    {
        Trace.checkPoint();

        direction.set( descriptor._DIR );
        speed.set( descriptor._SPEED.x, descriptor._SPEED.y );

        previousDirection.set( direction );

        strength = GameConstants.MAX_STRENGTH;

        isMovingX  = false;
        isMovingY  = false;
        isFlippedX = false;
        isFlippedY = false;
        canFlip    = false;
        isDrawable = true;

        isRotating  = true;
        rotateSpeed = 0.5f * asteroidType + 1;

        isSetupCompleted = true;

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

    @Override
    public void animate()
    {
        sprite.setRegion( animFrames[ asteroidType ] );
    }

    @Override
    public void tidy( int index )
    {
        App.getEntityData().getManager( GraphicID._ASTEROID_MANAGER ).free();
    }

    private void updateAsteroid()
    {
        switch ( getActionState() )
        {
            case _SPAWNING:
            {
                if ( pauseTimer.time( TimeUnit.MILLISECONDS ) < pauseDelay )
                {
                    setActionState( ActionStates._RUNNING );
                }
            }
            break;

            case _STANDING:
            {
                setActionState( ActionStates._RUNNING );
            }
            break;

            case _RUNNING:
            {
                if ( !boundsCheck() )
                {
                    getPhysicsBody().body.setLinearVelocity( ( speed.x * direction.x ), ( speed.y * direction.y ) );
                }
            }
            break;

            case _WAITING:
            {
                if ( pauseTimer.time( TimeUnit.MILLISECONDS ) > 1500 )
                {
                    setActionState( ActionStates._STANDING );
                }
            }
            break;

            default:
            {
            }
            break;
        }
    }

    private boolean boundsCheck()
    {
        boolean hasWrapped = false;

        Vector2 currentVelocity = getPhysicsBody().body.getLinearVelocity();

        if ( direction.x == Movement.DIRECTION_RIGHT )
        {
            if ( sprite.getX() > Gfx.GAME_VIEW_WIDTH )
            {
                getPhysicsBody().body.setTransform
                    (
                        -frameWidth / Gfx.PPM,
                        sprite.getY() / Gfx.PPM,
                        getPhysicsBody().body.getAngle()
                    );
            }
        }
        else if ( direction.x == Movement.DIRECTION_LEFT )
        {
            if ( ( sprite.getX() + frameWidth ) < 0 )
            {
                getPhysicsBody().body.setTransform
                    (
                        Gfx.GAME_VIEW_WIDTH / Gfx.PPM,
                        sprite.getY() / Gfx.PPM,
                        getPhysicsBody().body.getAngle()
                    );
            }
        }

        if ( direction.y == Movement.DIRECTION_UP )
        {
            if ( sprite.getY() > Gfx.GAME_VIEW_HEIGHT )
            {
                getPhysicsBody().body.setTransform
                    (
                        sprite.getX() / Gfx.PPM,
                        -frameHeight / Gfx.PPM,
                        getPhysicsBody().body.getAngle()
                    );

                hasWrapped = true;
            }
        }
        else if ( direction.y == Movement.DIRECTION_DOWN )
        {
            if ( ( sprite.getY() + frameHeight ) < 0 )
            {
                getPhysicsBody().body.setTransform
                    (
                        sprite.getX() / Gfx.PPM,
                        Gfx.GAME_VIEW_HEIGHT / Gfx.PPM,
                        getPhysicsBody().body.getAngle()
                    );

                hasWrapped = true;
            }
        }

        return hasWrapped;
    }
}

package com.richikin.asteroids.entities.actors;

import com.richikin.asteroids.core.App;
import com.richikin.asteroids.core.GameConstants;
import com.richikin.asteroids.entities.objects.GdxSprite;
import com.richikin.asteroids.entities.objects.SpriteDescriptor;
import com.richikin.asteroids.enums.ActionStates;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.physics.PhysicsBodyType;
import com.richikin.asteroids.utils.Trace;
import org.jetbrains.annotations.NotNull;

public class Ufo extends GdxSprite
{
    public boolean isMovingX;
    public boolean isMovingY;

    private boolean hasBeenOnScreen;

    public Ufo()
    {
        super( GraphicID.G_UFO );
    }

    @Override
    public void initialise( SpriteDescriptor descriptor )
    {
        Trace.checkPoint();

        create( descriptor );

        bodyCategory = Gfx.CAT_MOBILE_ENEMY;
        collidesWith = Gfx.CAT_PLAYER_WEAPON;

        createBody( PhysicsBodyType.DYNAMIC );

        getPhysicsBody().body.setGravityScale( 0 );

        setup( descriptor );
    }

    public void setup( @NotNull SpriteDescriptor descriptor )
    {
        Trace.checkPoint();

        direction.set( descriptor._DIR );
        speed.set( descriptor._SPEED.x, descriptor._SPEED.y );

        strength = GameConstants.MAX_STRENGTH;

        isMovingX  = false;
        isMovingY  = false;
        isFlippedX = false;
        isFlippedY = false;
        canFlip    = false;
        isDrawable = true;

        hasBeenOnScreen  = false;
        isSetupCompleted = true;

        setActionState( ActionStates._STANDING );
    }

    @Override
    public void update()
    {
        updateUfo();

        animate();

        updateCommon();
    }

    @Override
    public void animate()
    {
        sprite.setRegion( animFrames[ 0 ] );
    }

    @Override
    public void tidy( int index )
    {
        App.getEntityData().getManager( GraphicID._UFO_MANAGER ).free();
    }

    private void updateUfo()
    {
        switch ( getActionState() )
        {
            case _SPAWNING:
            case _STANDING:
            {
                setActionState( ActionStates._RUNNING );
            }
            break;

            case _RUNNING:
            {
                if ( hasBeenOnScreen && !App.getEntityUtils().isOnScreen( this ) )
                {
                    setActionState( ActionStates._DEAD );
                }
                else
                {
                    getPhysicsBody().body.setLinearVelocity( ( speed.x * direction.x ), ( speed.y * direction.y ) );

                    hasBeenOnScreen = App.getEntityUtils().isOnScreen( this );
                }
            }
            break;

            default:
            {
            }
            break;
        }
    }
}

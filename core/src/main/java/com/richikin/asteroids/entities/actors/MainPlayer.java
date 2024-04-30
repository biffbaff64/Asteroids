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
import com.richikin.asteroids.utils.Developer;
import com.richikin.asteroids.utils.Trace;

public class MainPlayer extends GdxSprite
{
    private static final int PLAYER_X_SPEED = 12;

    public ButtonInputHandler buttons;
    public boolean            isHurting;
    public boolean            isMovingX;
    public boolean            isMovingY;

    private Direction previousDirection;

    // ------------------------------------------------------------------------
    // CODE
    // ------------------------------------------------------------------------

    public MainPlayer()
    {
        super( GraphicID.G_PLAYER );
    }

    @Override
    public void initialise( SpriteDescriptor descriptor )
    {
        Trace.checkPoint();

        create( descriptor );

        bodyCategory = Gfx.CAT_PLAYER;
        collidesWith = Gfx.CAT_MOBILE_ENEMY;

        createBody( PhysicsBodyType.KINEMATIC );

        previousDirection = new Direction();
        buttons           = new ButtonInputHandler();

        setup( true );
    }

    /**
     * Completes entity initialisation WITHOUT allocating any new objects.
     * This is the re-entry point for restarting an entity, after losing a
     * life, for instance.
     */
    public void setup( boolean isSpawning )
    {
        Trace.checkPoint();

        direction.set( Movement.DIRECTION_STILL, Movement.DIRECTION_STILL );
        lookingAt.set( Movement.DIRECTION_RIGHT, Movement.DIRECTION_STILL );
        previousDirection.set( lookingAt );

        strength = GameConstants.MAX_STRENGTH;

        isMovingX       = false;
        isMovingY       = false;
        isRotating      = false;
        isFlippedX      = false;
        isFlippedY      = false;
        canFlip         = true;
        isDrawable      = true;
        isHurting       = false;
        isAnimating     = false;

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

    /**
     * Set back to start positions, and reset relevant properties
     * such as movement, collision state etc.
     */
    public void restartPlayer()
    {
        App.getPlayer().isSetupCompleted = false;
        App.getPlayer().setup( true );
    }

    @Override
    public void preUpdate()
    {
        // If the physics body was destroyed by a previous action state
        // change, create a new body and update the PhysicsBody object.
        // This requires the bodyBox to have been set up correctly for the
        // new body, which must be done in setActionState.
        if ( getPhysicsBody().body == null )
        {
            createBody( PhysicsBodyType.DYNAMIC );
        }
        // else...
        // Update the bodybox details ready for when the physics body
        // needs to be recreated.
        else
        {
            updateBodyBox();
        }

        super.preUpdate();
    }

    @Override
    public void update()
    {
        if ( App.getAppState().peek() == StateID._STATE_PAUSED )
        {
            setActionState( ActionStates._PAUSED );
        }

        updateMainPlayer();

        animate();

        updateCommon();
    }

    @Override
    public void postUpdate()
    {
        super.postUpdate();

        // Quick check to see if the player entity has left the map window.
//        if ( !App.getMapData().mapBox.contains( sprite.getBoundingRectangle() ) )
//        {
//            setActionState( ActionStates._DYING );
//            handleDying();
//        }
    }

    /**
     * Sets the sprite position from the physics body coordinates
     */
    @Override
    public void setPositionFromBody()
    {
        if ( ( getPhysicsBody().body != null ) && ( sprite != null ) )
        {
            float newX = getBodyX();
            float newY;

            newX -= ( getPhysicsBody().bodyBox.width / 2f );
            newY = getBodyY() - ( getPhysicsBody().bodyBox.height / 2f );

            sprite.setPosition( newX, newY );
        }
    }

    @Override
    public void definePhysicsBodyBox( boolean useBodyPos )
    {
        if ( !useBodyPos )
        {
            int modifier = ( ( lookingAt.x == Movement.DIRECTION_LEFT ) ? 23 : 26 );

            getPhysicsBody().bodyBox.set
                (
                    sprite.getX() + modifier,
                    sprite.getY(),
                    sprite.getWidth(),
                    ( getActionState() == ActionStates._CROUCHING ) ? frameHeight - 5 : frameHeight
                );
        }
        else
        {
            updateBodyBox();
        }
    }

    /**
     * The body box holds the size, shape, and position data for use
     * when creating a new body.
     * Useful for when an existing body is destroyed when changing
     * to an animation which is a different size than the current one.
     */
    private void updateBodyBox()
    {
        if ( sprite != null )
        {
            getPhysicsBody().bodyBox.width  = sprite.getWidth();
            getPhysicsBody().bodyBox.height = ( getActionState() == ActionStates._CROUCHING )
                ? frameHeight - 5
                : frameHeight;
            getPhysicsBody().bodyBox.x      = sprite.getX() + 26;
            getPhysicsBody().bodyBox.y      = sprite.getY();
        }
    }

    /**
     * Utility method for updating the players X Direction.
     * Updates the X Direction after first copying the current
     * direction to the copy.
     * This makes it easier to update lookingAt when direction
     * has no direction value.
     *
     * @param newXDir int holding the new X direction.
     */
    public void setXDirection( int newXDir )
    {
        previousDirection.set( direction );
        direction.x = newXDir;
    }

    /**
     * Adds a specified amount to the player strength.
     *
     * @param amount The amount to add.
     */
    public void addStrength( float amount )
    {
        strength = Math.min( strength + amount, GameConstants.MAX_STRENGTH );
    }

    /**
     * Flag the players physics body for destruction.
     */
    public void killBody()
    {
        getPhysicsBody().isAlive = false;
        App.getBox2DHelper().bodiesList.add( App.getPlayer().getPhysicsBody() );
    }

    public void handleDying()
    {
        App.getGameManager().lives.subtract( 1 );

        if ( ( App.getGameManager().lives.getTotal() > 0 ) || Developer.isGodMode() )
        {
            if ( App.getGameManager().lives.getTotal() <= 0 )
            {
                App.getGameManager().lives.refill();
            }

            setActionState( ActionStates._RESETTING );

            App.getGameManager().isRestarting = false;
            App.getGameManager().isGameOver   = false;
        }
        else
        {
            setActionState( ActionStates._DEAD );

            App.getGameManager().isRestarting = false;
            App.getGameManager().isGameOver   = true;
        }
    }

    private void updateMainPlayer()
    {
        switch ( getActionState() )
        {
            case _SPAWNING:
            {
//                if ( animation.isAnimationFinished( elapsedAnimTime ) )
                {
                    setActionState( ActionStates._STANDING );
                }
            }
            break;

            case _STANDING:
            {
                setActionState( ActionStates._RUNNING );
            }
            break;

            case _EXPLODING:
            case _RESTARTING:
            case _WAITING:
            case _DEAD:
            case _PAUSED:
            case _KILLED:
            {
            }
            break;

            case _RESETTING:
            {
                // TODO
            }
            break;

            case _RUNNING:
            {
                if ( direction.hasDirection() )
                {
                    lookingAt.set( direction );
                }

                buttons.checkButtons();
                movePlayer();
            }
            break;

            case _FIGHTING:
            {
                if ( animation.isAnimationFinished( elapsedAnimTime ) )
                {
                    if ( getActionState() == ActionStates._FIGHTING )
                    {
                        createBullet();
                    }

                    setActionState( ActionStates._STANDING );
                }
            }
            break;

            // State may have changed in checkButtons
            case _HURT:
            {
                buttons.checkButtons();

                if ( getActionState() == ActionStates._HURT )
                {
                    if ( animation.isAnimationFinished( elapsedAnimTime ) )
                    {
                        setActionState( ActionStates._STANDING );

//                        isTouchingEnemy = false;
                    }
                }
                else
                {
//                    isTouchingEnemy = false;

                    movePlayer();
                }
            }
            break;

            case _DYING:
            {
                if ( animation.isAnimationFinished( elapsedAnimTime ) )
                {
                    handleDying();
                }
            }
            break;

            default:
            {
                Trace.dbg( "Unsupported player action: " + getActionState() );
            }
            break;
        }
    }

    private void movePlayer()
    {
        speed.x = ( isMovingX ? PLAYER_X_SPEED : 0 );

        if ( isMovingX )
        {
            getPhysicsBody().body.setLinearVelocity
                (
                    ( speed.x * direction.x ),
                    App.getPlayer().getPhysicsBody().body.getLinearVelocity().y
                );
        }
    }

    private void createBullet()
    {
    }

    @Override
    public void dispose()
    {
    }
}

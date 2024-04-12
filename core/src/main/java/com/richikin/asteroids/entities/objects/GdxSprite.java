package com.richikin.asteroids.entities.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.core.GameConstants;
import com.richikin.asteroids.enums.ActionStates;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.physics.BodyIdentity;
import com.richikin.asteroids.physics.Direction;
import com.richikin.asteroids.physics.PhysicsBody;
import com.richikin.asteroids.physics.PhysicsBodyType;
import com.richikin.asteroids.utils.Trace;

import java.util.Arrays;

public class GdxSprite implements Disposable
{
    // -----------------------------------------------
    // Identity etc.
    //
    public GraphicID    gid;
    public GraphicID    type;                           // Entity Type - _ENTITY, _PICKUP, _OBSTACLE, etc.
    public ActionStates entityAction;                   // Current action/state
    public int          spriteNumber;                   // Position in the EntityMap array
    public boolean      isMainCharacter;
    public Sprite       sprite;
    public float        strength;

    protected boolean isLinked;                         // TRUE if this sprite is linked to another.
    protected int     link;                             // The index of the linked sprite.

    // -----------------------------------------------
    // Movement / Transform
    //
    public Direction direction;
    public Direction lookingAt;
    public Vector2   distance;
    public Vector2   speed;
    public Vector3   initXYZ;                         // Initial Map position, set on creation.
    public int       zPosition;
    public boolean   isFlippedX;
    public boolean   isFlippedY;
    public boolean   canFlip;
    public boolean   isRotating;
    public float     rotateSpeed;
    public Vector3   position;

    // -----------------------------------------------
    // Collision/Physics Related
    //
    public int     b2dBodyIndex;                  // Index into the Body array
    public short   bodyCategory;                  // Bit-mask entity collision type (See Gfx()).
    public short   collidesWith;                  // Bit-mask of entity types that can be collided with
    public boolean isHittable;                    // ( Might be losing this flag... )
    public boolean isHittingSame;
    public boolean isHittingWeapon;
    public boolean isTouchingPlayer;

    // -----------------------------------------------
    // Animation related
    //
    public Animation< TextureRegion > animation;
    public TextureRegion[]            animFrames;

    public float   elapsedAnimTime;
    public boolean isAnimating;
    public boolean isLoopingAnim;
    public int     frameWidth;         // Width in pixels, or width of frame for animations
    public int     frameHeight;        // Width in pixels, or width of frame for animations
    public boolean isDrawable;
    public boolean isActive;
    public boolean isSetupCompleted;

    // --------------------------------------------------------------
    // Code
    // --------------------------------------------------------------

    /**
     * Default constructor.
     */
    public GdxSprite()
    {
        this( GraphicID.G_NO_ID );
    }

    /**
     * Constructor.
     * Creates a GdxSprite with the supplied GraphicID.
     */
    public GdxSprite( GraphicID gid )
    {
        this.gid = gid;
    }

    /**
     * Initialise this Sprite.
     * Override in any entity classes and add any
     * other relevant initialisation code AFTER the
     * call to create().
     *
     * @param descriptor The {@link SpriteDescriptor} holding all setup information.
     */
    public void initialise( SpriteDescriptor descriptor )
    {
        create( descriptor );
    }

    /**
     * Performs the actual setting up of the GdxSprite, according to the
     * information provided in the supplied {@link SpriteDescriptor}.
     */
    public void create( SpriteDescriptor descriptor )
    {
        sprite    = new Sprite();
        direction = new Direction();
        lookingAt = new Direction();
        speed     = new Vector2();
        distance  = new Vector2();
        initXYZ   = new Vector3();
        position  = new Vector3();

        isDrawable      = true;
        isRotating      = false;
        isFlippedX      = false;
        isFlippedY      = false;
        canFlip         = true;
        isMainCharacter = false;
        isHittable      = true;
        isActive        = true;

        spriteNumber = descriptor._INDEX;
        type         = descriptor._TYPE;
        isAnimating  = ( descriptor._FRAMES > 1 );
        entityAction = ActionStates._NO_ACTION;
        b2dBodyIndex = App.getBox2DHelper().bodiesList.size;
        strength     = GameConstants.MAX_STRENGTH;

        // PhysicsBody data will be initialised later on.
        App.getBox2DHelper().bodiesList.add( new PhysicsBody() );

        if ( descriptor._ASSET != null )
        {
            setAnimation( descriptor );
        }

        // Determine the initial starting position by
        // multiplying the marker tile position by tile size
        // and then adding on any supplied modifier value.
        Vector3 vec3m = getPositionModifier();

        Vector3 vec3 = new Vector3
            (
                descriptor._POSITION.x + vec3m.x,
                descriptor._POSITION.y + vec3m.y,
                App.getEntityUtils().getInitialZPosition( gid ) + vec3m.z
            );

        initPosition( vec3 );

        definePhysicsBodyBox( false );

        isLinked = ( descriptor._LINK > 0 );
        link     = descriptor._LINK;

        isSetupCompleted = true;
    }

    public void createBody( PhysicsBodyType bodyType )
    {
        getPhysicsBody().body = App.getBox2DHelper().bodyBuilder.newBody
            (
                getPhysicsBody().bodyBox,
                bodyCategory,
                collidesWith,
                bodyType
            );

        if ( App.getBox2DHelper().bodiesList.get( b2dBodyIndex ).body != null )
        {
            getPhysicsBody().body.setUserData( new BodyIdentity( this, gid, type ) );

            App.getBox2DHelper().bodiesList.get( b2dBodyIndex ).isAlive = true;
        }
    }

    /**
     * Sets the initial starting position for this sprite.
     * NOTE: It is important that frameWidth & frameHeight
     * are initialised before this method is called.
     */
    public void initPosition( Vector3 vec3F )
    {
        initXYZ.set( vec3F );

        sprite.setPosition( initXYZ.x, initXYZ.y );
        sprite.setBounds( initXYZ.x, initXYZ.y, frameWidth, frameHeight );
        sprite.setOriginCenter();

        zPosition = ( int ) vec3F.z;
    }

    /**
     * Provides an init position modifier value.
     * GdxSprites are placed on TiledMap boundaries and
     * some may need that position adjusting.
     */
    public Vector3 getPositionModifier()
    {
        return new Vector3( 0, 0, 0 );
    }

    /**
     * Provides the facility for some sprites to perform certain
     * actions before the main update method.
     * Some sprites may not need to do this, or may need to do extra
     * tasks, in which case this can be overridden.
     */
    public void preUpdate()
    {
        //
        // Catch-All for NPCs. Kill them if strength is depleted.
        if ( !isMainCharacter
            && ( strength <= 0 )
            && ( entityAction != ActionStates._DEAD )
            && ( entityAction != ActionStates._DEAD_PAUSE )
            && ( entityAction != ActionStates._DYING ) )
        {
            entityAction = ActionStates._DYING;
        }
    }

    /**
     * The main update method.
     * This is the MINIMUM that should be performed for each sprite. Most sprites
     * should override this to perform their various actions, or at least call
     * this method at the end of the overridden method.
     */
    public void update()
    {
        animate();

        updateCommon();
    }

    /**
     * Common updates for all entities
     */
    public void updateCommon()
    {
        if ( isRotating )
        {
            sprite.rotate( rotateSpeed );
        }

        if ( canFlip )
        {
            sprite.setFlip( isFlippedX, isFlippedY );
        }
    }

    public void postUpdate()
    {
    }

    public void tidy()
    {
    }

    public void preDraw()
    {
    }

    public void draw( SpriteBatch spriteBatch )
    {
        setPositionFromBody();

        if ( isDrawable )
        {
            try
            {
                sprite.setAlpha( isActive ? 1.0f : 0.4f );
                sprite.draw( spriteBatch );
            }
            catch ( NullPointerException npe )
            {
                Trace.dbg( gid.name() + " : " + npe.getMessage() );
            }
        }
    }

    public void animate()
    {
        if ( isAnimating )
        {
            sprite.setRegion( App.getAnimationUtils().getKeyFrame( animation, elapsedAnimTime, isLoopingAnim ) );
            elapsedAnimTime += Gdx.graphics.getDeltaTime();
        }
        else
        {
            sprite.setRegion( animFrames[ 0 ] );
        }
    }

    /**
     * Creates the animation sequence to be used.
     * Also initialises frameWidth & frameHeight.
     */
    public void setAnimation( SpriteDescriptor descriptor )
    {
        try
        {
            animFrames = new TextureRegion[ descriptor._FRAMES ];

            TextureRegion asset = App.getAssets().getAnimationRegion( descriptor._ASSET );

            if ( descriptor._SIZE != null )
            {
                frameWidth  = ( int ) descriptor._SIZE.x;
                frameHeight = ( int ) descriptor._SIZE.y;
            }
            else
            {
                frameWidth  = asset.getRegionWidth() / descriptor._FRAMES;
                frameHeight = asset.getRegionHeight();
            }

            TextureRegion[][] tmpFrames = asset.split( frameWidth, frameHeight );

            int i = 0;

            for ( final TextureRegion[] tmpFrame : tmpFrames )
            {
                for ( final TextureRegion textureRegion : tmpFrame )
                {
                    if ( i < descriptor._FRAMES )
                    {
                        animFrames[ i++ ] = textureRegion;
                    }
                }
            }

            animation = new Animation<>( descriptor._ANIM_RATE / 6f, animFrames );
            animation.setPlayMode( descriptor._PLAYMODE );
            elapsedAnimTime = 0;

            isLoopingAnim = ( ( descriptor._PLAYMODE != Animation.PlayMode.NORMAL )
                && ( descriptor._PLAYMODE != Animation.PlayMode.REVERSED ) );

            sprite.setRegion( animFrames[ 0 ] );
            sprite.setSize( frameWidth, frameHeight );
        }
        catch ( NullPointerException npe )
        {
            Trace.divider( '#', 100 );
            Trace.checkPoint();
            descriptor.debug();
            Trace.divider( '#', 100 );
        }
    }

    /**
     * Sets the sprite position from the physics body coordinates
     * so that it is drawn at the correct location.
     */
    public void setPositionFromBody()
    {
        if ( ( getPhysicsBody() != null ) && ( sprite != null ) )
        {
            sprite.setPosition
                (
                    ( getPhysicsBody().body.getPosition().x * Gfx.PPM ) - ( getPhysicsBody().bodyBox.width / 2f ),
                    ( getPhysicsBody().body.getPosition().y * Gfx.PPM ) - ( getPhysicsBody().bodyBox.height / 2f )
                );
        }
    }

    /**
     * Sets up the CollisionObject for this sprite, and sets
     * its position to the supplied x & y coordinates.
     */
    public void setCollisionObject( float xPos, float yPos )
    {
    }

    /**
     * Defines a box to use for Box2D Physics body creation, based on this sprite's
     * co-ordinates and size. This is the default implementation, and sets the box
     * size to the exact dimensions of an animation frame.
     * <p>
     *     Override to create differing sizes.
     * </p>
     */
    public void definePhysicsBodyBox( boolean useBodyPos )
    {
        getPhysicsBody().bodyBox.set( sprite.getX(), sprite.getY(), frameWidth, frameHeight );
    }

    //@formatter:off
    public short          getBodyCategory()   {   return bodyCategory;                }
    public short          getCollidesWith()   {   return collidesWith;                }
    public int            getSpriteNumber()   {   return spriteNumber;                }
    public int            getLink()           {   return link;                        }
    public boolean        isLinked()          {   return isLinked;                    }
    public GraphicID      getGID()            {   return gid;                         }
    public GraphicID      getType()           {   return type;                        }
    public ActionStates   getActionState()    {   return entityAction;                }
    public boolean        isHittingSame()     {   return isHittingSame;               }

    public void setActionState( ActionStates action ) { entityAction = action; }
    //@formatter:on

    public void setCollecting()
    {
    }

    public void setDying()
    {
        speed.setZero();

        getPhysicsBody().body.setLinearVelocity( 0, 0 );
        setActionState( ActionStates._DYING );
    }

    public void setLink( int _link )
    {
        link     = _link;
        isLinked = ( _link > 0 );
    }

    /**
     * Gets the Box2D Physics body associated with this GdxSprite.
     */
    public PhysicsBody getPhysicsBody()
    {
        return App.getBox2DHelper().bodiesList.get( b2dBodyIndex );
    }

    /**
     * Gets the current X position of the physics body
     * attached to this sprite.
     */
    public float getBodyX()
    {
        return getPhysicsBody().body == null
            ? 0 : ( getPhysicsBody().body.getPosition().x * Gfx.PPM );
    }

    /**
     * Gets the current Y position of the physics body
     * attached to this sprite.
     */
    public float getBodyY()
    {
        return getPhysicsBody().body == null
            ? 0 : ( getPhysicsBody().body.getPosition().y * Gfx.PPM );
    }

    @Override
    public void dispose()
    {
        Arrays.fill( animFrames, null );

        gid          = null;
        type         = null;
        entityAction = null;
        sprite       = null;
        direction    = null;
        lookingAt    = null;
        distance     = null;
        speed        = null;
        initXYZ      = null;
        animation    = null;
        animFrames   = null;
    }
}

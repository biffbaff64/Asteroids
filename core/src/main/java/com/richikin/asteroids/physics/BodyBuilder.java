package com.richikin.asteroids.physics;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.utils.Trace;

public class BodyBuilder
{
    public BodyBuilder()
    {
    }

    public Body newBody( Rectangle rectangle, short bodyCategory, short collidesWith, PhysicsBodyType bodyType )
    {
        Body             body;
        B2BodyDescriptor descriptor = new B2BodyDescriptor();

        descriptor.bodyType = bodyType;

        switch ( bodyType )
        {
            case DYNAMIC:
            case DYNAMIC_SENSOR:
            case DYNAMIC_BOUNCY:
            case KINEMATIC:
            case KINEMATIC_SENSOR:
            {
                descriptor.shape = createPolygonShape( rectangle );
                descriptor.density = B2DConstants.DEFAULT_DENSITY;

                if ( bodyType == PhysicsBodyType.DYNAMIC_BOUNCY )
                {
                    descriptor.friction    = B2DConstants.LOW_FRICTION;
                    descriptor.restitution = B2DConstants.HIGH_RESTITUTION;
                }
                else
                {
                    descriptor.friction    = B2DConstants.DEFAULT_FRICTION;
                    descriptor.restitution = B2DConstants.LOW_RESTITUTION;
                }

                descriptor.filter = new CollisionFilter
                    (
                        bodyCategory,
                        collidesWith,
                        ( ( bodyType == PhysicsBodyType.DYNAMIC_SENSOR )
                            || ( bodyType == PhysicsBodyType.KINEMATIC_SENSOR ) )
                    );

                if ( ( bodyType == PhysicsBodyType.KINEMATIC )
                    || ( bodyType == PhysicsBodyType.KINEMATIC_SENSOR ) )
                {
                    body = createKinematicBody( rectangle, descriptor );
                }
                else
                {
                    body = createDynamicBox( rectangle, descriptor );
                }
            }
            break;

            case DYNAMIC_HEAVY:
            {
                descriptor.shape = createPolygonShape( rectangle );
                descriptor.density = B2DConstants.FULL_DENSITY;
                descriptor.friction = B2DConstants.FULL_FRICTION;
                descriptor.restitution = B2DConstants.ZERO_RESTITUTION;
                descriptor.filter = new CollisionFilter
                    (
                        bodyCategory,
                        collidesWith,
                        false
                    );
                body = createDynamicBox( rectangle, descriptor );
            }
            break;

            case DYNAMIC_PUSHABLE:
            {
                descriptor.shape = createPolygonShape( rectangle );
                descriptor.density = B2DConstants.DEFAULT_DENSITY;
                descriptor.friction = B2DConstants.MEDIUM_LOW_FRICTION;
                descriptor.restitution = B2DConstants.LOW_RESTITUTION;
                descriptor.filter = new CollisionFilter
                    (
                        bodyCategory,
                        collidesWith,
                        false
                    );
                body = createDynamicBox( rectangle, descriptor );
            }
            break;

            case DYNAMIC_CIRCLE:
            case DYNAMIC_CIRCLE_SENSOR:
            {
                Circle circle = new Circle();
                circle.setPosition( rectangle.getX(), rectangle.getY() );
                circle.setRadius( ( rectangle.getWidth() / 2 ) / Gfx.PPM );
                descriptor.shape = createCircleShape( circle );
                descriptor.density = B2DConstants.DEFAULT_DENSITY;
                descriptor.friction = B2DConstants.LOW_FRICTION;
                descriptor.restitution = B2DConstants.MEDIUM_LOW_RESTITUTION;
                descriptor.filter = new CollisionFilter
                    (
                        bodyCategory,
                        collidesWith,
                        ( bodyType == PhysicsBodyType.DYNAMIC_CIRCLE_SENSOR )
                    );
                body = createDynamicCircle( circle, descriptor );
            }
            break;

            case STATIC:
            case STATIC_SENSOR:
            {
                descriptor.shape = createPolygonShape( rectangle );
                descriptor.density = B2DConstants.FULL_DENSITY;
                descriptor.friction = B2DConstants.FULL_FRICTION;
                descriptor.restitution = B2DConstants.MEDIUM_LOW_RESTITUTION;
                descriptor.filter = new CollisionFilter
                    (
                        bodyCategory,
                        collidesWith,
                        ( bodyType == PhysicsBodyType.STATIC_SENSOR )
                    );
                body = createStaticBody( rectangle, descriptor );
            }
            break;

            default:
            {
                Trace.err( "UNKNOWN BODY TYPE SPECIFIED: ", bodyType );
                body = null;
            }
            break;
        }

        descriptor.dispose();

        return body;
    }

    /**
     * Creates a Dynamic Box2D body which can be assigned to a GdxSprite.
     * <p>
     * Dynamic bodies are objects which move around and are affected by
     * forces and other dynamic, kinematic and static objects. Dynamic
     * bodies are suitable for any object which needs to move and be
     * affected by forces.
     */
    public Body createDynamicBox( Rectangle _rectangle, B2BodyDescriptor descriptor )
    {
        PolygonShape shape   = createPolygonShape( _rectangle );
        BodyDef      bodyDef = createBodyDef( BodyDef.BodyType.DynamicBody, _rectangle );
        FixtureDef fixtureDef = createFixtureDef
            (
                descriptor.filter,
                shape,
                descriptor.density,
                descriptor.friction,
                descriptor.restitution
            );

        Body body = buildBody( bodyDef, fixtureDef );

        if ( fixtureDef.isSensor )
        {
            body.setGravityScale( 0 );
        }

        shape.dispose();

        return body;
    }

    public Body createDynamicCircle( Circle circle, B2BodyDescriptor descriptor )
    {
        BodyDef bodyDef = createBodyDef( BodyDef.BodyType.DynamicBody, circle );
        FixtureDef fixtureDef = createFixtureDef
            (
                descriptor.filter,
                descriptor.shape,
                descriptor.density,
                descriptor.friction,
                descriptor.restitution
            );

        Body body = buildBody( bodyDef, fixtureDef );

        if ( fixtureDef.isSensor )
        {
            body.setGravityScale( 0 );
        }

        return body;
    }

    /**
     * Creates a Kinematic Box2D body which can be assigned to a GdxSprite.
     * <p>
     * Kinematic bodies are somewhat in between static and dynamic bodies.
     * Like static bodies, they do not react to forces, but like dynamic bodies,
     * they do have the ability to move. Kinematic bodies are great for things
     * where you, the programmer, want to be in full control of a body's motion,
     * such as a moving platform in a platform game.
     * It is possible to set the position on a kinematic body directly, but it's
     * usually better to set a velocity instead, and letting Box2D take care of
     * position updates.
     */
    public Body createKinematicBody( Rectangle _rectangle, B2BodyDescriptor descriptor )
    {
        PolygonShape shape   = createPolygonShape( _rectangle );
        BodyDef      bodyDef = createBodyDef( BodyDef.BodyType.KinematicBody, _rectangle );
        FixtureDef fixtureDef = createFixtureDef
            (
                descriptor.filter,
                shape,
                descriptor.density,
                descriptor.friction,
                descriptor.restitution
            );

        Body body = buildBody( bodyDef, fixtureDef );

        if ( fixtureDef.isSensor )
        {
            body.setGravityScale( 0 );
        }

        shape.dispose();

        return body;
    }

    /**
     * Creates a Static Box2D body.
     * <p>
     * Static bodies are objects which do not move and are not affected by forces.
     * Dynamic bodies are affected by static bodies. Static bodies are perfect for
     * ground, walls, and any object which does not need to move. Static bodies
     * require less computing power.
     */
    public Body createStaticBody( Rectangle rectangle, B2BodyDescriptor descriptor )
    {
        PolygonShape shape   = createPolygonShape( rectangle );
        BodyDef      bodyDef = createBodyDef( BodyDef.BodyType.StaticBody, rectangle );
        FixtureDef fixtureDef = createFixtureDef
            (
                descriptor.filter,
                shape,
                descriptor.density,
                descriptor.friction,
                descriptor.restitution
            );
        Body body = buildBody( bodyDef, fixtureDef );

        shape.dispose();

        return body;
    }

    private Body buildBody( BodyDef _bodyDef, FixtureDef _fixtureDef )
    {
        Body body = App.getBox2DHelper().box2DWorld.createBody( _bodyDef );
        body.createFixture( _fixtureDef );

        return body;
    }

    private BodyDef createBodyDef( BodyDef.BodyType bodyType, Rectangle rectangle )
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type          = bodyType;
        bodyDef.fixedRotation = true;

        bodyDef.position.set(
            ( rectangle.getX() + ( rectangle.getWidth() / 2f ) ) / Gfx.PPM,
            ( rectangle.getY() + ( rectangle.getHeight() / 2f ) ) / Gfx.PPM
        );

        return bodyDef;
    }

    @SuppressWarnings("SameParameterValue")
    private BodyDef createBodyDef( BodyDef.BodyType bodyType, Circle circle )
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type          = bodyType;
        bodyDef.fixedRotation = true;

        bodyDef.position.set(
            ( circle.x + ( circle.radius ) ) / Gfx.PPM,
            ( circle.y + ( circle.radius ) ) / Gfx.PPM
        );

        return bodyDef;
    }

    private FixtureDef createFixtureDef( CollisionFilter _filter, Shape _shape, float _density, float _friction,
                                         float _restitution )
    {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape               = _shape;
        fixtureDef.density             = _density;
        fixtureDef.friction            = _friction;
        fixtureDef.restitution         = _restitution;
        fixtureDef.filter.maskBits     = _filter.collidesWith;
        fixtureDef.filter.categoryBits = _filter.bodyCategory;
        fixtureDef.isSensor            = _filter.isSensor;

        return fixtureDef;
    }

    private PolygonShape createPolygonShape( Rectangle _rectangle )
    {
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(
            ( ( _rectangle.getWidth() / 2f ) / Gfx.PPM ),
            ( ( _rectangle.getHeight() / 2f ) / Gfx.PPM )
        );

        return shape;
    }

    private CircleShape createCircleShape( Circle _circle )
    {
        CircleShape shape = new CircleShape();
        shape.setRadius( _circle.radius );

        return shape;
    }
}

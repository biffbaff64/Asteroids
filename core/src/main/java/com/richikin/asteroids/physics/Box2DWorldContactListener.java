package com.richikin.asteroids.physics;

import com.badlogic.gdx.physics.box2d.*;
import com.richikin.asteroids.enums.ActionStates;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.graphics.Gfx;

public class Box2DWorldContactListener implements ContactListener
{
    private static final int _A = 0;
    private static final int _B = 1;

    private final BodyIdentity[] bodyIdentities;

    public Box2DWorldContactListener()
    {
        bodyIdentities = new BodyIdentity[ 2 ];
    }

    /**
     * Called when two fixtures begin to touch.
     */
    @Override
    public void beginContact( Contact contact )
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        bodyIdentities[ _A ] = ( BodyIdentity ) fixtureA.getBody().getUserData();
        bodyIdentities[ _B ] = ( BodyIdentity ) fixtureB.getBody().getUserData();

        for ( BodyIdentity bodyIdentity : bodyIdentities )
        {
            if ( bodyIdentity != null )
            {
                bodyIdentity.entity.getPhysicsBody().contactCount++;
            }
        }

        int catData = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        // Establish the contact edges.
//        checkAABB( fixtureA.getBody(), fixtureB.getBody() );

        checkForSame();

        checkWeaponContact( catData );
        checkPlayerContactWithCollectible( catData );
    }

    /**
     * Called when two fixtures cease to touch.
     */
    @Override
    public void endContact( Contact contact )
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        BodyIdentity bodyIdentityA = ( BodyIdentity ) fixtureA.getBody().getUserData();
        BodyIdentity bodyIdentityB = ( BodyIdentity ) fixtureB.getBody().getUserData();

        if ( bodyIdentityA != null )
        {
            bodyIdentityA.entity.getPhysicsBody().contactCount--;

            if ( bodyIdentityA.entity.getBodyCategory() == Gfx.CAT_MOBILE_ENEMY )
            {
                bodyIdentityA.entity.isTouchingPlayer = false;
            }

            bodyIdentityA.entity.isHittingSame   = false;
            bodyIdentityA.entity.isHittingWeapon = false;
        }

        if ( bodyIdentityB != null )
        {
            bodyIdentityB.entity.getPhysicsBody().contactCount--;

            if ( bodyIdentityB.entity.getBodyCategory() == Gfx.CAT_MOBILE_ENEMY )
            {
                bodyIdentityB.entity.isTouchingPlayer = false;
            }

            bodyIdentityB.entity.isHittingSame   = false;
            bodyIdentityB.entity.isHittingWeapon = false;
        }
    }

    /**
     * This is called after a contact is updated. This allows you to
     * inspect a contact before it goes to the solver. If you are careful,
     * you can modify the contact manifold (e.g. disable contact).
     * A copy of the old manifold is provided so that you can detect
     * changes.
     * <p>
     * Note: this is called only for awake bodies.
     * Note: this is called even when the number of contact points is zero.
     * Note: this is not called for sensors.
     * Note: if you set the number of contact points to zero, you will not
     * get an EndContact callback. However, you may get a BeginContact
     * callback the next step.
     */
    @Override
    public void preSolve( Contact contact, Manifold oldManifold )
    {
    }

    /**
     * This lets you inspect a contact after the solver is finished.
     * This is useful for inspecting impulses.
     * <p>
     * Note: the contact manifold does not include time of impact impulses,
     * which can be arbitrarily large if the sub-step is small. Hence the
     * impulse is provided explicitly in a separate data structure.
     * <p>
     * Note: this is only called for contacts that are touching, solid, and awake.
     */
    @Override
    public void postSolve( Contact contact, ContactImpulse impulse )
    {
    }

    private void checkWeaponContact( int catData )
    {
        if ( ( bodyIdentities[ 0 ] != null ) && ( bodyIdentities[ 1 ] != null ) )
        {
            if ( bodyIdentities[ 0 ].entity.getGID() == GraphicID.G_PLAYER_WEAPON )
            {
                bodyIdentities[ 0 ].entity.setActionState( ActionStates._DYING );
                bodyIdentities[ 1 ].entity.isHittingWeapon = true;
            }
            else if ( bodyIdentities[ 1 ].entity.getGID() == GraphicID.G_PLAYER_WEAPON )
            {
                bodyIdentities[ 1 ].entity.setActionState( ActionStates._DYING );
                bodyIdentities[ 0 ].entity.isHittingWeapon = true;
            }
        }
    }

    /**
     * Check collision between player and collectible items
     */
    private void checkPlayerContactWithCollectible( int catData )
    {
        if ( ( catData & ( Gfx.CAT_PLAYER | Gfx.CAT_COLLECTIBLE ) ) == ( Gfx.CAT_PLAYER | Gfx.CAT_COLLECTIBLE ) )
        {
            if ( ( bodyIdentities[ _A ] != null ) && ( bodyIdentities[ _B ] != null ) )
            {
                if ( bodyIdentities[ _A ].entity.isHittable && bodyIdentities[ _B ].entity.isHittable )
                {
                    for ( BodyIdentity bodyIdentity : bodyIdentities )
                    {
                        if ( bodyIdentity.entity.getType() == GraphicID._PICKUP )
                        {
                            bodyIdentity.entity.setCollecting();
                        }
                    }
                }
            }
        }
    }

    private void checkForSame()
    {
        if ( ( bodyIdentities[ _A ] != null ) && ( bodyIdentities[ _B ] != null ) )
        {
            if ( bodyIdentities[ _A ].entity.gid == bodyIdentities[ _B ].entity.gid )
            {
                bodyIdentities[ _A ].entity.isHittingSame = true;
            }

            if ( bodyIdentities[ _B ].entity.gid == bodyIdentities[ _A ].entity.gid )
            {
                bodyIdentities[ _B ].entity.isHittingSame = true;
            }
        }
    }

    private void checkAABB( Body bodyA, Body bodyB )
    {
    }
}
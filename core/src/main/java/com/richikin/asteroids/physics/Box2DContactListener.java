package com.richikin.asteroids.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Box2DContactListener implements ContactListener
{
    /**
     * @param contact
     */
    @Override
    public void beginContact( Contact contact )
    {

    }

    /**
     * @param contact
     */
    @Override
    public void endContact( Contact contact )
    {

    }

    /**
     * @param contact
     * @param manifold
     */
    @Override
    public void preSolve( Contact contact, Manifold manifold )
    {

    }

    /**
     * @param contact
     * @param contactImpulse
     */
    @Override
    public void postSolve( Contact contact, ContactImpulse contactImpulse )
    {

    }
}

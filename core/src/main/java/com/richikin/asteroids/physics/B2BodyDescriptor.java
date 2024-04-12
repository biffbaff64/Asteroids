package com.richikin.asteroids.physics;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;

public class B2BodyDescriptor implements Disposable
{
    public PhysicsBodyType bodyType;
    public Shape           shape;
    public CollisionFilter filter;
    public float           density;
    public float           friction;
    public float           restitution;

    public B2BodyDescriptor()
    {
    }

    public B2BodyDescriptor( PhysicsBodyType _bodyType,
                             Shape _shape,
                             CollisionFilter _filter,
                             float _density,
                             float _friction,
                             float _restitution )
    {
        bodyType    = _bodyType;
        shape       = _shape;
        filter      = _filter;
        density     = _density;
        friction    = _friction;
        restitution = _restitution;
    }

    @Override
    public void dispose()
    {
        bodyType = null;
        shape = null;
        filter = null;
    }
}

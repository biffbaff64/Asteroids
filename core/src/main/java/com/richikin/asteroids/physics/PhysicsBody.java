package com.richikin.asteroids.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.enums.GraphicID;

public class PhysicsBody implements Disposable
{
    public Body      body;
    public int       index;
    public boolean   isAlive;
    public int       contactCount;
    public short     contactMask;
    public Rectangle bodyBox;
    public GraphicID type;

    public PhysicsBody()
    {
        this( null, 0, false );
    }

    public PhysicsBody( Body body, int index, boolean isAlive )
    {
        this.body         = body;
        this.index        = index;
        this.isAlive      = isAlive;
        this.contactCount = 0;
        this.contactMask  = 0;
        this.bodyBox      = new Rectangle();
        this.type         = GraphicID._ENTITY;
    }

    @Override
    public void dispose()
    {
        body    = null;
        bodyBox = null;
        type    = null;
    }

    @Override
    public String toString()
    {
        return "PhysicsBody["
            + "body=" + body
            + ", index=" + index
            + ", isAlive=" + isAlive
            + ", contactCount=" + contactCount
            + ", [bodyBox=" + bodyBox.toString() + "]]"
            + ", type=" + type;
    }
}

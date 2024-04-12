package com.richikin.asteroids.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.richikin.asteroids.core.App;

public class BodyUtils
{
    public static float getBodyAngle( Body body )
    {
        return (float) Math.atan2( body.getLinearVelocity().y, body.getLinearVelocity().x );
    }

    public static void setBodyAtAngle( Body body, float angle )
    {
        body.setTransform(body.getWorldCenter(), angle - ((float)Math.PI)/2.0f);
    }

    public static void killBody( PhysicsBody physicsBody )
    {
        physicsBody.isAlive = false;
        App.getBox2DHelper().bodiesList.add( physicsBody );
    }
}
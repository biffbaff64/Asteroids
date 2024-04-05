package com.richikin.asteroids.entities;

import com.badlogic.gdx.math.Rectangle;
import com.richikin.asteroids.enums.ActionStates;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.physics.PhysicsBody;

public interface EntityComponent
{
    ActionStates getActionState();

    void setActionState( ActionStates action );

    /**
     * Gets the {@link PhysicsBody} attached to this sprite.
     */
    PhysicsBody getPhysicsBody();

    Rectangle getBodyBox();

    /**
     * Gets the current X position of the {@link PhysicsBody}
     * attached to this sprite.
     */
    float getBodyX();

    /**
     * Gets the current Y position of the {@link PhysicsBody}
     * attached to this sprite.
     */
    float getBodyY();

    void tidy( int index );

    short getBodyCategory();

    short getCollidesWith();

    int getSpriteNumber();

    int getLink();

    void setLink( int lnk );

    boolean isLinked();

    boolean isHittingSame();

    GraphicID getGID();

    GraphicID getType();

    void setDying();

    void dispose();
}
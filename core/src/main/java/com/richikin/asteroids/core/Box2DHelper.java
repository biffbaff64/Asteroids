package com.richikin.asteroids.core;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.physics.BodyBuilder;
import com.richikin.asteroids.physics.Box2DContactListener;
import com.richikin.asteroids.physics.PhysicsBody;
import com.richikin.asteroids.utils.Developer;
import com.richikin.asteroids.utils.Trace;

public class Box2DHelper implements Disposable
{
    public       World                box2DWorld;
    public       Box2DDebugRenderer   b2dr;
    public       Box2DContactListener box2DContactListener;
    public       BodyBuilder          bodyBuilder;
    public       boolean              worldStepEnabled;
    public       boolean              canDrawDebug;
    public final Array< PhysicsBody > bodiesList;

    public Box2DHelper()
    {
        bodiesList = new Array<>();
    }

    public void createWorld()
    {
        Trace.checkPoint();

        box2DWorld = new World
            (
                new Vector2
                    (
                        ( Gfx.WORLD_GRAVITY.x * Gfx.PPM ),
                        ( Gfx.WORLD_GRAVITY.y * Gfx.PPM )
                    ),
                false
            );

        bodyBuilder          = new BodyBuilder();
        box2DContactListener = new Box2DContactListener();

        box2DWorld.setContactListener( box2DContactListener );

        worldStepEnabled = true;
    }

    public void createB2DRenderer()
    {
        b2dr = new Box2DDebugRenderer
            (
                true,
                true,
                true,
                true,
                false,
                true
            );

        canDrawDebug = false;
    }

    public void drawDebugMatrix()
    {
        if ( ( b2dr != null ) && Developer.isDevMode() && canDrawDebug )
        {
            // Care needed here if the viewport sizes for SpriteGameCamera
            // and TiledGameCamera are different.
            Matrix4 debugMatrix = App.getGameRenderer()
                .getSpriteGameCamera()
                .camera
                .combined
                .scale( Gfx.PPM, Gfx.PPM, 0 );

            b2dr.render( box2DWorld, debugMatrix );
        }
    }

    /**
     * Update this games Box2D physics.
     */
    public void worldStep()
    {
        if ( worldStepEnabled && ( box2DWorld != null ) && !App.getAppConfig().gamePaused )
        {
            box2DWorld.step
                (
                    Gfx.STEP_TIME,
                    Gfx.VELOCITY_ITERATIONS,
                    Gfx.POSITION_ITERATIONS
                );
        }

        tidyDeletionList();
    }

    /**
     * If any PhysicsBody#isAlive is FALSE then destroy the associated
     * Box2D Body. The PhysicsBody should remain intact, allowing for
     * a new body to be allocated if required.
     */
    public void tidyDeletionList()
    {
        if ( !box2DWorld.isLocked() && ( bodiesList.size > 0 ) )
        {
            Array.ArrayIterator< PhysicsBody > iterator = new Array.ArrayIterator<>( bodiesList );

            while ( iterator.hasNext() )
            {
                PhysicsBody physicsBody = iterator.next();

                if ( ( physicsBody != null ) && ( physicsBody.body != null ) && !physicsBody.isAlive )
                {
                    box2DWorld.destroyBody( physicsBody.body );

                    physicsBody.body = null;
                }
            }
        }
    }

    public void activate()
    {
        worldStepEnabled = true;
    }

    public void deActivate()
    {
        worldStepEnabled = false;
    }

    @Override
    public void dispose()
    {
        Trace.checkPoint();

        b2dr.dispose();
        b2dr = null;

        box2DContactListener = null;
        bodyBuilder          = null;

        box2DWorld.dispose();
        box2DWorld = null;
    }
}

package com.richikin.asteroids.core;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.config.Settings;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.physics.BodyBuilder;
import com.richikin.asteroids.physics.Box2DContactListener;
import com.richikin.asteroids.utils.Developer;
import com.richikin.asteroids.utils.Trace;

public class Box2DHelper implements Disposable
{
    public World                box2DWorld;
    public Box2DDebugRenderer   b2dr;
    public Box2DContactListener box2DContactListener;
    public BodyBuilder          bodyBuilder;

    public Box2DHelper()
    {
        Trace.checkPoint();

        if ( App.settings.isEnabled( Settings._BOX2D_PHYSICS ) )
        {
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
        }
    }

    public void createB2DRenderer()
    {
        if ( Developer.isDevMode() )
        {
            Trace.checkPoint();

            b2dr = new Box2DDebugRenderer
                (
                    true,
                    true,
                    true,
                    true,
                    false,
                    true
                );
        }
    }

    public void drawDebugMatrix()
    {
        if ( App.settings.isEnabled( Settings._BOX2D_PHYSICS ) )
        {
            if ( b2dr != null )
            {
                // Care needed here if the viewport sizes for SpriteGameCamera
                // and TiledGameCamera are different.
                Matrix4 debugMatrix = App.gameRenderer.getSpriteGameCamera().camera.combined.scale( Gfx.PPM, Gfx.PPM, 0 );

                if ( debugMatrix != null )
                {
                    b2dr.render( box2DWorld, debugMatrix );
                }
            }
        }
    }

    public void worldStep()
    {
        if ( box2DWorld != null )
        {
            box2DWorld.step( Gfx.STEP_TIME, Gfx.VELOCITY_ITERATIONS, Gfx.POSITION_ITERATIONS );
        }
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

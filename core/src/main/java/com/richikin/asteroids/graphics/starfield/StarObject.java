package com.richikin.asteroids.graphics.starfield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.graphics.Gfx;

public class StarObject implements Disposable
{
    private static final float INITIAL_DEPTH       = 100.0f;
    private static final float FINAL_DEPTH         = 1000.0f;
    private static final float MINIMUM_VELOCITY    = 0.5f;
    private static final float MAXIMUM_VELOCITY    = 5.0f;
    private static final float MAXIMUM_STAR_RADIUS = 12.0f;

    private Vector3       position;
    private Vector3       velocity;
    private TextureRegion region;

    public StarObject()
    {
        position = new Vector3();
        velocity = new Vector3();
        region   = App.getAssets().getStarfieldObject();

        resetPosition();
    }

    public void update( float speed )
    {
        if ( ( position.z < 0 ) || ( position.z > FINAL_DEPTH )
            || ( position.y < -Gfx.GAME_VIEW_HEIGHT ) || ( position.y > Gfx.GAME_VIEW_HEIGHT )
            || ( position.x < -Gfx.GAME_VIEW_WIDTH ) || ( position.x > Gfx.GAME_VIEW_WIDTH ) )
        {
            resetPosition();
        }

        float deltaTime = Gdx.graphics.getDeltaTime();

        moveStar( ( velocity.x * speed ) * deltaTime, ( velocity.y * speed ) * deltaTime, ( velocity.z * speed ) * deltaTime );
    }

    public void render( float speed )
    {
        update( speed );

        float x = ( position.x / position.z ) * ( Gfx.GAME_VIEW_WIDTH * 0.5f );
        float y = ( position.y / position.z ) * ( Gfx.GAME_VIEW_HEIGHT * 0.5f );

        float radius = ( ( MAXIMUM_STAR_RADIUS - ( ( position.z * MAXIMUM_STAR_RADIUS ) * 0.0025f ) ) * velocity.z ) * 0.2f;

        App.getSpriteBatch().draw( region, x, y, radius, radius );
    }

    private void resetPosition()
    {
        position.x = MathUtils.random( -Gfx.GAME_VIEW_WIDTH, Gfx.GAME_VIEW_WIDTH );
        position.y = MathUtils.random( -Gfx.GAME_VIEW_HEIGHT, Gfx.GAME_VIEW_HEIGHT );
        position.z = MathUtils.random( INITIAL_DEPTH, FINAL_DEPTH );
        velocity.z = MathUtils.random( MINIMUM_VELOCITY, MAXIMUM_VELOCITY );
    }

    private void moveStar( float x, float y, float z )
    {
        position.sub( x, y, z );
    }

    @Override
    public void dispose()
    {
        position = null;
        velocity = null;
        region   = null;
    }
}
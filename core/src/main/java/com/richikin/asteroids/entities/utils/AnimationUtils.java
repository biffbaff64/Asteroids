package com.richikin.asteroids.entities.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.entities.objects.GdxSprite;
import com.richikin.asteroids.utils.Trace;

public class AnimationUtils
{
    /**
     * Gets an animation frame based on the supplied animation time.
     */
    public TextureRegion getKeyFrame( Animation<? extends TextureRegion> animation, float elapsedTime, boolean looping )
    {
        return animation.getKeyFrame( elapsedTime, looping );
    }

    public Animation<TextureRegion> createAnimation( String filename, TextureRegion[] destinationFrames, int frameCount, Animation.PlayMode playmode )
    {
        Animation<TextureRegion> animation;

        try
        {
            TextureRegion textureRegion = App.getAssets().getAnimationRegion( filename );

            TextureRegion[] tmpFrames = textureRegion.split(
                ( textureRegion.getRegionWidth() / frameCount ),
                textureRegion.getRegionHeight()
            )[ 0 ];

            System.arraycopy( tmpFrames, 0, destinationFrames, 0, frameCount );

            animation = new Animation<>( 0.75f / 6f, tmpFrames );
            animation.setPlayMode( playmode );
        }
        catch ( NullPointerException npe )
        {
            Trace.dbg( "Creating animation from " + filename + " failed!" );

            animation = null;
        }

        return animation;
    }

    public void randomiseAnimTime( GdxSprite sprite )
    {
        int counter = MathUtils.random( 5 );

        for ( int i = 0; i < counter; i++ )
        {
            sprite.elapsedAnimTime += Gdx.graphics.getDeltaTime();
        }
    }
}

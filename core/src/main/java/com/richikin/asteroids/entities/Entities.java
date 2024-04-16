package com.richikin.asteroids.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.richikin.asteroids.entities.objects.SpriteDescriptor;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.graphics.GameAssets;
import com.richikin.asteroids.utils.Trace;
import com.richikin.asteroids.utils.Vec2;

public class Entities
{
    /**
     * Table of SpriteDescriptors describing the basic properties
     * of entities. Used to create placement tiles.
     */
    public final SpriteDescriptor[] entityList =
        {
            // -----------------------------------------------------
            // Player
            new SpriteDescriptor
                (
                    "Player",
                    GraphicID.G_PLAYER, GraphicID._MAIN,
                    GameAssets.PLAYER, GameAssets.PLAYER_FRAMES,
                    new Vec2( 64, 64 ),
                    Animation.PlayMode.LOOP
                ),

            new SpriteDescriptor
                (
                    "Bullet",
                    GraphicID.G_PLAYER_WEAPON, GraphicID._MAIN,
                    GameAssets.BULLET, GameAssets.BULLET_FRAMES,
                    new Vec2( 15, 15 ),
                    Animation.PlayMode.LOOP
                ),
            // -----------------------------------------------------
            // Asteroids
            new SpriteDescriptor
                (
                    "Big Asteroid",
                    GraphicID.G_BIG_ASTEROID, GraphicID._ENEMY,
                    GameAssets.ASTEROID_LARGE, GameAssets.ASTEROID_FRAMES,
                    new Vec2( 96, 96 ),
                    Animation.PlayMode.NORMAL
                ),
            new SpriteDescriptor
                (
                    "Medium Asteroid",
                    GraphicID.G_MID_ASTEROID, GraphicID._ENEMY,
                    GameAssets.ASTEROID_MEDIUM, GameAssets.ASTEROID_FRAMES,
                    new Vec2( 64, 64 ),
                    Animation.PlayMode.NORMAL
                ),
            new SpriteDescriptor
                (
                    "Small Asteroid",
                    GraphicID.G_SMALL_ASTEROID, GraphicID._ENEMY,
                    GameAssets.ASTEROID_SMALL, GameAssets.ASTEROID_FRAMES,
                    new Vec2( 31, 31 ),
                    Animation.PlayMode.NORMAL
                ),

            // -----------------------------------------------------
            // UFO
            new SpriteDescriptor
                (
                    "Ufo",
                    GraphicID.G_UFO, GraphicID._ENEMY,
                    GameAssets.UFO, GameAssets.UFO_FRAMES,
                    new Vec2( 47, 39 ),
                    Animation.PlayMode.NORMAL
                ),
        };

    // ------------------------------------------------------------------------

    public Vec2 getAssetSize( GraphicID _gid )
    {
        Vec2 size = new Vec2();

        for ( final SpriteDescriptor descriptor : entityList )
        {
            if ( descriptor._GID == _gid )
            {
                size = descriptor._SIZE;
            }
        }

        if ( size.isEmpty() )
        {
            Trace.dbg( "***** SIZE FOR " + _gid + " NOT FOUND! *****" );
        }

        return size;
    }

    public int getDescriptorIndex( GraphicID gid )
    {
        int     index      = 0;
        int     defsIndex  = 0;
        boolean foundIndex = false;

        for ( SpriteDescriptor descriptor : entityList )
        {
            if ( descriptor._GID == gid )
            {
                defsIndex  = index;
                foundIndex = true;
            }

            index++;
        }

        if ( !foundIndex )
        {
            Trace.dbg( "INDEX FOR " + gid + " NOT FOUND!!!" );
        }

        return defsIndex;
    }

    public SpriteDescriptor getDescriptor( GraphicID gid )
    {
        SpriteDescriptor descriptor = new SpriteDescriptor();

        descriptor.set( entityList[ getDescriptorIndex( gid ) ] );

        return descriptor;
    }
}

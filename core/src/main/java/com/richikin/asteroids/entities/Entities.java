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

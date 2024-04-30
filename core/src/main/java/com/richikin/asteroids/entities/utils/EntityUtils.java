package com.richikin.asteroids.entities.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.entities.EntityComponent;
import com.richikin.asteroids.entities.objects.GdxSprite;
import com.richikin.asteroids.enums.ActionStates;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.graphics.Gfx;
import org.jetbrains.annotations.NotNull;

public class EntityUtils
{
    public EntityUtils()
    {
    }

    /**
     * Resets all members of entityMap to their initXY positions
     */
    public void resetAllPositions()
    {
        if ( App.getEntityData().getEntityMap() != null )
        {
            GdxSprite entity;

            for ( int i = 0; i < App.getEntityData().getEntityMap().size; i++ )
            {
                if ( App.getEntityData().getEntity( i ).getType() == GraphicID._MAIN )
                {
                    entity = ( GdxSprite ) App.getEntityData().getEntity( i );

                    entity.getPhysicsBody().body.setTransform
                        (
                            entity.initXYZ.x,
                            entity.initXYZ.y,
                            entity.getPhysicsBody().body.getAngle()
                        );
                }
            }
        }
    }

    /**
     * Fetch an initial Z position for the specified ID.
     *
     * @param graphicID The GraphicID.
     * @return Z position range is between 0 and Gfx._MAXIMUM_Z_DEPTH.
     */
    public int getInitialZPosition( @NotNull GraphicID graphicID )
    {
        int zed;

        switch ( graphicID )
        {
            case G_BIG_ASTEROID:
            case G_MID_ASTEROID:
            case G_SMALL_ASTEROID:
            case G_UFO:
            {
                zed = 3;
            }
            break;

            case G_PLAYER_WEAPON:
            case G_PLAYER:
            {
                zed = 1;
            }
            break;

            case G_MESSAGE_BUBBLE:
            case G_EXPLOSION12:
            case G_EXPLOSION32:
            case G_EXPLOSION64:
            case G_EXPLOSION128:
            case G_EXPLOSION256:
            {
                zed = 0;
            }
            break;

            default:
            {
                zed = Gfx.MAXIMUM_Z_DEPTH + 1;
            }
            break;
        }

        return zed;
    }

    public boolean isOnScreen( @NotNull GdxSprite spriteObject )
    {
        return App.getGameRenderer().getGameWindow().overlaps( spriteObject.sprite.getBoundingRectangle() );
    }

    public void tidy()
    {
        for ( int i = 0; i < App.getEntityData().getEntityMap().size; i++ )
        {
            if ( App.getEntityData().getEntity( i ).getActionState() == ActionStates._DEAD )
            {
                App.getEntityData().removeEntityAt( i );
            }
        }
    }

    public void killAllExcept( GraphicID gidToLeave )
    {
        for ( int i = 0; i < App.getEntityData().getEntityMap().size; i++ )
        {
            if ( App.getEntityData().getEntity( i ).getGID() != gidToLeave )
            {
                App.getEntityData().getEntity( i ).setActionState( ActionStates._DEAD );
                App.getEntityData().getEntity( i ).getPhysicsBody().isAlive = false;
                App.getBox2DHelper().bodiesList.add( App.getEntityData().getEntity( i ).getPhysicsBody() );
            }
        }

        tidy();
    }

    /**
     * Gets a random sprite from the entity map, making
     * sure to not return the specified sprite.
     */
    public GdxSprite getRandomSprite( @NotNull GdxSprite oneToAvoid )
    {
        GdxSprite randomSprite;

        do
        {
            randomSprite = ( GdxSprite ) App.getEntityData().getEntity( MathUtils.random( App.getEntityData().getEntityMap().size - 1 ) );
        }
        while ( ( randomSprite.gid == oneToAvoid.gid )
            || ( randomSprite.sprite == null )
            || ( randomSprite.getSpriteNumber() == oneToAvoid.getSpriteNumber() ) );

        return randomSprite;
    }

    /**
     * Finds the nearest sprite of type gid to the player.
     */
    public GdxSprite findNearest( GraphicID gid )
    {
        GdxSprite distantSprite = findFirstOf( gid );

        if ( distantSprite != null )
        {
            Vector2 playerPos  = new Vector2( App.getPlayer().sprite.getX(), App.getPlayer().sprite.getY() );
            Vector2 distantPos = new Vector2( distantSprite.sprite.getX(), distantSprite.sprite.getY() );
            Vector2 spritePos  = new Vector2();

            float distance = playerPos.dst( distantPos );

            for ( EntityComponent entity : App.getEntityData().getEntityMap() )
            {
                if ( entity.getGID() == gid )
                {
                    GdxSprite gdxSprite = ( GdxSprite ) entity;

                    spritePos.set( gdxSprite.sprite.getX(), gdxSprite.sprite.getY() );

                    float tempDistance = playerPos.dst( spritePos );

                    if ( Math.abs( tempDistance ) < Math.abs( distance ) )
                    {
                        distance      = tempDistance;
                        distantSprite = gdxSprite;
                    }
                }
            }
        }

        return distantSprite;
    }

    /**
     * Finds the furthest sprite of type gid to the player.
     */
    public GdxSprite getDistantSprite( GraphicID targetGID )
    {
        GdxSprite distantSprite = App.getPlayer();

        Vector2 playerPos  = new Vector2( App.getPlayer().sprite.getX(), App.getPlayer().sprite.getY() );
        Vector2 distantPos = new Vector2();
        Vector2 spritePos  = new Vector2();

        float distance = 0;

        for ( EntityComponent entity : App.getEntityData().getEntityMap() )
        {
            GdxSprite gdxSprite = ( GdxSprite ) entity;

            spritePos.set( gdxSprite.sprite.getX(), gdxSprite.sprite.getY() );

            float tempDistance = playerPos.dst( spritePos );

            if ( Math.abs( tempDistance ) > Math.abs( distance ) )
            {
                distance      = tempDistance;
                distantSprite = gdxSprite;
            }
        }

        return distantSprite;
    }

    public GdxSprite findFirstOf( final GraphicID gid )
    {
        GdxSprite gdxSprite = null;

        for ( EntityComponent entity : App.getEntityData().getEntityMap() )
        {
            if ( entity.getGID() == gid )
            {
                gdxSprite = ( GdxSprite ) entity;
                break;
            }
        }

        return gdxSprite;
    }

    public GdxSprite findLastOf( final GraphicID gid )
    {
        GdxSprite gdxSprite = null;

        for ( EntityComponent entity : App.getEntityData().getEntityMap() )
        {
            if ( entity.getGID() == gid )
            {
                gdxSprite = ( GdxSprite ) entity;
            }
        }

        return gdxSprite;
    }

    public int findNumberOfGid( final GraphicID gid )
    {
        int count = 0;

        for ( EntityComponent entity : App.getEntityData().getEntityMap() )
        {
            if ( entity.getGID() == gid )
            {
                count++;
            }
        }

        return count;
    }

    public int findNumberOfType( final GraphicID type )
    {
        int count = 0;

        for ( EntityComponent entity : App.getEntityData().getEntityMap() )
        {
            if ( entity.getType() == type )
            {
                count++;
            }
        }

        return count;
    }

    public boolean canRandomlyTurn( EntityComponent entity )
    {
        return ( ( MathUtils.random( 100 ) == 5 )
            && ( entity.getPhysicsBody().contactCount > 1 ) );
    }

    public int getHittingSameCount( GraphicID gid )
    {
        int count = 0;

        for ( EntityComponent entity : App.getEntityData().getEntityMap() )
        {
            if ( ( entity.getGID() == gid ) && entity.isHittingSame() )
            {
                count++;
            }
        }

        return count;
    }
}

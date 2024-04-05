package com.richikin.asteroids.entities;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.utils.Trace;

public class EntityData implements Disposable
{
    private Array< EntityComponent >        entityMap;
    private Array< EntityManagerComponent > managerList;

    public EntityData()
    {
        entityMap   = new Array<>();
        managerList = new Array<>();
    }

    /**
     * Adds an IEntityComponent to the entityMap.
     */
    public void addEntity( EntityComponent entity )
    {
        if ( entity != null )
        {
            entityMap.add( entity );
        }
        else
        {
            throw new NullPointerException
                (
                    "***** Attempt to add NULL Object, EntityMap current size: "
                        + entityMap.size
                );
        }
    }

    /**
     * Add an IEntityManagerComponent to the manager list.
     *
     * @param manager - The entity manager to add.
     */
    public void addManager( EntityManagerComponent manager )
    {
        if ( manager != null )
        {
            managerList.add( manager );
        }
        else
        {
            throw new NullPointerException
                (
                    "***** Attempt to add NULL Object, ManagerList current size: "
                        + managerList.size
                );
        }
    }

    /**
     * Gets the entity from entityMap at the supplied index.
     */
    public EntityComponent getEntity( int index )
    {
        return entityMap.get( index );
    }

    /**
     * Gets the entity manager at the specified array index.
     */
    public EntityManagerComponent getManager( GraphicID managerID )
    {
        int index = 0;

        for ( int i = 0; i < managerList.size; i++ )
        {
            if ( managerList.get( i ).getGID() == managerID )
            {
                index = i;
            }
        }

        return managerList.get( index );
    }

    /**
     * Remove the entity at the supplied index from entityMap.
     */
    public void removeEntityAt( int index )
    {
        entityMap.removeIndex( index );
    }

    /**
     * Removes the specified manager from the manager array.
     * @param manager The manager to remove.
     */
    public void removeManager( EntityManagerComponent manager )
    {
        if ( managerList.removeValue( manager, false ) )
        {
            Trace.err( "FAILED to remove ", manager.getGID().name() );
        }
    }

    public Array< EntityComponent > getEntityMap()
    {
        return entityMap;
    }

    public Array< EntityManagerComponent > getManagerList()
    {
        return managerList;
    }

    @Override
    public void dispose()
    {
        Trace.checkPoint();

        for ( EntityComponent component : entityMap )
        {
            component.dispose();
        }

        for ( EntityManagerComponent component : managerList )
        {
            component.dispose();
        }

        entityMap.clear();
        managerList.clear();

        entityMap   = null;
        managerList = null;
    }
}

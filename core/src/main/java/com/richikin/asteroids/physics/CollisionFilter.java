package com.richikin.asteroids.physics;

public class CollisionFilter
{
    public short   bodyCategory;
    public short   collidesWith;
    public boolean isSensor;
    public String  identity = "undefined";

    public CollisionFilter( short _bodyCategory, short _collidesWith, boolean _sensor )
    {
        this.bodyCategory = _bodyCategory;
        this.collidesWith = _collidesWith;
        this.isSensor     = _sensor;
    }

    public CollisionFilter( String _id, short _bodyCategory, short _collidesWith, boolean _sensor )
    {
        this( _bodyCategory, _collidesWith, _sensor );

        this.identity = _id;
    }
}

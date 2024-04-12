package com.richikin.asteroids.physics;

import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.entities.objects.GdxSprite;

public class BodyIdentity
{
    public final GraphicID gid;
    public final GraphicID type;
    public final GdxSprite entity;

    public BodyIdentity( GdxSprite entity, GraphicID gid, GraphicID type )
    {
        this.entity = entity;
        this.gid    = gid;
        this.type   = type;
    }

    @Override
    public String toString()
    {
        return "BodyIdentity{" +
            "gid=" + gid +
            ", type=" + type +
            ", entity=" + entity +
            '}';
    }
}

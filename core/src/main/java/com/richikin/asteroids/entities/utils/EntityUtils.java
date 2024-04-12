package com.richikin.asteroids.entities.utils;

import com.richikin.asteroids.entities.objects.GdxSprite;
import com.richikin.asteroids.enums.GraphicID;

public class EntityUtils
{
    public float getInitialZPosition( GraphicID gid ) { return 0; }

    public boolean isOnScreen( GdxSprite entity ) { return true; }
}

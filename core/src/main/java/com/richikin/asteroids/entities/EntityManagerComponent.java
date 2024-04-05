package com.richikin.asteroids.entities;

import com.richikin.asteroids.enums.GraphicID;

public interface EntityManagerComponent
{
    void init();

    void update();

    void create();

    void free();

    void reset();

    int getActiveCount();

    void setActiveCount( int numActive );

    void addMaxCount( int add );

    void setMaxCount( int max );

    GraphicID getGID();

    void dispose();
}

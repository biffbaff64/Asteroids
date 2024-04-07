package com.richikin.asteroids.utils;

public interface ItemInterface
{
    void setToMaximum();

    void setToMinimum();

    boolean isFull();

    boolean isEmpty();

    boolean hasRoom();

    boolean isOverflowing();

    boolean isUnderflowing();

    void refill();
}

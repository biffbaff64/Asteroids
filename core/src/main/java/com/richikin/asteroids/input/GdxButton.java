package com.richikin.asteroids.input;

public interface GdxButton
{
    boolean checkPress( int touchX, int touchY );

    boolean checkRelease( int touchX, int touchY );

    void press();

    void pressConditional( boolean condition );

    boolean isPressed();

    boolean isDisabled();

    void setDisabled( boolean _state );

    boolean isDrawable();

    void setDrawable( boolean _state );

    void release();

    void toggleDisabled();

    void togglePressed();

    Type getType();

    enum Type
    {
        _SWITCH,
        _GAME_BUTTON,
        _ANIMATED_BUTTON,
        _BUTTON_REGION,
    }
}

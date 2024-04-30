package com.richikin.asteroids.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.physics.Dir;
import com.richikin.asteroids.utils.Trace;

public class InputManager implements Disposable
{
    public Array< GdxButton > gameButtons;
    public Vector2            mousePosition;
    public Vector2            mouseWorldPosition;
    public Keyboard           keyboard;
    public TouchScreen        touchScreen;
    public InputMultiplexer   inputMultiplexer;
    public Dir                currentRegisteredDirection;
    public Dir                lastRegisteredDirection;
    public float              horizontalValue;
    public float              verticalValue;
    public GameController     gameController;
    public VirtualJoystick    virtualJoystick;

    public InputManager()
    {
    }

    public void setup()
    {
        Trace.checkPoint();

        currentRegisteredDirection = Dir._STILL;
        lastRegisteredDirection    = Dir._STILL;

        if ( App.getAppConfig().availableInputs.contains( ControllerType._JOYSTICK, true ) )
        {
            Trace.dbg( "Initialising _VIRTUAL Controller" );

            virtualJoystick = new VirtualJoystick();
            virtualJoystick.create();
        }

        mousePosition      = new Vector2();
        mouseWorldPosition = new Vector2();
        keyboard           = new Keyboard();
        touchScreen        = new TouchScreen();
        gameButtons        = new Array<>();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor( App.getStage() );
        inputMultiplexer.addProcessor( keyboard );

        Gdx.input.setCatchKey( Input.Keys.BACK, true );
        Gdx.input.setCatchKey( Input.Keys.MENU, true );
        Gdx.input.setInputProcessor( inputMultiplexer );

        App.getAppConfig().addBackButton( "button_backToMenu", "button_backToMenu_pressed" );
    }

    public Array< GdxButton > getGameButtons()
    {
        return gameButtons;
    }

    public VirtualJoystick getVirtualJoystick()
    {
        return virtualJoystick;
    }

    public TouchScreen getTouchScreen()
    {
        return touchScreen;
    }

    public Keyboard getKeyboard()
    {
        return keyboard;
    }

    public Vector2 getMousePosition()
    {
        return mousePosition;
    }

    public Vector2 getMouseWorldPosition()
    {
        return mouseWorldPosition;
    }

    public Dir getCurrentRegisteredDirection()
    {
        return currentRegisteredDirection;
    }

    public Dir getLastRegisteredDirection()
    {
        return lastRegisteredDirection;
    }

    public void setCurrentRegisteredDirection( Dir dir )
    {
        currentRegisteredDirection = dir;
    }

    public void setLastRegisteredDirection( Dir dir )
    {
        lastRegisteredDirection = dir;
    }

    public float getHorizontalValue()
    {
        return horizontalValue;
    }

    public void setHorizontalValue( float hval )
    {
        horizontalValue = hval;
    }

    public void clearHorizontalValue()
    {
        horizontalValue = 0;
    }

    public float getVerticalValue()
    {
        return verticalValue;
    }

    public void setVerticalValue( float vval )
    {
        verticalValue = vval;
    }

    public void clearVerticalValue()
    {
        verticalValue = 0;
    }

    public float getControllerXPercentage()
    {
        float xPercent = 0.0f;

        if ( App.getHud() != null )
        {
            if ( App.getAppConfig().availableInputs.contains( ControllerType._JOYSTICK, true ) )
            {
                if ( virtualJoystick != null )
                {
                    xPercent = virtualJoystick.getXPercent();
                }
                else
                {
                    if ( App.getAppConfig().availableInputs.contains( ControllerType._EXTERNAL, true ) )
                    {
                        xPercent = horizontalValue;
                    }
                    else
                    {
                        if ( App.getAppConfig().availableInputs.contains( ControllerType._KEYBOARD, true ) )
                        {
                            xPercent = horizontalValue;
                        }
                    }
                }
            }
        }

        return xPercent;
    }

    public float getControllerYPercentage()
    {
        float yPercent = 0.0f;

        if ( App.getHud() != null )
        {
            if ( App.getAppConfig().availableInputs.contains( ControllerType._JOYSTICK, true ) )
            {
                if ( virtualJoystick != null )
                {
                    yPercent = virtualJoystick.getYPercent();
                }
            }
            else
            {
                if ( App.getAppConfig().availableInputs.contains( ControllerType._EXTERNAL, true )
                    && gameController != null )
                {
                    yPercent = verticalValue;

                    switch ( gameController.controller.getName() )
                    {
                        case "PC/PS3/Android":
                        case "Controller (Inno GamePad..)":
                            yPercent *= -1;
                            break;
                        default:
                            break;
                    }
                }
                else
                {
                    if ( App.getAppConfig().availableInputs.contains( ControllerType._KEYBOARD, true ) )
                    {
                        yPercent = App.getPlayer().lookingAt.y;
                    }
                }
            }
        }

        return yPercent;
    }

    @Override
    public void dispose()
    {
        inputMultiplexer.clear();
        inputMultiplexer = null;

        mousePosition      = null;
        mouseWorldPosition = null;
        keyboard           = null;
        touchScreen        = null;
    }
}

package com.richikin.asteroids.entities.actors;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.input.ControllerType;
import com.richikin.asteroids.input.DirectionMap;
import com.richikin.asteroids.input.PlayerButton;
import com.richikin.asteroids.physics.Dir;
import com.richikin.asteroids.physics.Movement;

public class ButtonInputHandler implements Disposable
{
    private Array< PlayerButton >               playerButtons;
    private Array.ArrayIterator< PlayerButton > abxyButtonArrayIterator;

    /**
     * Handles Player button actions
     */
    public ButtonInputHandler()
    {
        playerButtons = new Array<>();

        playerButtons.add( new RotateLeftActions() );   // A - Rotate Left
        playerButtons.add( new RotateRightActions() );  // B - Rotate Right
        playerButtons.add( new ShootActions() );        // X - Shoot
        playerButtons.add( new ThrustActions() );       // Y - Thrust

        abxyButtonArrayIterator = new Array.ArrayIterator<>( playerButtons );
    }

    public void checkButtons()
    {
        abxyButtonArrayIterator.reset();

        while ( abxyButtonArrayIterator.hasNext() )
        {
            abxyButtonArrayIterator.next().update();
        }

        // PC Keyboard.
        if ( App.getAppConfig().availableInputs.contains( ControllerType._KEYBOARD, true ) )
        {
            setDirection( App.getInputManager().getLastRegisteredDirection() );
        }
        else
        {
            // External XBox/Playstation style controllers.
            if ( App.getAppConfig().availableInputs.contains( ControllerType._EXTERNAL, true ) )
            {
                setDirection( App.getInputManager().getLastRegisteredDirection() );
            }
            else
            {
                // On-Screen virtual controller, usually on phone/tablet builds, but
                // sometimes used on desktop builds for testing.
                if ( App.getAppConfig().availableInputs.contains( ControllerType._JOYSTICK, true ) )
                {
                    if ( App.getInputManager().getVirtualJoystick() != null )
                    {
                        // Updates button presses depending upon joystick knob position
                        App.getInputManager().getVirtualJoystick().update();

                        setDirection( App.getInputManager().getLastRegisteredDirection() );
                    }
                }
            }
        }

        // Check for rotating LEFT
        if ( App.getPlayer().direction.x == Movement.DIRECTION_LEFT )
        {
            App.getPlayer().isRotating = true;
            App.getPlayer().rotateSpeed = 3.0f;
        }
        // Check for rotating RIGHT
        else if ( App.getPlayer().direction.x == Movement.DIRECTION_RIGHT )
        {
            App.getPlayer().isRotating = true;
            App.getPlayer().rotateSpeed = -3.0f;
        }
        else
        {
            App.getPlayer().isRotating = false;
            App.getPlayer().rotateSpeed = 0.0f;
        }
    }

    public void setDirection( Dir _direction )
    {
        for ( int i = 0; i < DirectionMap.map.length; i++ )
        {
            if ( DirectionMap.map[ i ].translated == _direction )
            {
                int yDir = DirectionMap.map[ i ].dirY;

                if ( App.getPlayer().isMovingY )
                {
                    yDir = App.getPlayer().direction.y;
                }

                App.getPlayer().setXDirection( DirectionMap.map[ i ].dirX );
                App.getPlayer().direction.y = yDir;
            }
        }
    }

    @Override
    public void dispose()
    {
        playerButtons.clear();
        playerButtons           = null;
        abxyButtonArrayIterator = null;
    }
}

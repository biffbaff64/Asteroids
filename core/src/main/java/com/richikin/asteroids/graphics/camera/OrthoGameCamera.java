package com.richikin.asteroids.graphics.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.utils.Trace;

public class OrthoGameCamera implements Disposable
{
    public  Viewport           viewport;
    public  OrthographicCamera camera;
    public  String             name;
    public  boolean            isInUse;
    public  boolean            isLerpingEnabled;
    public  Vector3            lerpVector;
    private float              defaultZoom;

    public OrthoGameCamera( float _sceneWidth, float _sceneHeight, ViewportType _viewType, String _name )
    {
        this.name             = _name;
        this.isInUse          = false;
        this.isLerpingEnabled = false;
        this.lerpVector       = new Vector3();

        camera = new OrthographicCamera( _sceneWidth, _sceneHeight );
        camera.position.set( _sceneWidth / 2, _sceneHeight / 2, 0 );
        camera.update();

        switch ( _viewType )
        {
            case _STRETCH:
            {
                viewport = new StretchViewport
                    (
                        camera.viewportWidth * Gfx.PPM,
                        camera.viewportHeight * Gfx.PPM,
                        camera
                    );
                viewport.apply();
            }
            break;

            case _FIT:
            {
                viewport = new FitViewport
                    (
                        camera.viewportWidth * Gfx.PPM,
                        camera.viewportHeight * Gfx.PPM,
                        camera
                    );
                viewport.apply();
            }
            break;

            case _FILL:
            case _SCREEN:
            {
                throw new GdxRuntimeException( "Type " + _viewType + " not yet supported" );
            }

            case _EXTENDED:
            default:
            {
                viewport = new ExtendViewport
                    (
                        camera.viewportWidth * Gfx.PPM,
                        camera.viewportHeight * Gfx.PPM,
                        camera
                    );
                viewport.apply();
            }
            break;
        }

        setZoomDefault( Zoom._DEFAULT_ZOOM );
    }

    public void setPosition( Vector3 _position, float _zoom )
    {
        if ( isInUse )
        {
            camera.position.x = _position.x;
            camera.position.y = _position.y;
            camera.position.z = _position.z;
            camera.zoom += _zoom;

            camera.update();
        }
    }

    public void setPosition( Vector3 _position, float _zoom, boolean _shake )
    {
        if ( isInUse )
        {
            camera.position.x = _position.x;
            camera.position.y = _position.y;
            camera.position.z = _position.z;
            camera.zoom += _zoom;

            if ( _shake )
            {
                Shake.update( Gdx.graphics.getDeltaTime(), camera );
            }

            camera.update();
        }
    }

    public Vector3 getPosition()
    {
        return camera.position;
    }

    public void setPosition( Vector3 _position )
    {
        if ( isInUse )
        {
            camera.position.x = _position.x;
            camera.position.y = _position.y;
            camera.position.z = _position.z;

            camera.update();
        }
    }

    public void updatePosition( float targetX, float targetY )
    {
        if ( isInUse )
        {
            Vector3 position = camera.position;

            // a = current camera position
            // b = target
            // a = (b - a) * lerp

            position.x = camera.position.x + ( targetX - camera.position.x ) * 0.1f;
            position.y = camera.position.y + ( targetY - camera.position.y ) * 0.1f;

            camera.position.set( position );
            camera.update();
        }
    }

    public void lerpTo( Vector3 _position, float _speed )
    {
        if ( isInUse && isLerpingEnabled )
        {
            lerpVector.set( _position.x, _position.y, _position.z );

            camera.position.lerp( lerpVector, _speed );
            camera.update();
        }
    }

    public void lerpTo( Vector3 _position, float _speed, float _zoom, boolean _shake )
    {
        if ( isInUse && isLerpingEnabled )
        {
            lerpVector.set( _position.x, _position.y, _position.z );

            camera.position.lerp( lerpVector, _speed );
            camera.zoom += _zoom;

            if ( _shake )
            {
                Shake.update( Gdx.graphics.getDeltaTime(), camera );
            }

            camera.update();
        }
    }

    public void updateZoom( float _targetZoom, float _speed )
    {
        if ( camera.zoom < _targetZoom )
        {
            if ( ( camera.zoom += _speed ) > _targetZoom )
            {
                camera.zoom = _targetZoom;
            }
        }
        else if ( camera.zoom > _targetZoom )
        {
            if ( ( camera.zoom -= _speed ) < _targetZoom )
            {
                camera.zoom = _targetZoom;
            }
        }
    }

    public void resizeViewport( int _width, int _height, boolean _centerCamera )
    {
        viewport.update( _width, _height, _centerCamera );
        camera.update();
    }

    public float getCameraZoom()
    {
        return camera.zoom;
    }

    public void setCameraZoom( float _zoom )
    {
        camera.zoom = _zoom;
    }

    public void setZoomDefault( float _zoom )
    {
        camera.zoom = _zoom;
        defaultZoom = _zoom;
    }

    public float getDefaultZoom()
    {
        return defaultZoom;
    }

    public void reset()
    {
        camera.zoom = Zoom._DEFAULT_ZOOM;
        camera.position.set( 0, 0, 0 );
        camera.update();
    }

    public void debug()
    {
        Trace.divider( 100 );

        Trace.info( "Name                      : ", name );
        Trace.info( "camera.viewportWidth      : ", camera.viewportWidth );
        Trace.info( "camera.viewportHeight     : ", camera.viewportHeight );
        Trace.info( "viewport.getScreenWidth() : ", viewport.getScreenWidth() );
        Trace.info( "viewport.getScreenHeight(): ", viewport.getScreenHeight() );
        Trace.info( "camera.zoom               : ", camera.zoom );
    }

    @Override
    public void dispose()
    {
        camera     = null;
        viewport   = null;
        name       = null;
        lerpVector = null;
    }
}

package com.richikin.asteroids.scenes;

import com.badlogic.gdx.ScreenAdapter;
import com.richikin.asteroids.core.App;

public abstract class AbstractBaseScene extends ScreenAdapter implements SceneInterface
{
    public AbstractBaseScene()
    {
        super();
    }

    @Override
    public void update()
    {
    }

    @Override
    public void resize( int _width, int _height )
    {
        App.getGameRenderer().resizeCameras( _width, _height );
    }

    @Override
    public void pause()
    {
        App.getSettings().getPrefs().flush();
    }

    @Override
    public void show()
    {
        loadImages();
    }

    @Override
    public void render( float delta )
    {
        App.getGameRenderer().render();
    }

    @Override
    public void loadImages()
    {
    }
}

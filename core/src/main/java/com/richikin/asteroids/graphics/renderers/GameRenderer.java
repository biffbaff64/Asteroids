package com.richikin.asteroids.graphics.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.enums.CamID;
import com.richikin.asteroids.graphics.Gfx;
import com.richikin.asteroids.graphics.camera.OrthoGameCamera;
import com.richikin.asteroids.graphics.camera.ViewportType;
import com.richikin.asteroids.graphics.camera.Zoom;
import com.richikin.asteroids.utils.Trace;
import org.jetbrains.annotations.NotNull;

public class GameRenderer implements Disposable
{
    private static final float DEFAULT_HUD_ZOOM      = 1.0f;
    private static final float DEFAULT_PARALLAX_ZOOM = 1.0f;
    private static final float DEFAULT_MAP_ZOOM      = 1.0f;

    private OrthoGameCamera hudGameCamera;
    private OrthoGameCamera spriteGameCamera;
    private OrthoGameCamera backgroundCamera;
    private Zoom            gameZoom;
    private Zoom            hudZoom;
    private boolean         isDrawingStage;

    private WorldRenderer worldRenderer;
    private HudRenderer   hudRenderer;
    private Vector3       cameraPos;

    public GameRenderer()
    {
    }

    /**
     * Create all game cameras and
     * associated viewports.
     */
    public void createCameras()
    {
        Trace.checkPoint();

        App.appConfig.camerasReady = false;

        // --------------------------------------
        // Camera for displaying the starfield background.
        backgroundCamera = new OrthoGameCamera
            (
                Gfx.GAME_SCENE_WIDTH, Gfx.GAME_SCENE_HEIGHT,
                ViewportType._STRETCH,
                "Tiled Cam"
            );

        // --------------------------------------
        // Camera for displaying game scene, usually just sprites.
        spriteGameCamera = new OrthoGameCamera
            (
                Gfx.GAME_SCENE_WIDTH, Gfx.GAME_SCENE_HEIGHT,
                ViewportType._STRETCH,
                "Sprite Cam"
            );

        // --------------------------------------
        // Camera for displaying the HUD
        // Using a seperate camera to allow camera effects to be applied
        // to mainGameCamera without affecting the hud.
        hudGameCamera = new OrthoGameCamera
            (
                Gfx.HUD_SCENE_WIDTH, Gfx.HUD_SCENE_HEIGHT,
                ViewportType._STRETCH,
                "Hud Cam"
            );

        gameZoom      = new Zoom();
        hudZoom       = new Zoom();
        cameraPos     = new Vector3();
        worldRenderer = new WorldRenderer();
        hudRenderer   = new HudRenderer();

        backgroundCamera.setCameraZoom( DEFAULT_MAP_ZOOM );
        spriteGameCamera.setCameraZoom( DEFAULT_MAP_ZOOM );
        hudGameCamera.setCameraZoom( DEFAULT_HUD_ZOOM );

        isDrawingStage             = false;
        App.appConfig.camerasReady = true;
    }

    public void render()
    {
        ScreenUtils.clear( 0, 0, 0, 1, true );

        App.getSpriteBatch().enableBlending();

        drawBackground();
        drawSprites();
        updateHudCamera();

        // ----- Draw the Stage, if enabled -----
        if ( isDrawingStage && ( App.getStage() != null ) )
        {
            App.getStage().act( Math.min( Gdx.graphics.getDeltaTime(), Gfx.STEP_TIME ) );
            App.getStage().draw();
        }

        gameZoom.stop();
        hudZoom.stop();

        App.getBox2DHelper().drawDebugMatrix();
    }

    private void drawBackground()
    {
        if ( backgroundCamera.isInUse )
        {
            backgroundCamera.viewport.apply();
            App.getSpriteBatch().setProjectionMatrix( backgroundCamera.camera.combined );
            App.getSpriteBatch().begin();

            cameraPos.x = 0;
            cameraPos.y = 0;
            cameraPos.z = 0;

            if ( backgroundCamera.isLerpingEnabled )
            {
                backgroundCamera.lerpTo( cameraPos, Gfx.LERP_SPEED, gameZoom.getZoomValue(), true );
            }
            else
            {
                backgroundCamera.setPosition( cameraPos, gameZoom.getZoomValue(), true );
            }

            App.getSpriteBatch().end();
        }
    }

    private void drawSprites()
    {
        if ( spriteGameCamera.isInUse )
        {
            spriteGameCamera.viewport.apply();
            App.getSpriteBatch().setProjectionMatrix( spriteGameCamera.camera.combined );
            App.getSpriteBatch().begin();

            cameraPos.x = 0;
            cameraPos.y = 0;
            cameraPos.z = 0;

            spriteGameCamera.setPosition( cameraPos, gameZoom.getZoomValue(), true );

            worldRenderer.render();

            App.getSpriteBatch().end();
        }
    }

    private void updateHudCamera()
    {
        if ( hudGameCamera.isInUse )
        {
            hudGameCamera.viewport.apply();
            App.getSpriteBatch().setProjectionMatrix( hudGameCamera.camera.combined );
            App.getSpriteBatch().begin();

            cameraPos.x = ( hudGameCamera.camera.viewportWidth / 2 );
            cameraPos.y = ( hudGameCamera.camera.viewportHeight / 2 );
            cameraPos.z = 0;

            hudGameCamera.setPosition( cameraPos, hudZoom.getZoomValue(), false );

            hudRenderer.render( App.getSpriteBatch(), hudGameCamera );

            App.getSpriteBatch().end();
        }
    }

    public void resizeCameras( int width, int height )
    {
        backgroundCamera.resizeViewport( width, height, true );
        spriteGameCamera.resizeViewport( width, height, true );
        hudGameCamera.resizeViewport( width, height, true );

        App.getStage().getViewport().update( width, height );
    }

    public void resetCameraZoom()
    {
        backgroundCamera.camera.zoom = DEFAULT_MAP_ZOOM;
        spriteGameCamera.camera.zoom = DEFAULT_MAP_ZOOM;
        hudGameCamera.camera.zoom    = DEFAULT_HUD_ZOOM;

        backgroundCamera.camera.update();
        spriteGameCamera.camera.update();
        hudGameCamera.camera.update();

        gameZoom.stop();
        hudZoom.stop();
    }

    /**
     * Enables only the cameras specified in cameraList.
     * All others will be disabled.
     *
     * @param cameraList The list of {@link CamID} cameras to enable.
     */
    public void enableCamera( @NotNull CamID... cameraList )
    {
        disableAllCameras();

        for ( CamID id : cameraList )
        {
            if ( id == CamID._BACKGROUND )
            {
                backgroundCamera.isInUse = true;
            }
            else if ( id == CamID._SPRITE )
            {
                spriteGameCamera.isInUse = true;
            }
            else if ( id == CamID._HUD )
            {
                hudGameCamera.isInUse = true;
            }
            else if ( id == CamID._STAGE )
            {
                isDrawingStage = true;
            }
        }
    }

    public void enableAllCameras()
    {
        backgroundCamera.isInUse = true;
        spriteGameCamera.isInUse = true;
        hudGameCamera.isInUse    = true;
        isDrawingStage           = true;
    }

    public void disableAllCameras()
    {
        backgroundCamera.isInUse = false;
        spriteGameCamera.isInUse = false;
        hudGameCamera.isInUse    = false;
        isDrawingStage           = false;
    }

    public void disableLerping()
    {
        backgroundCamera.isLerpingEnabled = false;
        spriteGameCamera.isLerpingEnabled = false;
        hudGameCamera.isLerpingEnabled    = false;
    }

    public OrthoGameCamera getHudGameCamera()
    {
        return hudGameCamera;
    }

    public OrthoGameCamera getSpriteGameCamera()
    {
        return spriteGameCamera;
    }

    public OrthoGameCamera getBackgroundCamera()
    {
        return backgroundCamera;
    }

    public Zoom getGameZoom()
    {
        return gameZoom;
    }

    public Zoom getHudZoom()
    {
        return hudZoom;
    }

    public boolean isDrawingStage()
    {
        return isDrawingStage;
    }

    @Override
    public void dispose()
    {
        backgroundCamera.dispose();
        spriteGameCamera.dispose();
        hudGameCamera.dispose();

        backgroundCamera = null;
        spriteGameCamera = null;
        hudGameCamera    = null;

        cameraPos     = null;
        gameZoom      = null;
        hudZoom       = null;
        worldRenderer = null;
        hudRenderer   = null;
    }
}

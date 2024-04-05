package com.richikin.asteroids.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.richikin.asteroids.assets.Assets;
import com.richikin.asteroids.config.AppConfig;
import com.richikin.asteroids.config.Settings;
import com.richikin.asteroids.entities.*;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.graphics.renderers.GameRenderer;
import com.richikin.asteroids.scenes.MainScene;
import com.richikin.asteroids.scenes.TitleScene;
import com.richikin.asteroids.utils.StateManager;

public class App
{
    public static MainGame     mainGame;
    public static Settings     settings;
    public static GameRenderer gameRenderer;
    public static AppConfig    appConfig;
    public static StateManager appState;

    // ------------------------------------------------------------------------

    public static MainScene  mainScene;
    public static TitleScene titleScene;

    // ------------------------------------------------------------------------

    public static Vector2 mapPosition = new Vector2();

    // ------------------------------------------------------------------------

    private static Box2DHelper    box2DHelper;
    private static Assets         assets;
    private static SpriteBatch    spriteBatch;
    private static GameProgress   gameProgress;
    private static Entities       entities;
    private static HeadsUpDisplay hud;
    private static EntityManager  entityManager;
    private static EntityUtils    entityUtils;
    private static EntityData     entityData;
    private static Stage          stage;
    private static MainPlayer     mainPlayer;

    // ------------------------------------------------------------------------
    // CODE
    // ------------------------------------------------------------------------

    public static void createObjects()
    {
        appState    = new StateManager( StateID._STATE_POWER_UP );
        spriteBatch = new SpriteBatch();
        entityData  = new EntityData();
    }

    public static Box2DHelper getBox2DHelper()
    {
        return box2DHelper;
    }

    // ------------------------------------------------------------------------

    public static EntityManager getEntityManager()
    {
        return entityManager;
    }

    public static EntityUtils getEntityUtils()
    {
        return entityUtils;
    }

    public static EntityData getEntityData()
    {
        return entityData;
    }

    public static MainPlayer getPlayer()
    {
        return mainPlayer;
    }

    // ------------------------------------------------------------------------

    public static void createStage( Viewport viewport )
    {
        stage = new Stage( viewport, getSpriteBatch() );
    }

    public static Stage getStage()
    {
        return stage;
    }

    // ------------------------------------------------------------------------

    public static Assets getAssets()
    {
        return assets;
    }

    // ------------------------------------------------------------------------

    public static SpriteBatch getSpriteBatch()
    {
        return spriteBatch;
    }
}

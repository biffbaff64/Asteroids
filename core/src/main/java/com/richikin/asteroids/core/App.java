package com.richikin.asteroids.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.richikin.asteroids.assets.AssetLoader;
import com.richikin.asteroids.assets.Assets;
import com.richikin.asteroids.config.AppConfig;
import com.richikin.asteroids.config.Settings;
import com.richikin.asteroids.entities.*;
import com.richikin.asteroids.enums.StateID;
import com.richikin.asteroids.graphics.renderers.GameRenderer;
import com.richikin.asteroids.scenes.HudScene;
import com.richikin.asteroids.scenes.MainScene;
import com.richikin.asteroids.scenes.TitleScene;
import com.richikin.asteroids.utils.StateManager;

public class App
{
    public static Vector2 mapPosition = new Vector2();

    // ------------------------------------------------------------------------

    private static MainGame      mainGame;
    private static Settings      settings;
    private static GameRenderer  gameRenderer;
    private static AppConfig     appConfig;
    private static StateManager  appState;
    private static MainScene     mainScene;
    private static TitleScene    titleScene;
    private static Box2DHelper   box2DHelper;
    private static Assets        assets;
    private static SpriteBatch   spriteBatch;
    private static GameManager   gameManager;
    private static Entities      entities;
    private static HudScene      hud;
    private static EntityManager entityManager;
    private static EntityUtils   entityUtils;
    private static EntityData    entityData;
    private static Stage         stage;
    private static MainPlayer    mainPlayer;

    // ------------------------------------------------------------------------
    // CODE
    // ------------------------------------------------------------------------

    /**
     * Creates essential global objects that need to be accessible from app startup.
     * These will only be destroyed when the app is closed. Some data held within
     * these objects may be cleared elsewhere, but the objects can only be destroyed
     * in {@link App#deleteEssentialObjects()}
     */
    public static void createEssentialObjects()
    {
        appState    = new StateManager( StateID._STATE_POWER_UP );
        spriteBatch = new SpriteBatch();
        entityData  = new EntityData();

        // ----------------------------

        settings = new Settings();
        assets   = new AssetLoader();

        // ----------------------------

        box2DHelper  = new Box2DHelper();
        gameRenderer = new GameRenderer();
        gameManager  = new GameManager();
    }

    public static void createMainSceneObjects()
    {
        entities      = new Entities();
        hud           = new HudScene();
        entityManager = new EntityManager();
        entityUtils   = new EntityUtils();
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

    /**
     * Sets the global access reference to MainGame, which extends
     * LibGDX's Game class. This allows for access to setScreen()
     * in Config classes etc.
     */
    public static void setMainGame( MainGame game )
    {
        mainGame = game;
    }

    public static void setAppConfig( AppConfig config )
    {
        appConfig = config;
    }

    public static void setMainScene( MainScene scene )
    {
        mainScene = scene;
    }

    // ------------------------------------------------
    //@formatter:off
    public static GameRenderer          getGameRenderer()           {   return gameRenderer;        }
    public static MainGame              getMainGame()               {   return mainGame;            }
    public static Box2DHelper           getBox2DHelper()            {   return box2DHelper;         }
    public static TitleScene            getTitleScene()             {   return titleScene;          }
    public static MainScene             getMainScene()              {   return mainScene;           }
    public static GameManager           getGameManager()            {  return gameManager;          }
    public static HudScene              getHud()                    {  return hud;                  }
    public static Entities              getEntities()               {  return entities;             }
    public static EntityManager         getEntityManager()          {  return entityManager;        }
    public static EntityUtils           getEntityUtils()            {  return entityUtils;          }
    public static Assets                getAssets()                 {  return assets;               }
    public static StateManager          getAppState()               {  return appState;             }
    public static AppConfig             getAppConfig()              {  return appConfig;            }
    public static Settings              getSettings()               {  return settings;             }
    public static SpriteBatch           getSpriteBatch()            {  return spriteBatch;          }
    //@formatter:on
    // ------------------------------------------------

    public static void createStage( Viewport viewport )
    {
        stage = new Stage( viewport, getSpriteBatch() );
    }

    public static Stage getStage()
    {
        return stage;
    }

    // ------------------------------------------------------------------------

    public void deleteEssentialObjects()
    {
    }

    public void deleteMainSceneObjects()
    {
    }
}

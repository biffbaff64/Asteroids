package com.richikin.asteroids.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.richikin.asteroids.enums.GraphicID;
import com.richikin.asteroids.utils.Trace;

public class AssetLoader implements com.richikin.asteroids.assets.Assets
{
    // Texture Atlas names.
    // For more information check DesktopLauncher::main()
    private static final String _BUTTONS_ATLAS      = "packedimages/output/buttons.atlas";
    private static final String _ANIMATIONS_ATLAS   = "packedimages/output/animations.atlas";
    private static final String _OBJECTS_ATLAS      = "packedimages/output/objects.atlas";
    private static final String _TEXT_ATLAS         = "packedimages/output/text.atlas";
    private static final String _ACHIEVEMENTS_ATLAS = "packedimages/output/achievements.atlas";

    public AssetManager assetManager;

    public AssetLoader()
    {
    }

    /**
     * Loads the TextureAtlases that will be
     * used for animations/object/buttons etc.
     */
    @Override
    public void initialise()
    {
        this.assetManager = new AssetManager();

        try
        {
            loadAtlas( _BUTTONS_ATLAS );
            loadAtlas( _ANIMATIONS_ATLAS );
            loadAtlas( _TEXT_ATLAS );
            loadAtlas( _OBJECTS_ATLAS );
            loadAtlas( _ACHIEVEMENTS_ATLAS );
        }
        catch ( Exception _exception )
        {
            Trace.checkPoint();
            Trace.dbg( "WARNING: ATLASES NEED BUILDING!" );
        }
    }

    /**
     * Use the AssetManager to find and return the specified
     * region from the Buttons Atlas.
     *
     * @param name The name of the required region
     * @return A TextureRegion holding requested Region
     */
    @Override
    public TextureRegion getButtonRegion( final String name )
    {
        return assetManager.get( _BUTTONS_ATLAS, TextureAtlas.class ).findRegion( name );
    }

    /**
     * Use the AssetManager to find and return the specified
     * region from the Animations Atlas.
     *
     * @param name The name of the required region
     * @return A TextureRegion holding requested Region
     */
    @Override
    public TextureRegion getAnimationRegion( final String name )
    {
        return assetManager.get( _ANIMATIONS_ATLAS, TextureAtlas.class ).findRegion( name );
    }

    /**
     * Use the AssetManager to find and return the specified
     * region from the Objects Atlas.
     *
     * @param name The name of the required region
     * @return A TextureRegion holding requested Region
     */
    @Override
    public TextureRegion getObjectRegion( final String name )
    {
        return assetManager.get( _OBJECTS_ATLAS, TextureAtlas.class ).findRegion( name );
    }

    /**
     * Use the AssetManager to find and return the specified
     * region from the Text Atlas.
     *
     * @param name The name of the required region
     * @return A TextureRegion holding requested Region
     */
    @Override
    public TextureRegion getTextRegion( final String name )
    {
        return assetManager.get( _TEXT_ATLAS, TextureAtlas.class ).findRegion( name );
    }

    /**
     * Use the AssetManager to find and return the specified
     * region from the Achievements Atlas.
     *
     * @param name The name of the required region
     * @return A TextureRegion holding requested Region
     */
    @Override
    public TextureRegion getAchievementRegion( final String name )
    {
        return assetManager.get( _ACHIEVEMENTS_ATLAS, TextureAtlas.class ).findRegion( name );
    }

    @Override
    public AssetManager getAssetManager()
    {
        return assetManager;
    }

    @Override
    public TextureAtlas getButtonsLoader()
    {
        return assetManager.get( _BUTTONS_ATLAS, TextureAtlas.class );
    }

    @Override
    public TextureAtlas getAnimationsLoader()
    {
        return assetManager.get( _ANIMATIONS_ATLAS, TextureAtlas.class );
    }

    @Override
    public TextureAtlas getObjectsLoader()
    {
        return assetManager.get( _OBJECTS_ATLAS, TextureAtlas.class );
    }

    @Override
    public TextureAtlas getTextsLoader()
    {
        return assetManager.get( _TEXT_ATLAS, TextureAtlas.class );
    }

    @Override
    public TextureAtlas getAchievementsLoader()
    {
        return assetManager.get( _ACHIEVEMENTS_ATLAS, TextureAtlas.class );
    }

    @Override
    public String getRedObjectName()
    {
        return null;
    }

    @Override
    public String getBlueObjectName()
    {
        return null;
    }

    @Override
    public String getGreenObjectName()
    {
        return null;
    }

    @Override
    public String getYellowObjectName()
    {
        return null;
    }

    @Override
    public String getWhiteObjectName()
    {
        return null;
    }

    /**
     * Load single asset, and ensures that it is loaded.
     * It then returns an object of the specified type.
     *
     * @param <T>   the type parameter
     * @param asset the asset to load
     * @param type  the class type of the asset to load
     * @return an object of the specified type
     */
    @Override
    public < T > T loadSingleAsset( String asset, Class< ? > type )
    {
        if ( !assetManager.isLoaded( asset, type ) )
        {
            assetManager.load( asset, type );
            assetManager.finishLoadingAsset( asset );
        }

        return assetManager.get( asset );
    }

    /**
     * Load TextureAtlas asset.
     *
     * @param atlasName the full name of the specified atlas.
     */
    @Override
    public void loadAtlas( String atlasName )
    {
        loadSingleAsset( atlasName, TextureAtlas.class );
    }

    /**
     * Unload the specified object
     *
     * @param asset the filename of the object to unload
     */
    @Override
    public void unloadAsset( String asset )
    {
        if ( assetManager.isLoaded( asset ) )
        {
            assetManager.unload( asset );
        }
    }

    @Override
    public String getSkinFilename()
    {
        return null;
    }

    @Override
    public String getDevPanelFont()
    {
        return null;
    }

    @Override
    public String getDevPanelBackground()
    {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getPausePanelBackground()
    {
        return null;
    }

    @Override
    public String getOptionsPanelAsset()
    {
        return null;
    }

    @Override
    public String getControllerTestAsset()
    {
        return null;
    }

    /**
     * @return
     */
    @Override
    public TextureRegion getStarfieldObject()
    {
        return getObjectRegion( "solid_white32x32" );
    }

    /**
     * @return
     */
    @Override
    public GraphicID getPlayerGID()
    {
        return null;
    }

    @Override
    public void dispose()
    {
        Trace.checkPoint();

        assetManager.dispose();
        assetManager = null;
    }
}
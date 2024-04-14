package com.richikin.asteroids.graphics;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TextureAtlasBuilder
{
    // Set _rebuildAtlas to TRUE to rebuild all atlases. This is only really necessary
    // when new images are added to a folder, but can be left as true by default
    // if you prefer to rebuild every time.
    // NB: Bear in mind that, if you are testing builds other than the desktop
    // build and you add new images to a folder, YOU MUST build and run the desktop
    // version so that the atlases are rebuilt. If you don't do this then you will
    // experience errors when the program tries to access the new images.
    private static final boolean _rebuildAtlas          = true;
    private static final boolean _drawDebugLines        = false;
    private static final boolean _removeDuplicateImages = false;

    public static void buildTextureAtlases()
    {
        if ( _rebuildAtlas )
        {
            TexturePacker.Settings settings = new TexturePacker.Settings();

            settings.maxWidth         = 2048;        // Maximum Width of final atlas image
            settings.maxHeight        = 2048;        // Maximum Height of final atlas image
            settings.pot              = true;
            settings.debug            = _drawDebugLines;
            settings.alias            = _removeDuplicateImages;
            settings.stripWhitespaceX = false;
            settings.stripWhitespaceY = false;

            //
            // Build the Atlases from the specified parameters :-
            // - configuration settings
            // - source folder
            // - destination folder
            // - name of atlas, without extension (the extension '.atlas' will be added automatically)
            TexturePacker.process( settings, "packedimages/objects", "packedimages/output", "objects" );
            TexturePacker.process( settings, "packedimages/animations", "packedimages/output", "animations" );
            TexturePacker.process( settings, "packedimages/input", "packedimages/output", "buttons" );
            TexturePacker.process( settings, "packedimages/text", "packedimages/output", "text" );
        }
    }
}
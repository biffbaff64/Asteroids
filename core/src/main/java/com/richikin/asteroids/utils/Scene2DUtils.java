package com.richikin.asteroids.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.richikin.asteroids.core.App;
import com.richikin.asteroids.graphics.FontUtils;
import org.jetbrains.annotations.NotNull;

public class Scene2DUtils
{
    public Scene2DUtils()
    {
    }

    /**
     * Creates a {@link Table}, without adding it to the stage.
     *
     * @param pos    X, Y Display coordinates.
     * @param width  The table Width in pixels.
     * @param height The table Height in pixels.
     * @param skin   The {@link Skin} to use.
     * @return Tne Table.
     */
    public Table createTable( @NotNull Vector2 pos, int width, int height, Skin skin )
    {
        Table table = new Table( skin );
        table.setSize( width, height );
        table.setPosition( pos.x, pos.y );

        return table;
    }

    /**
     * Convenience method for creating a {@link Image}.
     *
     * @param imageName   The name of the image to use.
     * @param atlasLoader The {@link TextureAtlas} loader to use.
     * @return The Image.
     */
    public Image createImage( String imageName, @NotNull TextureAtlas atlasLoader )
    {
        TextureRegion         region   = atlasLoader.findRegion( imageName );
        TextureRegionDrawable drawable = new TextureRegionDrawable( region );

        return new Image( drawable );
    }

    /**
     * Create an {@link Image} from the Objects TextureAtlas.
     *
     * @param imageName Filename of the texture to use.
     * @return The Image.
     */
    public Image makeObjectsImage( String imageName )
    {
        TextureRegion         region   = App.getAssets().getObjectRegion( imageName );
        TextureRegionDrawable drawable = new TextureRegionDrawable( region );

        return new Image( drawable );
    }

    /**
     * Convenience method for creating a {@link Drawable}.
     *
     * @param imageName   The name of the image to use.
     * @param atlasLoader The {@link TextureAtlas} loader to use.
     * @return The Drawable.
     */
    public Drawable createDrawable( String imageName, @NotNull TextureAtlas atlasLoader )
    {
        TextureRegion region = atlasLoader.findRegion( imageName );

        return new TextureRegionDrawable( region );
    }

    public Drawable getDrawable( TextureRegion textureRegion )
    {
        return new TextureRegionDrawable( textureRegion );
    }

    /**
     * Creates a {@link ScrollPane}, without adding it to the stage.
     *
     * @param table The associated {@link Table}.
     * @param skin  The {@link Skin} to use.
     * @param name  The name of this pane.
     * @return The ScrollPane.
     */
    public ScrollPane createScrollPane( Table table, Skin skin, String name )
    {
        ScrollPane scrollPane = new ScrollPane( table, skin );

        scrollPane.setName( name );

        return scrollPane;
    }

    /**
     * Makes a text {@link Label}, amd adds it to the stage.
     *
     * @param labelText The label text to display.
     * @param pos       X, Y Display coordinates.
     * @param color     The Tint.
     * @param fontName  The {@link BitmapFont} to use.
     * @return The label.
     */
    public Label addLabel( String labelText, @NotNull Vector2 pos, int size, Color color, String fontName )
    {
        FontUtils fontUtils = new FontUtils();

        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font      = fontUtils.createFont( fontName, size, Color.WHITE );
        label1Style.fontColor = color;

        Label label = new Label( labelText, label1Style );
        label.setStyle( label1Style );
        label.setAlignment( Align.center );
        label.setPosition( pos.x, pos.y );

        return label;
    }

    /**
     * Makes a text {@link Label}, amd adds it to the stage.
     *
     * @param labelText The label text to display.
     * @param pos       X, Y Display coordinates.
     * @param color     The Tint.
     * @param skin      The {@link Skin} to use.
     * @return The label.
     */
    public Label addLabel( String labelText, @NotNull Vector2 pos, Color color, Skin skin )
    {
        Label label = makeLabel( labelText, ( int )pos.x, ( int )pos.y, color, skin );

        App.getStage().addActor( label );

        return label;
    }

    /**
     * Makes a selectable {@link ImageButton} and adds it to the stage.
     *
     * @param upButton   Filename of the image to display when NOT pressed.
     * @param downButton Filename of the image to display when pressed.
     * @param x          X Display coordinate.
     * @param y          Y Display coordinate.
     * @return The ImageButton.
     */
    public ImageButton addButton( String upButton, String downButton, int x, int y )
    {
        Image       imageUp     = new Image( App.getAssets().getButtonRegion( upButton ) );
        Image       imageDown   = new Image( App.getAssets().getButtonRegion( downButton ) );
        ImageButton imageButton = new ImageButton( imageUp.getDrawable(), imageDown.getDrawable() );

        imageButton.setPosition( x, y );
        imageButton.setVisible( true );
        imageButton.setZIndex( 1 );

        App.getStage().addActor( imageButton );

        return imageButton;
    }

    /**
     * Makes a selectable {@link TextButton} and adds it to the stage.
     *
     * @param text The text to display on the button.
     * @param x    X Display coordinate.
     * @param y    Y Display coordinate.
     * @return The TextButton.
     */
    public TextButton addButton( String text, int x, int y )
    {
        TextButton button = new TextButton( text, new Skin( Gdx.files.internal( App.getAssets().getSkinFilename() ) ) );

        button.setPosition( x, y );
        button.setVisible( true );
        button.setZIndex( 1 );

        App.getStage().addActor( button );

        return button;
    }

    /**
     * Makes a selectable {@link CheckBox} and adds it to the stage.
     *
     * @param imageOn  The image to display when selected.
     * @param imageOff The image to display when deselected.
     * @param x        X Display coordinate.
     * @param y        Y Display coordinate.
     * @param color    The Tint.
     * @param skin     The {@link Skin} to use.
     * @return The Checkbox.
     */
    public CheckBox addCheckBox( String imageOn, String imageOff, int x, int y, Color color, Skin skin )
    {
        CheckBox checkBox = makeCheckBox( imageOn, imageOff, x, y, color, skin );

        App.getStage().addActor( checkBox );

        return checkBox;
    }

    /**
     * Makes a text {@link Label}, without adding it to the stage.
     *
     * @param string The label text to display.
     * @param x      X Display coordinate.
     * @param y      Y Display coordinate.
     * @param color  The Tint.
     * @param skin   The {@link Skin} to use.
     * @return The label.
     */
    public Label makeLabel( String string, int x, int y, Color color, Skin skin )
    {
        Label            label = new Label( string, skin );
        Label.LabelStyle style = label.getStyle();
        style.fontColor = color;

        label.setStyle( style );
        label.setAlignment( Align.center );

        label.setPosition( x, y );

        return label;
    }

    /**
     * Make a {@link Slider} bar with a sliding indicator,
     * without adding it to the stage.
     *
     * @param pos  X, Y Display coordinates.
     * @param skin The {@link Skin} to use.
     * @return The Slider.
     */
    public Slider makeSlider( Vector2 pos, Skin skin )
    {
        Slider slider = new Slider( 0, 10, 1, false, skin );

        slider.setPosition( pos.x, pos.y );
        slider.setSize( 280, 30 );

        return slider;
    }

    /**
     * Create a {@link CheckBox} without adding it to stage.
     *
     * @param imageOn  The image to display when box is selected.
     * @param imageOff The image to display when box is deselected.
     * @param x        X Display coordinate.
     * @param y        Y Display coordinate.
     * @param color    The Tint.
     * @param skin     The {@link Skin} to use.
     * @return The Checkbox.
     */
    public CheckBox makeCheckBox( String imageOn, String imageOff, int x, int y, Color color, Skin skin )
    {
        TextureRegion regionOn  = App.getAssets().getButtonRegion( imageOn );
        TextureRegion regionOff = App.getAssets().getButtonRegion( imageOff );

        CheckBox               checkBox = new CheckBox( "", skin );
        CheckBox.CheckBoxStyle style    = checkBox.getStyle();

        style.fontColor   = color;
        style.checkboxOn  = new TextureRegionDrawable( regionOn );
        style.checkboxOff = new TextureRegionDrawable( regionOff );

        checkBox.setSize( regionOn.getRegionWidth(), regionOn.getRegionHeight() );
        checkBox.setStyle( style );
        checkBox.setPosition( x, y );

        return checkBox;
    }
}

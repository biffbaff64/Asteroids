package com.richikin.asteroids.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.richikin.asteroids.config.AppConfig;
import com.richikin.asteroids.config.Settings;
import com.richikin.asteroids.utils.Trace;
import com.richikin.asteroids.core.App;

public class GameAudio
{
    private int     currentTune;
    private int     musicVolumeSave;
    private int     fxVolumeSave;
    private boolean soundsLoaded;
    private boolean musicLoaded;
    private boolean isTunePaused;

    public GameAudio()
    {
    }

    public void setup()
    {
        soundsLoaded = false;
        musicLoaded  = false;
        isTunePaused = false;

        loadSounds();

        musicVolumeSave = Math.max( 0, AudioData._DEFAULT_MUSIC_VOLUME );
        fxVolumeSave    = Math.max( 0, AudioData._DEFAULT_FX_VOLUME );
    }

    public void update()
    {
        if ( musicLoaded )
        {
            if ( App.appConfig.gamePaused )
            {
                if ( ( AudioData.getMusic()[ currentTune ] != null )
                        && AudioData.getMusic()[ currentTune ].isPlaying() )
                {
                    AudioData.getMusic()[ currentTune ].pause();
                    isTunePaused = true;
                }
            }
            else
            {
                if ( ( AudioData.getMusic()[ currentTune ] != null )
                        && !AudioData.getMusic()[ currentTune ].isPlaying()
                        && isTunePaused )
                {
                    AudioData.getMusic()[ currentTune ].play();
                    isTunePaused = false;
                }
            }
        }
    }

    private void loadSounds()
    {
        Trace.checkPoint();

        try
        {
//            AudioData.sounds[AudioData.SFX_HIT]       = App.getAssets().loadSingleAsset("data/sounds/hit.wav", Sound.class);
//            AudioData.sounds[AudioData.SFX_LOST]      = App.getAssets().loadSingleAsset("data/sounds/lost.wav", Sound.class);
//            AudioData.sounds[AudioData.SFX_PICKUP]    = App.getAssets().loadSingleAsset("data/sounds/pickup.wav", Sound.class);
//            AudioData.sounds[AudioData.SFX_EXTRALIFE] = App.getAssets().loadSingleAsset("data/sounds/extra_life.mp3", Sound.class);
//
//            AudioData.music[AudioData.MUS_TITLE] = App.getAssets().loadSingleAsset("data/sounds/Bouncy.mp3", Music.class);
//            AudioData.music[AudioData.MUS_HISCORE] = App.getAssets().loadSingleAsset("data/sounds/breath.mp3", Music.class);
//            AudioData.music[AudioData.MUS_GAME]    = App.getAssets().loadSingleAsset("data/sounds/fear_mon.mp3", Music.class);

            App.settings.getPrefs().putInteger( Settings._MUSIC_VOLUME, AudioData._DEFAULT_MUSIC_VOLUME );
            App.settings.getPrefs().putInteger( Settings._FX_VOLUME, AudioData._DEFAULT_FX_VOLUME );
            App.settings.getPrefs().flush();

            soundsLoaded = AudioData.getSounds().length > 0;
            musicLoaded  = AudioData.getMusic().length > 0;
        }
        catch ( Exception e )
        {
            Trace.err( "SOUNDS NOT LOADED!" );

            soundsLoaded = false;
            musicLoaded  = false;
        }
    }

    public void playTune( boolean play )
    {
        if ( currentTune >= 0 )
        {
            if ( play && musicLoaded )
            {
                startTune( currentTune, getMusicVolume(), true );
            }
            else
            {
                tuneStop();
            }
        }
    }

    /**
     * Play or Stop the Main Game tune.
     *
     * @param playTune TRUE to play, FALSE to stop playing.
     */
    public void playGameTune( boolean playTune )
    {
        if ( AudioData.MUS_GAME >= 0 )
        {
            if ( playTune && musicLoaded )
            {
                startTune( AudioData.MUS_GAME, getMusicVolume(), true );
            }
            else
            {
                tuneStop();
            }
        }
    }

    /**
     * Play or Stop the Main Title tune.
     *
     * @param playTune TRUE to play, FALSE to stop playing.
     */
    public void playTitleTune( boolean playTune )
    {
        if ( AudioData.MUS_TITLE >= 0 )
        {
            if ( playTune && musicLoaded )
            {
                startTune( AudioData.MUS_TITLE, getMusicVolume(), true );
            }
            else
            {
                tuneStop();
            }
        }
    }

    /**
     * Play or Stop the HiScore name entry tune.
     * This tune is played on the nname entry screen only,
     * NOT when the hiscore table is displayed in
     * the titles screen sequence.
     *
     * @param playTune TRUE to play, FALSE to stop playing.
     */
    public void playHiScoreTune( boolean playTune )
    {
        if ( AudioData.MUS_HISCORE >= 0 )
        {
            if ( playTune & musicLoaded )
            {
                startTune( AudioData.MUS_HISCORE, getMusicVolume(), true );
            }
            else
            {
                tuneStop();
            }
        }
    }

    public void startTune( int musicNumber, int volume, boolean looping )
    {
        if ( musicLoaded && ( musicNumber >= 0 ) )
        {
            if ( getMusicVolume() > 0 )
            {
                if ( App.settings.isEnabled( Settings._MUSIC_ENABLED )
                        && ( AudioData.getMusic() != null )
                        && !AudioData.getMusic()[ musicNumber ].isPlaying() )
                {
                    AudioData.getMusic()[ musicNumber ].setLooping( looping );
                    AudioData.getMusic()[ musicNumber ].setVolume( volume );
                    AudioData.getMusic()[ musicNumber ].play();

                    currentTune = musicNumber;
                }
            }
        }
    }

    public void startSound( int soundNumber )
    {
        if ( App.settings.isEnabled( Settings._SOUNDS_ENABLED ) && soundsLoaded && ( soundNumber >= 0 ) )
        {
            if ( getFXVolume() > 0 )
            {
                if ( AudioData.getSounds()[ soundNumber ] != null )
                {
                    AudioData.getSounds()[ soundNumber ].play( getFXVolume() );
                }
            }
        }
    }

    public void tuneStop()
    {
        if ( musicLoaded && ( currentTune >= 0 ) )
        {
            if ( ( AudioData.getMusic()[ currentTune ] != null ) && AudioData.getMusic()[ currentTune ].isPlaying() )
            {
                AudioData.getMusic()[ currentTune ].stop();
            }
        }
    }

    public void setMusicVolume( int volume )
    {
        if ( musicLoaded && ( currentTune >= 0 ) )
        {
            if ( AudioData.getMusic()[ currentTune ] != null )
            {
                AudioData.getMusic()[ currentTune ].setVolume( volume );
            }
        }

        App.settings.getPrefs().putInteger( Settings._MUSIC_VOLUME, volume );
        App.settings.getPrefs().flush();
    }

    public void setFXVolume( int volume )
    {
        App.settings.getPrefs().putInteger( Settings._FX_VOLUME, volume );
        App.settings.getPrefs().flush();
    }

    public int getMusicVolume()
    {
        return App.settings.getPrefs().getInteger( Settings._MUSIC_VOLUME );
    }

    public int getFXVolume()
    {
        return App.settings.getPrefs().getInteger( Settings._FX_VOLUME );
    }

    public void saveMusicVolume()
    {
        musicVolumeSave = getMusicVolume();
    }

    public void saveFXVolume()
    {
        fxVolumeSave = getFXVolume();
    }

    public int getMusicVolumeSave()
    {
        return musicVolumeSave;
    }

    public int getFXVolumeSave()
    {
        return fxVolumeSave;
    }

    public Sound[] getSoundsTable()
    {
        return AudioData.getSounds();
    }

    public Music[] getMusicTable()
    {
        return AudioData.getMusic();
    }

    public boolean isTunePlaying()
    {
        return isTunePlaying( currentTune );
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isTunePlaying( int tune )
    {
        if ( tune >= 0 )
        {
            return musicLoaded && AudioData.getMusic()[ tune ].isPlaying();
        }

        return false;
    }
}

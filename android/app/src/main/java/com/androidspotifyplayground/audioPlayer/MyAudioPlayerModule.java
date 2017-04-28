package com.androidspotifyplayground.audioPlayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

// these classes are required for playing the audio

public class MyAudioPlayerModule extends ReactContextBaseJavaModule {

    private static MediaPlayer mediaPlayer = null;

    public MyAudioPlayerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "MyAudioPlayer";
    }

    @ReactMethod
    public void preparePlayer(String url) {
        try{
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepareAsync();
        }catch(Exception e){  }
    }

    @ReactMethod
    public void play() {
        try{
            if (mediaPlayer != null) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        }catch(Exception e){}
    }

    @ReactMethod
    public void pause(){
        try{
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        }catch(Exception e){}
    }

    @ReactMethod
    public void setOnPreparedCallback(Callback onPrepared){
        final Callback onPreparedCallback = onPrepared;
        try{
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer player) {
                    try{
                        onPreparedCallback.invoke(mediaPlayer.getDuration()); // invoking the callback with duration as argument
                    }catch(Exception e){}
                }
            });
        }catch(Exception e){}
    }
}

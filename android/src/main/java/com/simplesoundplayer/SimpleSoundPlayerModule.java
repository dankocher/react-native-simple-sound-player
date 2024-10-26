package com.simplesoundplayer;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;


// Importar la clase MediaPlayer
import android.media.MediaPlayer;
import android.util.Log;

public class SimpleSoundPlayerModule extends ReactContextBaseJavaModule  {
    public static final String NAME = "SimpleSoundPlayer";
    private MediaPlayer mediaPlayer;
    private final ReactApplicationContext reactContext;

    public SimpleSoundPlayerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void play(String soundName) {
        String fileNameWithoutExt;
        if (soundName.lastIndexOf('.') != -1) {
            fileNameWithoutExt = soundName.substring(0, soundName.lastIndexOf('.'));
        } else {
            fileNameWithoutExt = soundName;
        }

        Log.d("Play", soundName);
        int resId = reactContext.getResources().getIdentifier(fileNameWithoutExt, "raw", reactContext.getPackageName());
        if (resId != 0) {
            MediaPlayer mediaPlayer = MediaPlayer.create(reactContext, resId);
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();  // Liberar recursos una vez que la reproducción termine
            });
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                mp.release();  // Liberar recursos en caso de error
                return true;
            });
            mediaPlayer.start();
        } else {
            Log.e("File not found", soundName);
        }
    }

    @ReactMethod
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    @ReactMethod
    public void destroy() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @ReactMethod
    public void setVolume(float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }
}

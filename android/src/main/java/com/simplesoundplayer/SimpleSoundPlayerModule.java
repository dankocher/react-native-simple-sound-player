package com.simplesoundplayer;

import com.facebook.react.bridge.*;

import android.media.MediaPlayer;

public class SimpleSoundPlayerModule extends ReactContextBaseJavaModule {
    private MediaPlayer mediaPlayer;

    public SimpleSoundPlayerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SimpleSoundPlayer";
    }

    @ReactMethod
    public void play(String soundName, Promise promise) {
        int resId = getReactApplicationContext().getResources().getIdentifier(soundName, "raw", getReactApplicationContext().getPackageName());
        if (resId == 0) {
            promise.reject("ERROR", "Sound not found: " + soundName);
            return;
        }

        mediaPlayer = MediaPlayer.create(getReactApplicationContext(), resId);
        mediaPlayer.setOnCompletionListener(mp -> {
            promise.resolve(null);
            releaseMediaPlayer(); // Libera recursos al finalizar la reproducción
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            promise.reject("ERROR", "Error playing sound");
            return true;
        });

        mediaPlayer.start();
    }

    @ReactMethod
    public void stop(Promise promise) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            releaseMediaPlayer(); // Libera recursos al detener la reproducción
            promise.resolve(null);
        } else {
            promise.reject("ERROR", "No sound is playing");
        }
    }

    @ReactMethod
    public void destroy(Promise promise) {
        releaseMediaPlayer(); // Libera los recursos de la reproducción actual
        promise.resolve(null);
    }

    @ReactMethod
    public void setVolume(float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null; // Libera la referencia
        }
    }
}

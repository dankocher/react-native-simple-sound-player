package com.simplesoundplayer;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import android.media.MediaPlayer;

@ReactModule(name = SimpleSoundPlayerModule.NAME)
public class SimpleSoundPlayerModule extends ReactContextBaseJavaModule {
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

    // Método para reproducir un sonido
    @ReactMethod
    public void play(String soundName) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Obtiene el recurso de audio desde "raw" en los recursos de Android
        int resId = reactContext.getResources().getIdentifier(soundName, "raw", reactContext.getPackageName());
        if (resId != 0) {
            mediaPlayer = MediaPlayer.create(reactContext, resId);
            mediaPlayer.setOnCompletionListener(mp -> destroy());
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                destroy();
                return true;
            });
            mediaPlayer.start();
        }
    }

    // Método para detener la reproducción actual
    @ReactMethod
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    // Método para destruir el reproductor y liberar recursos
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

    // Método para ajustar el volumen de reproducción
    @ReactMethod
    public void setVolume(float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }
}

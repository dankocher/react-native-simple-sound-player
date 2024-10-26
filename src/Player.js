// Archivo: src/index.js

import { NativeModules, NativeEventEmitter } from 'react-native';

import async from 'async';
import EventEmitter from 'eventemitter3';
// Obtener el módulo nativo
const RCTSoundPlayer = NativeModules.SimpleSoundPlayer;

console.log(NativeModules)

// Crear un event emitter para manejar los eventos
const soundPlayerEvents = new NativeEventEmitter(RCTSoundPlayer);


class Player extends EventEmitter {
    constructor(soundName) {
        super();

        this.soundName = soundName;
    }

    // Método para reproducir el sonido
    play() {
        // RCTSoundPlayer.play(this.soundName);
        return new Promise((resolve, reject) => {
            RCTSoundPlayer.play(this.soundName, resolve, reject);
        });
    }

    // Método para detener la reproducción
    stop() {
        RCTSoundPlayer.stop();
    }

    // Método para ajustar el volumen
    setVolume(volume) {
        RCTSoundPlayer.setVolume(volume);
    }

    // Método para destruir la instancia actual
    destroy() {
        RCTSoundPlayer.destroy();
    }

    // Suscribirse a eventos de finalización de reproducción
    on(eventName, callback) {
        return soundPlayerEvents.addListener(eventName, callback);
    }

    // Desuscribirse de los eventos
    removeListener(eventName, listener) {
        soundPlayerEvents.removeListener(eventName, listener);
    }
}

// Exportar la clase Player
export default Player;

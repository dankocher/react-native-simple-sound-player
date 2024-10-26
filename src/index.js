// Archivo: src/index.js

import { NativeModules, NativeEventEmitter } from 'react-native';

// Obtener el módulo nativo
const { SimpleSoundPlayerTurboModule } = NativeModules;

// Crear un event emitter para manejar los eventos
const soundPlayerEvents = new NativeEventEmitter(SimpleSoundPlayerTurboModule);

// Definir la clase Player para la biblioteca
class Player {
    constructor(soundName) {
        this.soundName = soundName;
    }

    // Método para reproducir el sonido
    play() {
        return new Promise((resolve, reject) => {
            SimpleSoundPlayerTurboModule.play(this.soundName, resolve, reject);
        });
    }

    // Método para detener la reproducción
    stop() {
        SimpleSoundPlayerTurboModule.stop();
    }

    // Método para ajustar el volumen
    setVolume(volume) {
        SimpleSoundPlayerTurboModule.setVolume(volume);
    }

    // Método para destruir la instancia actual
    destroy() {
        SimpleSoundPlayerTurboModule.destroy();
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
export { Player };

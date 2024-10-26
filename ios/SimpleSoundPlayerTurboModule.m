// Archivo: SimpleSoundPlayerTurboModule.m

#import "SimpleSoundPlayerTurboModule.h"
#import <React/RCTUtils.h>

@implementation SimpleSoundPlayerTurboModule

RCT_EXPORT_MODULE(SimpleSoundPlayerTurboModule)

// Método para reproducir un sonido
- (void)play:(NSString *)soundName resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject {
    NSError *error = nil;

    // Obtener la ruta del archivo de sonido
    NSString *filePath = [[NSBundle mainBundle] pathForResource:soundName ofType:nil];
    if (!filePath) {
        reject(@"file_not_found", [NSString stringWithFormat:@"No se encontró el archivo de sonido: %@", soundName], nil);
        return;
    }

    NSURL *soundURL = [NSURL fileURLWithPath:filePath];
    self.audioPlayer = [[AVAudioPlayer alloc] initWithContentsOfURL:soundURL error:&error];

    if (error) {
        reject(@"playback_error", @"Error al reproducir el sonido.", error);
        return;
    }

    self.audioPlayer.delegate = self;
    [self.audioPlayer prepareToPlay];
    if ([self.audioPlayer play]) {
        resolve(@(YES));
    } else {
        reject(@"playback_failed", @"No se pudo iniciar la reproducción.", nil);
    }
}

// Método para detener la reproducción
- (void)stop {
    if (self.audioPlayer.isPlaying) {
        [self.audioPlayer stop];
    }
}

// Método para ajustar el volumen
- (void)setVolume:(nonnull NSNumber *)volume {
    if (self.audioPlayer) {
        self.audioPlayer.volume = [volume floatValue];
    }
}

// Método para destruir y liberar recursos del reproductor de audio
- (void)destroy {
    if (self.audioPlayer) {
        [self.audioPlayer stop];
        self.audioPlayer = nil;
    }
}

// Delegado para la finalización de la reproducción
- (void)audioPlayerDidFinishPlaying:(AVAudioPlayer *)player successfully:(BOOL)flag {
    [self sendEventWithName:@"onPlaybackEnded" body:@{@"success": @(flag)}];
}

// Manejo de errores de decodificación de audio
- (void)audioPlayerDecodeErrorDidOccur:(AVAudioPlayer *)player error:(NSError *)error {
    [self sendEventWithName:@"onPlaybackError" body:@{@"error": error.localizedDescription}];
}

// Método para enviar eventos a JavaScript
- (void)sendEventWithName:(NSString *)name body:(NSDictionary *)body {
    if (self.bridge) {
        [self sendEventWithName:name body:body];
    }
}

@end

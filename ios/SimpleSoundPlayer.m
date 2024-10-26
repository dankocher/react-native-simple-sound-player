// Archivo: SimpleSoundPlayer.m

#import "SimpleSoundPlayer.h"

@implementation SimpleSoundPlayer

RCT_EXPORT_MODULE(SimpleSoundPlayer)

- (NSArray<NSString *> *)supportedEvents {
    return @[@"onPlaybackEnded", @"onPlaybackError"];
}

// Método para reproducir el sonido
RCT_EXPORT_METHOD(play:(NSString *)soundName) {
    NSError *error = nil;

    // Obtén la ruta del archivo de sonido
    NSString *filePath = [[NSBundle mainBundle] pathForResource:soundName ofType:nil];
    if (!filePath) {
        [self sendEventWithName:@"onPlaybackError" body:@{@"error": @"Sound file not found"}];
        return;
    }

    NSURL *soundURL = [NSURL fileURLWithPath:filePath];
    self.audioPlayer = [[AVAudioPlayer alloc] initWithContentsOfURL:soundURL error:&error];

    if (error) {
        [self sendEventWithName:@"onPlaybackError" body:@{@"error": error.localizedDescription}];
        return;
    }

    self.audioPlayer.delegate = self;
    [self.audioPlayer prepareToPlay];
    [self.audioPlayer play];
}

// Método para detener el sonido
RCT_EXPORT_METHOD(stop) {
    if (self.audioPlayer.isPlaying) {
        [self.audioPlayer stop];
    }
}

// Método para ajustar el volumen
RCT_EXPORT_METHOD(setVolume:(float)volume) {
    if (self.audioPlayer) {
        self.audioPlayer.volume = volume;
    }
}

// Método para liberar el reproductor de audio
RCT_EXPORT_METHOD(destroy) {
    if (self.audioPlayer) {
        [self.audioPlayer stop];
        self.audioPlayer = nil;
    }
}

// Delegado de AVAudioPlayer: detecta el final de la reproducción
- (void)audioPlayerDidFinishPlaying:(AVAudioPlayer *)player successfully:(BOOL)flag {
    [self sendEventWithName:@"onPlaybackEnded" body:@{@"success": @(flag)}];
}

// Manejo de errores de reproducción
- (void)audioPlayerDecodeErrorDidOccur:(AVAudioPlayer *)player error:(NSError *)error {
    [self sendEventWithName:@"onPlaybackError" body:@{@"error": error.localizedDescription}];
}

@end

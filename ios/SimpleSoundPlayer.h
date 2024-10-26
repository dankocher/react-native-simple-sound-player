// Archivo: SimpleSoundPlayer.h

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <AVFoundation/AVFoundation.h>

@interface SimpleSoundPlayer : RCTEventEmitter <RCTBridgeModule>

// Declaración de propiedades y métodos para el módulo de sonido
@property (nonatomic, strong) AVAudioPlayer *audioPlayer; // Propiedad para manejar el audio actual

// Métodos para reproducir, detener, ajustar volumen y liberar recursos
- (void)play:(NSString *)soundName;
- (void)stop;
- (void)setVolume:(float)volume;
- (void)destroy;

@end

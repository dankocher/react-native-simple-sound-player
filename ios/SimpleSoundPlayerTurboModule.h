// Archivo: SimpleSoundPlayerTurboModule.h

#import <React/RCTBridgeModule.h>
#import <React/RCTTurboModule.h>
#import <AVFoundation/AVFoundation.h>

// Define el protocolo para el módulo Turbo de SimpleSoundPlayer
@protocol SimpleSoundPlayerTurboModuleSpec <RCTBridgeModule, RCTTurboModule>

// Métodos de la API del módulo Turbo que queremos exponer a JavaScript
- (void)play:(NSString *)soundName
     resolver:(RCTPromiseResolveBlock)resolve
     rejecter:(RCTPromiseRejectBlock)reject;

- (void)stop;

- (void)setVolume:(nonnull NSNumber *)volume;

- (void)destroy;

@end

// Interfaz de SimpleSoundPlayer que implementa el protocolo Turbo Module
@interface SimpleSoundPlayerTurboModule : NSObject <SimpleSoundPlayerTurboModuleSpec, AVAudioPlayerDelegate>

@property (nonatomic, strong) AVAudioPlayer *audioPlayer;

// Método para enviar eventos a JavaScript
- (void)sendEventWithName:(NSString *)name body:(NSDictionary *)body;

@end

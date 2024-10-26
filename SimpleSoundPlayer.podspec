require 'json'
version = JSON.parse(File.read('package.json'))["version"]

Pod::Spec.new do |s|
  s.name            = "react-native-simple-sound-player"
  s.version         = version
  s.homepage        = "https://github.com/dankocher/react-native-simple-sound-player" # Cambia "tuusuario" por tu nombre de usuario o el nombre del proyecto
  s.summary         = "A simple sound player for React Native."
  s.license         = "MIT"
  s.author          = { "Daniel" => "droque123@gmail.com" } # Cambia por tu nombre y correo
  s.ios.deployment_target = '14.0' # Cambia a la versión mínima que desees
  s.source          = { :git => "https://github.com/dankocher/react-native-simple-sound-player.git", :tag => "v#{s.version}" } # Cambia por tu repositorio
  s.source_files    = 'ios/*.{h,m}'
  s.preserve_paths  = "**/*.js"
  s.dependency 'React'

  # Si necesitas añadir otros frameworks
  s.frameworks = 'AVFoundation', 'Foundation' # AVFoundation es necesario para el audio
end

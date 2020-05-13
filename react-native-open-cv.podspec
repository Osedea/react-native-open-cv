require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-open-cv"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-open-cv
                   DESC
  s.homepage     = "https://github.com/Osedea/react-native-open-cv"
  s.license      = "MIT"
  s.authors      = { "James Morton" => "james.morton@osedea.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/Osedea/react-native-open-cv.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,mm,swift}"
  s.requires_arc = true
  s.vendored_frameworks = 'ios/Frameworks/opencv2.framework'

  s.dependency "React"
  s.dependency "OpenCV2"
end


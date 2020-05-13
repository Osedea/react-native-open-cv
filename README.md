# react-native-open-cv

This is an experimental repo.

The purpose of this repo is to facilitate the integration of the OpenCV libraries with React Native Android and iOS apps. There are no plans to maintain this repo at the moment so use it at your own risk.

## Getting started

`$ yarn add react-native-open-cv`

### Mostly automatic installation

`$ cd ios && pod install`

The source files for the OpenCV Android SDK are downloaded by the postinstall script into the android folder. The download is over 200MB so be ready to wait a while. Then files in `androidReactNative` are copied into the android source directories.

## Usage
```javascript
import OSDOpenCv from 'react-native-open-cv';

try {
    const result = await OSDOpenCv.isBlurry(imageUri.startsWith('file://') ? imageUri.slice(7) : imageUri)
    console.log(result.isBlurry);
} catch(e) {
    console.log(e);
}
```

## License

This repo is released under the MIT license but the third party code that it depends on (from OpenCV) is released under a 3-clause BSD license (see LICENSE-3RD-PARTY file, and https://opencv.org/license/ for more info).


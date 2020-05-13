# TODO: do this with a JS script for better portability
VERSION="4.3.0"

echo "Downloading OpenCV Android SDK ($VERSION)"
URL="https://sourceforge.net/projects/opencvlibrary/files/$VERSION/opencv-$VERSION-android-sdk.zip/download"

echo "GET $URL"
curl -o opencvandroid.zip -L "$URL"

echo "Unzipping SDK"
unzip opencvandroid.zip

echo "Moving SDK files to android folder"
mv OpenCV-android-sdk/sdk android

echo "Moving React Native package files to Android source directory"
cp androidReactNative/OSDOpenCvModule.java android/java/src/org/opencv/
cp androidReactNative/OSDOpenCvPackage.java android/java/src/org/opencv/
cp "androidReactNative/$VERSION.build.gradle" android/build.gradle

echo "Removing unused files"
rm -rf OpenCV-android-sdk opencvandroid.zip

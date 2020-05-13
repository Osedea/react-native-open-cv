package org.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class OSDOpenCvModule extends ReactContextBaseJavaModule {
    private static final String TAG = OSDOpenCvModule.class.getSimpleName();

    private boolean isReady = false;

    public OSDOpenCvModule(ReactApplicationContext reactContext) {
        super(reactContext);

        if (!OpenCVLoader.initDebug()) {
            // We should probably be using the initAsync method, but it shows a popup to the user...
            Log.d(TAG, "Error loading OpenCV native libraries");
            isReady = false;
        } else {
            isReady = true;
        }
    }

    @Override
    public String getName() {
        return "OSDOpenCv";
    }

    @ReactMethod
    public void isBlurry(String filePath, Promise promise) {
        if (!isReady) {
            promise.reject("NOT_READY", "OpenCV native libraries not loaded");
            return;
        }

        boolean isBlurry = isImageBlurry(filePath);

        WritableMap response = Arguments.createMap();
        response.putBoolean("isBlurry", isBlurry);

        promise.resolve(response);
    }

    public boolean isImageBlurry(String filePath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            File imageFile = new File(filePath);
            Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

            int l = CvType.CV_8UC1; //8-bit grey scale image
            Mat matImage = new Mat();
            Utils.bitmapToMat(image, matImage);
            Mat matImageGrey = new Mat();
            Imgproc.cvtColor(matImage, matImageGrey, Imgproc.COLOR_BGR2GRAY);

            Bitmap destImage;
            destImage = Bitmap.createBitmap(image);
            Mat dst2 = new Mat();
            Utils.bitmapToMat(destImage, dst2);
            Mat laplacianImage = new Mat();
            dst2.convertTo(laplacianImage, l);
            Imgproc.Laplacian(matImageGrey, laplacianImage, CvType.CV_8U);
            Mat laplacianImage8bit = new Mat();
            laplacianImage.convertTo(laplacianImage8bit, l);

            Bitmap bmp = Bitmap.createBitmap(laplacianImage8bit.cols(), laplacianImage8bit.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(laplacianImage8bit, bmp);
            int[] pixels = new int[bmp.getHeight() * bmp.getWidth()];
            bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
            int maxLap = -16777216; // 16m
            for (int pixel : pixels) {
                if (pixel > maxLap)
                    maxLap = pixel;
            }

            int soglia = -6118750;
//            int soglia = -8118750;
            boolean isBlurry = maxLap <= soglia;

            Log.d(TAG, "isBlurry native result: " + isBlurry + " (" + maxLap + ")");

            return isBlurry;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

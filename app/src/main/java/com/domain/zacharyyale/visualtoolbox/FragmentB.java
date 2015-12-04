package com.domain.zacharyyale.visualtoolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class FragmentB extends Fragment {

    private static final String TAG = "ZTG";
    RelativeLayout relativeLayout;
    FrameLayout savedImage = null;
    Button analysisButton;
    TextView analysisText;
    ImageView analysisImage;
    Bitmap bm = null;
    Bitmap bitmap1;

    // Native camera.
    private Camera mCamera;

    // View to display the camera output.
    private CameraPreview mPreview;

    // Reference to the containing view.
    private View mCameraView;
    Bitmap bitmap;
    /**
     * Default empty constructor.
     */
    public FragmentB(){

    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Define UI items
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        analysisText = (TextView) view.findViewById(R.id.analysisTextView);
        analysisImage = (ImageView) view.findViewById(R.id.analysisImageView);
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.id.preview);

        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        //Initialize analysis image
        analysisImage.setBackgroundColor(00000000);
        //Initialize analysis text
        analysisText.setText("");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        //boolean opened = safeCameraOpenInView(view);

        /*if(opened == false){
            Log.d("CameraGuide","Error: Camera failed to open");
            return view;
        }*/

        analysisButton = (Button) view.findViewById(R.id.analysisButtonItem);
        analysisButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(final View v) {

                                                  long timeAtStart = System.currentTimeMillis();
                                                  TextView rgbDisplay =  (TextView) getActivity().findViewById(R.id.analysisTextView);
                                                  Display display = getActivity().getWindowManager().getDefaultDisplay();
                                                  int width = display.getWidth();
                                                  int height = display.getHeight();
                                                  Log.d(TAG, "Width and Height Retrieved As: " + width + ", " + height);
                                                  Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config. RGB_565);
                                                  Canvas c = new Canvas(b);
                                                  CameraPreview view = (CameraPreview) ((ViewGroup) getActivity().findViewById(R.id.preview)).getChildAt(0);
                                                  view.draw(c);
                                                  String hexValue;
                                                  int centerX = (display.getWidth() / 2);
                                                  int centerY = (display.getHeight() / 2);

                                                  int test;


                                                  //test = b.getPixel(240, 350);
                                                  int sampleWidth = 9;
                                                  int sampleHeight = 9;
                                                  int[] pixels = new int[sampleWidth * sampleHeight];
                                                  b.getPixels(pixels, 0, 9, centerX - 4, centerY - 4, sampleWidth, sampleHeight);
                                                  int l = 0;
                                                  int tempNum;
                                                  int tempBlue;
                                                  int tempRed;
                                                  int tempGreen;
                                                  int blue = 0; //Color.blue(test);
                                                  int red = 0; //Color.red(test);
                                                  int green = 0; // Color.green(test);
                                                  test = 0;
                                                  Log.d("lookingFor", "test: " + pixels[1]);
                                                  while(l < 81){
                                                      tempNum = (Integer) pixels[l];
                                                      Log.d("lookingFor", "Pixel Num: " + Color.blue(tempNum));
                                                      tempBlue = Color.blue(tempNum);
                                                      tempRed = Color.red(tempNum);
                                                      tempGreen = Color.green(tempNum);
                                                      Log.d("lookingFor", "current Blue: " + tempBlue);
                                                      blue = blue + tempBlue;
                                                      Log.d("lookingFor", "added blue: " + blue);
                                                      red = red + tempRed;
                                                      green = green + tempGreen;
                                                      l = l + 1; //test g

                                                  }
                                                  Log.v("lookingFor", blue + " " + red + " " + green);
                                                  blue = blue / 81;
                                                  red = red / 81;
                                                  green = green / 81;

                                                  hexValue = Integer.toHexString(test);
                                                  Log.d(TAG, "pixel at (" + centerX + ", " + centerY + " succesfully retreived! with value of: " + test);

                                                  Log.d(TAG, "and an Hex value of: " + hexValue);
                                                  //  blue = Color.blue(test);
                                                  //  red = Color.red(test);
                                                  //  green = Color.green(test);
                                                  //this is a modification

                                                  Log.d(TAG, "RGB COLOR! R:" + red + " G:" + green + " B:" + blue);
                                                  long timeAtEnd = System.currentTimeMillis();
                                                  long totalTime = timeAtEnd - timeAtStart;
                                                  Log.d(TAG, "Fetching the color took " + totalTime + " milliseconds");
                                                  rgbDisplay.setText("R:" + red + " G:" + green + " B:" + blue);

                                                  //analysisText.setText("" + thiscolor);
                                                  //analysisImage.setBackgroundColor(domColour);
                                              }
                                          }
        );

        return view;
    }

    public static int getDominantColor(Bitmap bitmap) {
        bitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        int color = bitmap.getPixel(0, 0);
        return color;
    }

    private int getAverageColor(Bitmap image) {

        //Setup initial variables
        int hSamples = 20;						//Number of pixels to sample on horizontal axis

        int vSamples = 20;						//Number of pixels to sample on vertical axis
        int sampleSize = hSamples * vSamples;	//Total number of pixels to sample
        float[] sampleTotals = {0, 0, 0};		//Holds temporary sum of HSV values

        //If white pixels are included in sample, the average color will
        //  often have an unexpected shade. For this reason, we set a
        //  minimum saturation for pixels to be included in the sample set.
        //  Saturation < 0.1 is very close to white (see http://mkweb.bcgsc.ca/color_summarizer/?faq)
        float minimumSaturation = 0.1f;			//Saturation range is 0...1

        //By the same token, we should ignore transparent pixels
        //  (pixels with low alpha value)

        int minimumAlpha = 200;					//Alpha range is 0...255

        //Get bitmap
        //Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap b = image;
        int width = b.getWidth();
        int height = b.getHeight();
        //Loop through pixels horizontally
        float[] hsv = new float[3];
        int sample;
        for(int i=0; i<width; i+=(width/hSamples)) {
            //Loop through pixels vertically
            for(int j=0; j<height; j+=(height/vSamples)) {

                //Get pixel & convert to HSV format
                sample = b.getPixel(i, j);
                Color.colorToHSV(sample, hsv);
                //Check pixel matches criteria to be included in sample

                if((Color.alpha(sample)>minimumAlpha) && (hsv[1] >= minimumSaturation)) {

                    //Add sample values to total
                    sampleTotals[0] += hsv[0];	//H
                    sampleTotals[1] += hsv[1];	//S
                    sampleTotals[2] += hsv[2];	//V

                }

            //else {

                //Log.d(TAG, "Pixel rejected: Alpha "+Color.alpha(sample)+", H: "+hsv[0]+", S:"+hsv[1]+", V:"+hsv[1]);

            //}

            }

        }

        //Divide total by number of samples to get average HSV values

        float[] average = new float[3];

        average[0] = sampleTotals[0] / sampleSize;
        average[1] = sampleTotals[1] / sampleSize;
        average[2] = sampleTotals[2] / sampleSize;

        //Return average tuplet as RGB color
        return Color.HSVToColor(average);
    }

   /* private boolean safeCameraOpenInView(View view) {
        boolean qOpened = false;
        releaseCameraAndPreview();
        mCamera = getCameraInstance();
        qOpened = (mCamera != null);
        mPreview = new CameraPreview(getActivity().getBaseContext(), mCamera, view);
        SurfaceView preview = (SurfaceView) view.findViewById(R.id.preview);
        preview.addView(mPreview);
        return qOpened;
    }*/

    /**
     * Safe method for getting a camera instance.
     * @return
     */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseCameraAndPreview();
    }

    /**
     * Clear any existing preview / camera.
     */
    private void releaseCameraAndPreview() {

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        if(mPreview != null){
            mPreview.destroyDrawingCache();
            mPreview.mCamera = null;
        }
    }

    /**
     * Surface on which the camera projects it's capture results. This is derived both from Google's docs and the
     * excellent StackOverflow answer provided below.
     *
     * Reference / Credit: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
     */
    class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

        // SurfaceHolder
        private SurfaceHolder mHolder;

        // Our Camera.
        private Camera mCamera;

        // Parent Context.
        private Context mContext;

        // Camera Sizing (For rotation, orientation changes)
        private Camera.Size mPreviewSize;

        // List of supported preview sizes
        private List<Camera.Size> mSupportedPreviewSizes;

        // Flash modes supported by this camera
        private List<String> mSupportedFlashModes;

        // View holding this camera.
        private View mCameraView;

        public CameraPreview(Context context, Camera camera, View cameraView) {
            super(context);

            // Capture the context
            mCameraView = cameraView;
            mContext = context;
            setCamera(camera);

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setKeepScreenOn(true);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        /**
         * Begin the preview of the camera input.
         */
        public void startCameraPreview()
        {
            try{
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        /**
         * Extract supported preview and flash modes from the camera.
         * @param camera
         */
        private void setCamera(Camera camera)
        {
            // Source: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
            mCamera = camera;
            mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
            mSupportedFlashModes = mCamera.getParameters().getSupportedFlashModes();

            // Set the camera to Auto Flash mode.
            if (mSupportedFlashModes != null && mSupportedFlashModes.contains(Camera.Parameters.FLASH_MODE_AUTO)){
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                mCamera.setParameters(parameters);
            }

            requestLayout();
        }

        /**
         * The Surface has been created, now tell the camera where to draw the preview.
         * @param holder
         */
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.setDisplayOrientation(90);
        }

        /**
         * Dispose of the camera preview.
         * @param holder
         */
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mCamera != null){
                mCamera.stopPreview();
            }
        }

        /**
         * React to surface changed events
         * @param holder
         * @param format
         * @param w
         * @param h
         */
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.

            if (mHolder.getSurface() == null){
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                // Set the auto-focus mode to "continuous"
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

                List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
                Camera.Size optimalSize = getOptimalPreviewSize(sizes, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);

                // Preview size must exist.
                if(mPreviewSize != null) {
                    Camera.Size previewSize = mPreviewSize;
                    //parameters.setPreviewSize(previewSize.width, previewSize.height);
                    parameters.setPreviewSize(optimalSize.width, optimalSize.height);
                }

                mCamera.setParameters(parameters);
                mCamera.startPreview();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * Calculate the measurements of the layout
         * @param widthMeasureSpec
         * @param heightMeasureSpec
         */
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
            // Source: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
            final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            setMeasuredDimension(width, height);

            if (mSupportedPreviewSizes != null){
                mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
            }
        }

        /**
         * Update the layout based on rotation and orientation changes.
         * @param changed
         * @param left
         * @param top
         * @param right
         * @param bottom
         */
        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom)
        {
            // Source: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
            if (changed) {
                final int width = right - left;
                final int height = bottom - top;

                int previewWidth = width;
                int previewHeight = height;

                if (mPreviewSize != null){
                    Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

                    switch (display.getRotation())
                    {
                        case Surface.ROTATION_0:
                            previewWidth = mPreviewSize.height;
                            previewHeight = mPreviewSize.width;
                            mCamera.setDisplayOrientation(90);
                            break;
                        case Surface.ROTATION_90:
                            previewWidth = mPreviewSize.width;
                            previewHeight = mPreviewSize.height;
                            break;
                        case Surface.ROTATION_180:
                            previewWidth = mPreviewSize.height;
                            previewHeight = mPreviewSize.width;
                            break;
                        case Surface.ROTATION_270:
                            previewWidth = mPreviewSize.width;
                            previewHeight = mPreviewSize.height;
                            mCamera.setDisplayOrientation(180);
                            break;
                    }
                }

                final int scaledChildHeight = previewHeight * width / previewWidth;
                mCameraView.layout(0, height - scaledChildHeight, width, height);
            }
        }

        /*
         *
         * @param sizes
         * @param width
         * @param height
         * @return
         */
        /*private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height)
        {
            // Source: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
            Camera.Size optimalSize = null;

            final double ASPECT_TOLERANCE = 0.1;
            double targetRatio = (double) height / width;

            // Try to find a size match which suits the whole screen minus the menu on the left.
            for (Camera.Size size : sizes){

                if (size.height != width) continue;
                double ratio = (double) size.width / size.height;
                if (ratio <= targetRatio + ASPECT_TOLERANCE && ratio >= targetRatio - ASPECT_TOLERANCE){
                    optimalSize = size;
                }
            }

            // If we cannot find the one that matches the aspect ratio, ignore the requirement.
            if (optimalSize == null) {
                // TODO : Backup in case we don't get a size.
            }

            return optimalSize;
        }*/

        private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
            final double ASPECT_TOLERANCE = 0.05;
            double targetRatio = (double) w/h;

            if (sizes==null) return null;

            Camera.Size optimalSize = null;

            double minDiff = Double.MAX_VALUE;

            int targetHeight = h;

            // Find size
            for (Camera.Size size : sizes) {
                double ratio = (double) size.width / size.height;
                if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }

            if (optimalSize == null) {
                minDiff = Double.MAX_VALUE;
                for (Camera.Size size : sizes) {
                    if (Math.abs(size.height - targetHeight) < minDiff) {
                        optimalSize = size;
                        minDiff = Math.abs(size.height - targetHeight);
                    }
                }
            }
            return optimalSize;
        }

    }


}
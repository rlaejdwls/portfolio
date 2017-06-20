package com.example.opencv.demo.widget;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.OrientationEventListener;
import android.view.Surface;

import org.opencv.android.JavaCameraView;

/**
 * Created by Hwang on 2017-04-25.
 */
public class TGCameraView extends JavaCameraView {
    private OrientationEventListener orientationListener = null;
    private Activity activity;

    public TGCameraView(Context context, int cameraId) {
        super(context, cameraId);
    }
    public TGCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected boolean connectCamera(int width, int height) {
        boolean result = super.connectCamera(width, height);
        if (result) {
            initCameraOrientation();
        }
        return result;
    }

    public void initCameraOrientation() {
        orientationListener = new OrientationEventListener(getContext()) {
            public void onOrientationChanged(int orientation) {
                setCameraDisplayOrientation(activity, mCameraIndex, mCamera);
            }
        };
    }
    public void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}

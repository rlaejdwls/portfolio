package com.example.ar.demo.view;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.core.manage.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by Hwang on 2017-04-26.
 * 작성자 : 황의택
 * 내용 : Camera를 화면에 출력하기 위한 Surface View
 *        //!issue
 *        안드로이드에서 공식적으로 제공하고 있는 camera2 api가 아직 미숙하여 구 api로 구현함
 *        추후에 바꿀 필요성이 있음
 */
public class DisplayVIew extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceHolder holder;
    private Activity activity;

    public DisplayVIew(Context context, Activity activity) {
        super(context);
        init(activity);
    }
    public DisplayVIew(Context context, Activity activity, AttributeSet attrs) {
        super(context, attrs);
        init(activity);
    }
    public DisplayVIew(Context context, Activity activity, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(activity);
    }
    public void init(Activity activity) {
        this.activity = activity;
        this.holder = getHolder();
        this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();

        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        camera.setDisplayOrientation((info.orientation - degrees + 360) % 360);

        try {
            camera.setPreviewDisplay(getHolder());
        } catch (IOException e) {
            Logger.printStackTrace(e);
        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = camera.getParameters();
        List<Camera.Size> prevSizes = params.getSupportedPreviewSizes();
        for (Camera.Size s : prevSizes) {
            if((s.height <= height) && (s.width <= width)) {
                params.setPreviewSize(s.width, s.height);
                break;
            }
        }

        camera.setParameters(params);
        camera.startPreview();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }

    public void onResume() {
        camera.startPreview();
    }

    public void onPause() {
        camera.stopPreview();
    }
}

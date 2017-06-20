package com.example.opencv.demo.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.example.opencv.demo.R;
import com.example.core.manage.Logger;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;

/**
 * Created by Hwang on 2017-04-21.
 * 작성자 : 황의택
 * 내용 : 여러 테스트용 ~
 */
public class PlaygroundCameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private CameraBridgeViewBase camera;

    private Mat input;
    private Mat result;

    private boolean isCapture = false;

    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("native-lib");
    }

    private BaseLoaderCallback callback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    camera.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public native void histo(long input/*, long result*/);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_playground_camera);

        String joro = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Temp/background.jpg";
        Bitmap bitmap =  BitmapFactory.decodeFile(joro);
        Logger.d("exists::" + new File(joro).exists());
        if (input == null) input = new Mat();
        Utils.bitmapToMat(bitmap, input);

        camera = (CameraBridgeViewBase) findViewById(R.id.camera);
        camera.setVisibility(SurfaceView.VISIBLE);
        camera.setCvCameraViewListener(this);
        camera.setCameraIndex(0); // front-camera(1),  back-camera(0)
        camera.setMaxFrameSize(640, 480);
        callback.onManagerConnected(LoaderCallbackInterface.SUCCESS);

        this.findViewById(R.id.btnCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCapture = !isCapture;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, callback);
        } else {
            callback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)
            camera.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null)
            camera.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
    }

    @Override
    public void onCameraViewStopped() {
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        if (isCapture) {
            isCapture = false;
            histo(input.getNativeObjAddr());
        }
        return input;
    }
}

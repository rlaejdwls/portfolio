package com.example.opencv.demo.effect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.opencv.demo.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 * Created by Hwang on 2017-04-21.
 * 작성자 : 황의택
 * 내용 : 입력된 영상에 영상 처리 효과를 입히는 카메라
 */
public class EffectCameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private TextView lblInfo;
    private CameraBridgeViewBase camera;

    private Mat matInput;
    private Mat matResult;

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

    public native void ConvertRGBtoGray(long input, long result);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_effect_camera);

        lblInfo = (TextView) this.findViewById(R.id.lblInfo);
        camera = (CameraBridgeViewBase) findViewById(R.id.camera);
        camera.setVisibility(SurfaceView.VISIBLE);
        camera.setCvCameraViewListener(this);
        camera.setCameraIndex(0); // front-camera(1),  back-camera(0)
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
        matInput = inputFrame.rgba();
        Core.flip(matInput, matInput, 1);

        if ( matResult != null ) matResult.release();
        matResult = new Mat();

        if (isCapture) {
            ConvertRGBtoGray(matInput.getNativeObjAddr(), matResult.getNativeObjAddr());
        } else {
            matResult = matInput;
        }

        return matResult;
    }
}

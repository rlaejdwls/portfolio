package com.example.opencv.demo.detect;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.example.core.manage.Logger;
import com.example.opencv.demo.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.FONT_HERSHEY_DUPLEX;

/**
 * Created by Hwang on 2017-04-21.
 * 작성자 : 황의택
 * 내용 : 기본 Detect Camera Activity
 */
public abstract class DetectCameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    protected CameraBridgeViewBase camera;

    protected Processor processor;

    protected String imgDir;
    protected boolean isCapture = false;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_detect_camera);

        imgDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NFData/image/";

        processor = new Processor();
        processor.startRunning();

        camera = (CameraBridgeViewBase) findViewById(R.id.camera);
        camera.setVisibility(SurfaceView.VISIBLE);
        camera.setCvCameraViewListener(this);
        camera.setCameraIndex(0); // front-camera(1),  back-camera(0)
        camera.setMaxFrameSize(1280, 720);
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
        if (processor != null)
            processor.resumeRunning();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)
            camera.disableView();
        if (processor != null)
            processor.pauseRunning();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null)
            camera.disableView();
        if (processor != null)
            processor.stopRunning();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
    }

    @Override
    public void onCameraViewStopped() {
    }

    //영상 처리 쓰레드
    public class Processor extends Thread {
        private Mat input = null;

        private boolean isRunning = false;
        private boolean isPause = false;

        public Processor() {
        }

        public synchronized Mat getInput() {
            return input;
        }
        public synchronized void setInput(Mat input) {
            this.input = input;
        }
        public void startRunning() {
            isRunning = true;
            this.start();
        }
        public void stopRunning() {
            isRunning = false;
        }
        public void pauseRunning() {
            isPause = true;
        }
        public void resumeRunning() {
            isPause = false;
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    if (isPause) {
                        Thread.sleep(300);
                        continue;
                    }
                    running();
                }
            } catch (Exception e) {
                Logger.printStackTrace(e);
            }
        }
    }
    public abstract void running() throws Exception;

    public void printMemory(Mat input) {
        printMemory(input, null);
    }
    public void printMemory(Mat input, String info) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double percentAvail = mi.availMem / (double)mi.totalMem;
        drawText(input, Math.round(percentAvail * 100) + "%" + (info != null ? ", " + info : ""));
    }

    public void drawText(Mat input, String text) {
        Point ptText = new Point(20, input.rows() - 20);
        Imgproc.putText(input, text, ptText, FONT_HERSHEY_DUPLEX, 1, new Scalar(0, 0, 0));
    }
}

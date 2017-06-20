package com.example.opencv.demo.detect;

import android.app.ActivityManager;
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
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

import static org.opencv.core.Core.FONT_HERSHEY_DUPLEX;
import static org.opencv.imgproc.Imgproc.equalizeHist;

/**
 * Created by Hwang on 2017-04-21.
 * 작성자 : 황의택
 * 내용 : 입력되고 있는 영상을 기반으로 미리 분석되어 있는
 *        이미지 중에서 가장 유사도가 높은 이미지를 도출하는 카메라
 */
public class _BackupDetectCameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private CameraBridgeViewBase camera;

    private Mat input;
//    private Mat result;

    private Processor processor;

    private boolean isCapture = false;
    private boolean isDetect = true;

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

//    public native void drawRect(long facesCascade, long input/*, long result*/);
//    public native long loadCascade(String path);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_detect_camera);

        processor = new Processor();
        processor.startRunning();
//        loadCascade();

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
        if (processor != null)
            processor.stopRunning();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
    }

    @Override
    public void onCameraViewStopped() {
    }

    /*
    //!plan
    테스트 클래스
    * 문제점 : 프레임마다 영상을 처리할 경우 너무 방대한 양의 데이터를 처리해야함
    * 대안
      - 처리하는 데이터를 줄여야함
      - 데이터가 발생되는 onCameraFrame 함수와 데이터를 처리하는 프로세스 사이에 데이터의 양을 조절하는 중재 클래스가 필요함
      - 예상하는 처리과정은 다음과 같음
       1. onCameraFrame에서 데이터 발생
       2. 모든 데이터는 중재 클래스로 전송됨
       3. 중재 클래스는 처리해야하는 데이터를 선별함
       4. 영상이 끊기면 안되므로 선별된 데이터외에는 바로 반환함
       5. 처리 프로세스는 중재 클래스에 선별된 데이터가 있는지 문의하고
       6. 선별된 데이터가 있을 경우 데이터 처리를 시작함
       7. 데이터 처리가 완료 되었을 경우 중재 클래스에 알림
       8. 3번부터 7번 프로세스 반복
     */
    public class Processor extends Thread {
        private CascadeClassifier faceCascade;
        private MatOfRect faces = new MatOfRect();
        private Mat input = null;

        private boolean isRunning = false;

        public Processor() {
            String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NFData/";
            String haarcascades = "haarcascades";
            String fullpath = root + haarcascades + "/haarcascade_frontalface_alt2.xml";
            Logger.d("Cascade::" + new File(fullpath).exists());
            faceCascade = new CascadeClassifier(fullpath);
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

        @Override
        public void run() {
            while (isRunning) {
                if (this.getInput() != null) {
                    detect(this.getInput());
                    printMemory(this.getInput());
                    this.setInput(null);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    isDetect = true;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private Mat preProcessing(Mat img) {
            Mat gray = new Mat();
            Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);
            equalizeHist(gray, gray);

            return gray;
        }

        public void detect(Mat input) {
            Mat gray = preProcessing(input);
            faceCascade.detectMultiScale(gray, faces, 1.1, 2, 0, new Size(30, 30), new Size(input.cols(), input.rows()));
        }

        public void drawRect(Mat input) {
            for (Rect rect : faces.toList()) {
                Imgproc.rectangle(input, rect.tl(), rect.br(), new Scalar(255, 0, 0));
            }
        }

        public void printMemory(Mat input) {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
//            double availableMegs = mi.availMem / 0x100000L;
            double percentAvail = mi.availMem / (double)mi.totalMem;
            drawText(input, Math.round(percentAvail * 100) + "%, count:" + faces.size());
        }

        public void drawText(Mat input, String text) {
            Point ptText = new Point(20, input.rows() - 20);
            Imgproc.putText(input, text, ptText, FONT_HERSHEY_DUPLEX, 1, new Scalar(0, 0, 0));
        }
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        input = inputFrame.rgba();

        if (isCapture) {
            if (isDetect) {
                isDetect = false;
                processor.setInput(input);
            }
            processor.drawRect(input);
            processor.printMemory(input);
        }

        return input;
    }
}

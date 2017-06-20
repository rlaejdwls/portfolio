package com.example.opencv.demo.detect;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.example.core.manage.Logger;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

import static org.opencv.imgproc.Imgproc.equalizeHist;

/**
 * Created by Hwang on 2017-04-24.
 * 작성자 : 황의택
 * 내용 : 이거 얼굴임?
 */
public class FaceDetectCameraActivity extends DetectCameraActivity {
    private Mat input;
    private Mat gate = null;

    private CascadeClassifier faceCascade;
    private MatOfRect faces = new MatOfRect();

    private boolean isDetect = true;

    public synchronized Mat getGate() {
        return gate;
    }
    public synchronized void setGate(Mat gate) {
        this.gate = gate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCascade();
    }

    public void loadCascade() {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NFData/";
        String haarcascades = "haarcascades";
        String fullpath = root + haarcascades + "/haarcascade_frontalface_alt2.xml";
        Logger.d("Cascade::" + new File(fullpath).exists());
        faceCascade = new CascadeClassifier(fullpath);
    }

    @Override
    public void running() throws Exception {
        if (this.getGate() != null) {
            detect(this.getGate());
            this.setGate(null);
            Thread.sleep(100);
            isDetect = true;
        } else {
            Thread.sleep(100);
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

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        input = inputFrame.rgba();

        if (isCapture) {
            if (isDetect) {
                isDetect = false;
                this.setGate(input);
            }
            this.drawRect(input);
            this.printMemory(input, "count:" + faces.size());
        }

        return input;
    }
}

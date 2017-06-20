package com.example.opencv.demo.detect;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/*
Method	        Base - Base	    Base - Half	    Base - Test 1	Base - Test 2
Correlation	    1.000000	    0.930766	    0.182073	    0.120447
Chi-square	    0.000000	    4.940466	    21.184536	    49.273437
Intersection	24.391548	    14.959809	    3.889029	    5.775088
Bhattacharyya	0.000000	    0.222609	    0.646576	    0.801869
*/
/**
 * Created by Hwang on 2017-04-24.
 * 작성자 : 황의택
 * 내용 : 이거 조로임?
 */
public class _BackupJoroDetectCameraActivity extends DetectCameraActivity {
    private Mat input;
    private Mat gate = null;

    private boolean isDetect = true;

    public synchronized Mat getGate() {
        return gate;
    }
    public synchronized void setGate(Mat gate) {
        this.gate = gate;
    }

    public native boolean detect(long templ, String path);

    private final int HIST_SIZE = 180;

    Mat img;
//    Mat templ;

    Mat imgHsv = new Mat();
    Mat templHsv = new Mat();

    List<Mat> imgHist = new ArrayList<>();
    List<Mat> templHist = new ArrayList<>();

    Mat imgBGR = new Mat();
    Mat templBGR = new Mat();

    List<Mat> imgVector = new ArrayList<>();
    List<Mat> templVector = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String imgOriginalPath = DemoApp.get("DIR_DATA") + "/hist";
//        String matOriginalPath = imgOriginalPath + "/joro.mat";
//        File matOriginalFile = new File(matOriginalPath);
//        if (!matOriginalFile.exists()) {
//            MatUtils.write(matOriginalPath, Imgcodecs.imread(imgDir + "test_joro_small.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR));
//        }

        imgBGR = Imgcodecs.imread(imgDir + "test_joro_small.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Imgproc.cvtColor(imgBGR, imgHsv, Imgproc.COLOR_BGR2HSV);

//        Core.split(imgBGR, imgVector);
        Core.split(imgHsv, imgVector);
        for (int i = 0; i < imgVector.size(); i++) {
            imgHist.add(new Mat());
            this.calcHist(imgVector.get(i), imgHist.get(i), HIST_SIZE);
        }
    }

    @Override
    public void running() throws Exception {
        if (this.getGate() != null) {
            Imgproc.cvtColor(this.getGate(), templBGR, Imgproc.COLOR_BGRA2BGR);
            Imgproc.cvtColor(templBGR, templHsv, Imgproc.COLOR_BGR2HSV);
//            Core.split(templBGR, templVector);
            Core.split(templHsv, templVector);
            for (int i = 0; i < templVector.size(); i++) {
                templHist.add(new Mat());
                this.calcHist(templVector.get(i), templHist.get(i), HIST_SIZE);
            }

            double raw = 0.0;
            for (int i = 0; i < templVector.size(); i++) {
                raw += this.detect(imgHist.get(i), templHist.get(i));
            }
            result = Math.round(raw * 100) + "";
            this.setGate(null);
            isDetect = true;
        } else { Thread.sleep(100); }
    }

    public String result = "";

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        input = inputFrame.rgba();

        if (isCapture) {
            if (isDetect) {
                isDetect = false;
                this.setGate(input);
            }
            this.printMemory(input, result);
        }

        return input;
    }

    ArrayList<Mat> histImages = new ArrayList<>();

    public void calcHist(Mat image, Mat hist, int bins) {
        calcHist(image, hist, bins, 256f);
    }
//    public void calcHist(Mat image, Mat hist, int bin, float range) {
////        int hBins = 50;
////        int sBins = 60;
//        int[] bins = new int[image.channels()];
//        for (int i = 0; i < bins.length; i++) {
//            bins[i] = bin;
//        }
//        MatOfInt histSize = new MatOfInt(bins);
//
//        float[] ranges = new float[image.channels() * 2];
//        for (int i = 0; i < ranges.length; i = i + 2) {
//            ranges[i] = 0f;
//            ranges[i+1] = range;
//        }
//        // hue varies from 0 to 179, saturation from 0 to 255
//        MatOfFloat histRanges =  new MatOfFloat(ranges);
//
//        // we compute the histogram from the 0-th and 1-st channels
//        int[] channels = new int[image.channels()];
//        for (int i = 0; i < channels.length; i++) {
//            channels[i] = i;
//        }
//        MatOfInt histChannels = new MatOfInt(channels);
//
//        histImages.clear();
//        histImages.add(image);
//        Imgproc.calcHist(histImages, histChannels, new Mat(), hist, histSize, histRanges, false);
//        Core.normalize(hist, hist, 0, 100, Core.NORM_MINMAX, -1, new Mat());
//    }

    public void calcHist(Mat image, Mat hist, int bin, float range) {
        MatOfInt histSize = new MatOfInt(bin);

        // hue varies from 0 to 179, saturation from 0 to 255
        MatOfFloat histRanges =  new MatOfFloat(0f, range);

        // we compute the histogram from the 0-th and 1-st channels
        MatOfInt histChannels = new MatOfInt(0);

        histImages.clear();
        histImages.add(image);
        Imgproc.calcHist(histImages, histChannels, new Mat(), hist, histSize, histRanges, false);
        Core.normalize(hist, hist, 0, 1, Core.NORM_MINMAX, -1, new Mat());
    }
    public double detect(Mat histBase, Mat histTarget) {
        return Imgproc.compareHist(histBase, histTarget, 0);
    }
}

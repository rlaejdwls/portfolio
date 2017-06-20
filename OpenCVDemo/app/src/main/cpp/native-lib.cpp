#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/opencv.hpp>

using namespace cv;
using namespace std;

extern "C" {
/**
 * Playground
 */
void calcHisto(Mat image, Mat &hist, int bins, int range_max = 256) {
    hist = Mat(bins, 1, CV_32F, Scalar(0));
    float gap = range_max / (float) bins;

    for (int i = 0; i < image.rows; i++) {
        for (int j = 0; j < image.cols; j++) {
            int idx = int(image.at<uchar>(i, j) / gap);
            hist.at<float>(idx)++;
        }
    }
}

JNIEXPORT void JNICALL
Java_com_example_opencv_demo_example_PlaygroundCameraActivity_histo(JNIEnv *env, jobject instance,
                                                                    jlong rawInput/*, jlong rawResult*/) {
    Mat &input = *(Mat *) rawInput;
    //Mat &result = *(Mat *) rawResult;

    Mat hist;
    calcHisto(input, hist, 256);
    cout << hist.t() << endl;
}
}

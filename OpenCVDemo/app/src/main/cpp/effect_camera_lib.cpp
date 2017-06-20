//
// Created by Hwang on 2017-04-21.
// 작성자 : 황의택
// 내용 : EffrctCameraActivity의 lib
//
#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/opencv.hpp>

using namespace cv;
using namespace std;

extern "C" {
/**
 * Effect Camera Activity
 */
JNIEXPORT void JNICALL
Java_com_example_opencv_demo_effect_EffectCameraActivity_ConvertRGBtoGray(JNIEnv *env, jobject instance,
                                                                   jlong input,
                                                                   jlong result) {
    Mat &matInput = *(Mat *) input;
    Mat &matResult = *(Mat *) result;

    cvtColor(matInput, matResult, CV_RGBA2GRAY);
}
}
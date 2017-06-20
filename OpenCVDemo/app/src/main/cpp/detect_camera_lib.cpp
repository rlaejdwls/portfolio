//
// Created by Hwang on 2017-04-21.
// 작성자 : 황의택
// 내용 : DetectCameraActivity의 lib
//
#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/opencv.hpp>

using namespace cv;
using namespace std;

extern "C" {
/**
 * Detect Camera Activity
 */
JNIEXPORT jboolean JNICALL
Java_com_example_opencv_demo_detect__1BackupJoroDetectCameraActivity_detect__JLjava_lang_String_2(JNIEnv *env, jobject instance,
                                                                    jlong rawTempl, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    Mat img = imread(path, 1);
    Mat &templ = *(Mat *) rawTempl;

    /// Do the Matching and Normalize
    try {
        matchTemplate(img, templ, Mat(), 0);
        return true;
    } catch (Exception e) {
        return false;
    }
}

/**
 * Example
 */
void drawText(Mat input, string text) {
    Point ptText(20, input.rows - 20);
    putText(input, text, ptText, FONT_HERSHEY_DUPLEX, 2, Scalar(0, 0, 0));
}

void translation(Mat input, Mat& result, Point pt) {
    Rect rect(Point(0, 0), input.size());
    result = Mat(input.size(), input.type(), Scalar(0));

    for (int i = 0; i < result.rows; i++) {
        for (int j = 0; j < result.cols; j++) {
            Point result_pt(j, i);
            Point input_pt = result_pt - pt;
            if (rect.contains(input_pt))
                result.at<uchar>(result_pt) = input.at<uchar>(input_pt);
        }
    }
}

void average(Mat img, Mat& dst, int size) {
    dst = Mat(img.size(), CV_8U, Scalar(0));

    for (int i = 0; i < img.rows; i++) {
        for (int j = 0; j < img.cols; j++) {
            Point pt1 = Point(j - size / 2, i - size / 2);
            Point pt2 = pt1 + (Point) Size(size, size);

            if (pt1.x < 0) pt1.x = 0;
            if (pt1.y < 0) pt1.y = 0;
            if (pt2.x > img.cols) pt2.x = img.cols;
            if (pt2.y > img.rows) pt2.y = img.rows;

            Rect mask_rect(pt1, pt2);
            Mat mask = img(mask_rect);
            dst.at<uchar>(i, j) = (uchar)mean(mask)[0];
        }
    }
}

JNIEXPORT jlong JNICALL
Java_com_example_opencv_demo_detect_DetectCameraActivity_loadCascade__Ljava_lang_String_2(
        JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    return (jlong) new CascadeClassifier(path);
}

Mat preprocessing(Mat img) {
    Mat gray;
    cvtColor(img, gray, CV_BGR2GRAY);
    equalizeHist(gray, gray);

    return gray;
}

//Point2d calcCenter(Rect obj) {
//    Size_<int> c = obj.size() / 2;
//    Point2d center = (Point2d)obj.tl() + c;
//    return center;
//}

JNIEXPORT void JNICALL
Java_com_example_opencv_demo_detect_DetectCameraActivity_drawRect(JNIEnv *env, jobject instance,
                                                                  jlong rawFaceCascade,
                                                                  jlong rawInput,
                                                                  jlong rawResult) {
    Mat &input = *(Mat *) rawInput;
//    Mat &result = *(Mat *) rawResult;

    //example
//    average(input, result, 5);
//    translation(input, result, Point(30, 80));
//    blur(input, result, Size(5, 5));
//    boxFilter(input, result, -1, Size(10, 10));
//    drawText(input, "info view");

//    result = input.clone();

    CascadeClassifier *face_cascade = ((CascadeClassifier *) rawFaceCascade);

    Mat gray = preprocessing(input);

    vector<Rect> faces;
    face_cascade->detectMultiScale(gray, faces, 1.1, 2, 0, Size(100, 100));

    for (int i = 0; i < faces.size(); i++) {
//        Point center(faces[i].x + faces[i].width/2, faces[i].y + faces[i].height/2);
//        ellipse(input, center, Size(faces[i].width, faces[i].height), 0, 0, 360,
//                Scalar(255, 0, 255), 30, 8, 0);
        rectangle(input, faces[i], Scalar(255, 0, 0), 2);
    }
}
}
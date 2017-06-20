package com.example.opencv.demo.util;

import com.example.core.manage.Logger;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Hwang on 2017-04-25.
 * 작성자 : 황의택
 * 내용 : Mat 관련 유틸리티
 */
public class MatUtils {
    /**
     * Mat 클래스를 지정된 path의 파일로 출력
     * @param path Mat을 출력하고자 하는 경로(full path)
     * @param mat 출력하려는 Mat 클래스
     */
    public static void write(String path, Mat mat) {
        File file = new File(path).getAbsoluteFile();
        file.getParentFile().mkdirs();
        try {
            int cols = mat.cols();
            float[] data = new float[(int) mat.total() * mat.channels()];
            mat.get(0, 0, data);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(cols);
            oos.writeObject(data);
            oos.close();
        } catch (IOException | ClassCastException e) {
            Logger.printStackTrace(e);
        }
    }

    /**
     * 파일로 출력된 Mat 클래스를 읽어와서 반환
     * @param path Mat 클래스의 파일 경로
     * @return
     */
    public static Mat read(String path) {
        try {
            int cols;
            float[] data;

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            cols = (int) ois.readObject();
            data = (float[]) ois.readObject();

            Mat mat = new Mat(data.length / cols, cols, CvType.CV_32F);
            mat.put(0, 0, data);
            return mat;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            Logger.printStackTrace(e);
        }
        return null;
    }
}

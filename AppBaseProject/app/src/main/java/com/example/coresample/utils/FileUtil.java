package com.example.coresample.utils;

import com.example.core.SampleApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Hwang on 2016-12-08.
 * 작성자 : 황의택
 * 내용 : 파일을 다루기 위한 유틸리티 클래스
 */
public class FileUtil {
    public static File getImageTempDir() {
        File dir = new File(SampleApp.get("DIR_IMAGE") + "/temp");
        if (!dir.exists() ) {
            dir.mkdirs();
        }
        return dir;
    }
    public static File getProfileDir(String memberId) {
        File dir = new File(SampleApp.get("DIR_IMAGE") + "/user/" + memberId);
        if (!dir.exists() ) {
            dir.mkdirs();
        }
        return dir;
    }
    public static String getServerPofileDir(String memberId) {
        return SampleApp.get("FILE_SERVER_URL") + "user/" + memberId;
    }
    public static File getErrorLogDir() {
        String path = SampleApp.get("DIR_ERROR").toString();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    public Object getProfileImageUrl() {
        Object url;
        File file = new File(FileUtil.getProfileDir((String) SampleApp.get("_ID")) + "/profile0");
        if (file.exists()) {
            url = file;
        } else {
            url = SampleApp.get("FILE_SERVER_URL") + "user/" + SampleApp.get("_ID") + "/profile0";
        }
        return url;
    }
    public static String getExtension(File file) {
        return getExtension(file.getAbsolutePath());
    }
    public static String getExtension(String path) {
        if (path != null) {
            int lastIndexOf = path.lastIndexOf(".");
            if (lastIndexOf != -1) {
                return path.substring(lastIndexOf + 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public static void copy(String inFileName, String outFileName) throws Exception{
        copy(new File(inFileName), new File(outFileName));
    }
    public static void copy(File inFile, File outFile) throws Exception{
        FileInputStream fis = new FileInputStream(inFile);
        FileOutputStream fos = new FileOutputStream(outFile);

        int data;
        while((data=fis.read())!=-1) {
            fos.write(data);
        }
        fis.close();
        fos.close();
    }
}

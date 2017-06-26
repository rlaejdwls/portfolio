package com.example.core.util;

import com.example.core.AppCore;
import com.example.core.util.event.OnUploadCompleteListener;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Hwang on 2017-02-20.
 * 작성자 : 황의택
 * 내용 : 멀티파트 유틸 클래스를 이용해 파일을 업로드하는 업로더
 */
public class NFUploader {
    private int UPLOAD_TIMEOUT = 15000;

    private String url;
    private String path;

    public void upload(String detail, final OnUploadCompleteListener listener) {
        url = AppCore.get("WEB_URL") + detail;
        path = AppCore.get("DIR_ERROR").toString();

        new Thread() {
            public void run() {
                try {
                    MultipartUtil multipart = new MultipartUtil(url, "UTF-8", UPLOAD_TIMEOUT);

                    JSONObject description = new JSONObject();
                    description.put("member_id", (AppCore.get("_ID") == null ? "0" : AppCore.get("_ID")));
                    description.put("genre", "error_log");
                    description.put("path", "");

                    multipart.addFormField("description", description.toString());

                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File[] files = dir.listFiles();

                    for (final File file : files) {
                        try {
                            multipart.addFilePart(file.getName(), file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    multipart.finish(listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

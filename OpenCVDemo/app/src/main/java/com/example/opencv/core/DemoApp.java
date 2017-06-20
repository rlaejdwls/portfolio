package com.example.opencv.core;

import com.example.core.AppCore;
import com.example.core.manage.Debugger;

/**
 * Created by Hwang on 2017-04-20.
 * 작성자 : 황의택
 */
public class DemoApp extends AppCore {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    protected void notifyForeground() {
        super.notifyForeground();
        if (Debugger.DEBUG) {   //DEBUG 모드일 때
        } else {                //RELEASE 모드 일 때
        }
        this.put("DIR_LOG", DemoApp.getApplication().getApplicationContext().getFilesDir() + "/log/log");
        this.put("DIR_ERROR", DemoApp.getApplication().getFilesDir() + "/log/error");
        this.put("DIR_DATA", DemoApp.getApplication().getFilesDir() + "/data");
        this.put("DIR_IMAGE", DemoApp.getApplication().getFilesDir() + "/image");
    }
    @Override
    protected void notifyBackground() {
        super.notifyBackground();
    }
}

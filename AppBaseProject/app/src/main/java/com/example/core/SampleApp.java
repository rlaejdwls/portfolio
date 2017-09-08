package com.example.core;

import com.example.core.manage.Logger;

/**
 * Created by Hwang on 2017-01-04.
 */
public class SampleApp extends AppCore {
    @Override
    protected void notifyBackground() {
        super.notifyBackground();
        Logger.d("");
    }
    @Override
    protected void notifyForeground() {
        super.notifyForeground();
        Logger.d("");

        this.put("DIR_LOG", SampleApp.getApplication().getApplicationContext().getFilesDir() + "/log/log");
        this.put("DIR_ERROR", SampleApp.getApplication().getFilesDir() + "/log/error");
        this.put("DIR_DATA", SampleApp.getApplication().getFilesDir() + "/data");
        this.put("DIR_IMAGE", SampleApp.getApplication().getFilesDir() + "/image");
    }
}

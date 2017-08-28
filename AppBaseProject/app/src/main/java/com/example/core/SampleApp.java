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
    }
}

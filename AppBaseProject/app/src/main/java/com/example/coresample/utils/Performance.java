package com.example.coresample.utils;

import java.util.Date;

/**
 * Created by Hwang on 2017-08-29.
 */
public class Performance {
    private static Performance instance;
    private Date start;

    private Performance() {
    }
    public synchronized static Performance get() {
        if (instance == null) {
            instance = new Performance();
        }
        return instance;
    }
    public void start() {
        get().start = new Date();
    }
    public long check() {
        Date check = new Date();
        long result = check.getTime() - get().start.getTime();
        get().start = check;
        return result;
    }
}

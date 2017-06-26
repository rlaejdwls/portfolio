package com.example.demo.util;

/**
 * Created by Hwang on 2017-06-14.
 * 작성자 : 황의택
 * 내용 : 유틸 클래스
 */
public class NFUtils {
    public static String getFormatTime(long milliSec) {
        long duration = milliSec / 1000;
        long hours = duration / 3600;
        long minutes = (duration - hours * 3600) / 60;
        long seconds = duration - (hours * 3600 + minutes * 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

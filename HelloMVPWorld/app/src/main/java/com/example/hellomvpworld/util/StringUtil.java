package com.example.hellomvpworld.util;

/**
 * Created by Hwang on 2018-01-05.
 *
 * Description :
 */
public class StringUtil {
    public String[] toArray(String data, String regex) {
        return data.split(regex);
    }
}

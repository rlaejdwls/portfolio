package com.example.core.manage;

import com.example.core.AppCore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Hwang on 2016-10-15.
 * 작성자 : 황의택
 * 내용 : 로그인 세션을 유지하고 관리하는 클래스
 */
public class Session {
    public enum SNS {
        NONE, NORMAL, KAKAOTALK, NAVER, GOOGLE
    }

    public static final int FREE                    = 0x00;                 //미가입
    public static final int SIGNUP                  = 0x01;                 //가입
    public static final int APPROVAL                = 0x02;                 //승인
    public static final int DETAIL_INPUT            = 0x04;                 //부가정보 입력

    public static void open(Map result) {
        open(result, false);
    }
    public static void open(Map result, boolean isAutoLogin) {
        cookie(result, isAutoLogin);
    }
    public static void cookie(Map result, boolean isAutoLogin) {
        AppCore.saveAll(result);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDate = dateFormat.format(date);
        AppCore.getApplication().getConfig().set("SESSION_OPEN_DATE", currentDate)
                .commit();
    }
    public static void close() {
        AppCore.removeAll();

        AppCore.save("IS_AUTO_LOGIN", false)
                .commit();
    }
}

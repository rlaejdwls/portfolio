package com.example.core.manage;

import android.content.Context;
import android.util.Log;

import com.example.core.util.JSONSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hwang on 2016-10-15.
 * 작성자 : 황의택
 * 내용 : 로그를 관리하는 클래스
 */
public class Logger {
    private static Context context;

    public static void initialize(Context context) {
        if (Logger.context == null) {
            Logger.context = context;
        }
    }

    /*
     * error log
     */
    public static final void e(String message) {
        e("ERROR", message);
    }
    public static final void e(String message, Throwable tr) {
        e("ERROR", message, tr);
    }
    public static final void e(String tag, String message) {
        if (Debugger.DEBUG)Log.e(tag, buildMessage(message));
    }
    public static final void e(String tag, String message, Throwable tr) {
        if (Debugger.DEBUG)Log.e(tag, buildMessage(message), tr);
    }
    /*
     * warning log
     */
    public static final void w(String message) {
        w("WARN", message);
    }
    public static final void w(String tag, String message) {
        if (Debugger.DEBUG)Log.w(tag, buildMessage(message));
    }
    /*
     * Information log
     */
    public static final void i(String message) {
        i("INFO", message);
    }
    public static final void i(String message, Throwable tr) {
        i("INFO", message, tr);
    }
    public static final void i(String tag, String message) {
        if (Debugger.DEBUG)Log.i(tag, buildMessage(message));
    }
    public static final void i(String tag, String message, Throwable tr) {
        if (Debugger.DEBUG)Log.i(tag, buildMessage(message), tr);
    }
    /*
     * debug log
     */
    public static final void d(String message) {
        d("DEBUG", message);
    }
    public static final void d(String tag, String message) {
        if (Debugger.DEBUG)Log.d(tag, buildMessage(message));
    }
    /*
     * Verbose log
     */
    public static final void v(String tag, String message) {
        if (Debugger.DEBUG)Log.v(tag, buildMessage(message));
    }

    /**
     * 현재 StackTrace에서 Logger 클래스 정보를 제외하고 그 외 클래스 정보와 메세지를 결합하여 반환하는 함수
     * @param message 로그에 출력하려는 메세지
     * @return 클래스 정보와 사용자가 전달한 메세지
     */
    private static String buildMessage(String message) {
        String logger = Logger.class.getName();
        StringBuilder sb = new StringBuilder();

        //현재 시간
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDate = dateFormat.format(date);
        sb.append(currentDate+":");

        //스택 정보
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();

        for(int i = 4; i < stacks.length; i++) {
            StackTraceElement element = stacks[i];
            if (element.getClassName().length() < logger.length() ||
                    !logger.equals(element.getClassName().substring(0, logger.length()))) {
                sb.append(element.getClassName() + "[" + element.getMethodName() + "]" + "(" + element.getFileName() + ":" + element.getLineNumber() + "):");
                break;
            }
        }
        sb.append(message);

        return sb.toString();
    }

    /**
     * 오류 로그를 남기기 위한 함수
     * @param e
     */
    public static  void printStackTrace(Throwable e) {
        printStackTrace(null, e);
    }
    /**
     * 오류 로그를 남기기 위한 함수
     * @param comment
     * @param e
     */
    public static void printStackTrace(String comment, Throwable e) {
        Map<String, String> output = new HashMap<>();

        //현재 날짜와 시간
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        String currentDate = dateFormat.format(date);
        String currentTime = timeFormat.format(date);

        //log 폴더 생성
        if (context != null) {
            String path = context.getFilesDir() + "/log/error";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = "/error_" + currentDate + ".log";

            //데이터 json으로 변환
            output.put("date", currentDate + " " + currentTime);
            if (comment != null) output.put("comment", comment);
            StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            output.put("message", stackTrace.toString());

            //출력
            BufferedWriter bw = null;
            try {
                String data = JSONSerializer.toJSON(output) + "\n";
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + fileName, true), "UTF-8"));
                bw.write(data, 0, data.length());
                bw.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (bw != null) bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println(stackTrace.toString());
        }
    }
}

package com.example.core.manage;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.core.widget.CrashReportDialog;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Hwang on 2016-10-15.
 * 작성자 : 황의택
 * 내용 : Application 범위에서 Exception을 관리하고 핸들링하는 클래스
 * 참고 : ACRA 라이브러리로 대체 가능
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Context context;
    private final String LINE_SEPARATOR = "\n";

    public ExceptionHandler(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        try {
            StringWriter stackTrace = new StringWriter();
            ex.printStackTrace(new PrintWriter(stackTrace));
            StringBuilder errorContent = new StringBuilder();
            errorContent.append("************ CAUSE OF ERROR ************");
            errorContent.append(LINE_SEPARATOR);
            errorContent.append(stackTrace.toString());
            errorContent.append("************ CAUSE OF ERROR ************");
            errorContent.append(LINE_SEPARATOR);


            errorContent.append(LINE_SEPARATOR);
            errorContent.append("************ DEVICE INFORMATION ***********");
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("Brand: ");
            errorContent.append(Build.BRAND);
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("Device: ");
            errorContent.append(Build.DEVICE);
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("Model: ");
            errorContent.append(Build.MODEL);
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("Id: ");
            errorContent.append(Build.ID);
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("Product: ");
            errorContent.append(Build.PRODUCT);
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("************ DEVICE INFORMATION ***********");
            errorContent.append(LINE_SEPARATOR);

            errorContent.append(LINE_SEPARATOR);
            errorContent.append("************ FIRMWARE OF ANDROID ************");
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("SDK: ");
            errorContent.append(Build.VERSION.SDK_INT);
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("Release: ");
            errorContent.append(Build.VERSION.RELEASE);
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("Incremental: ");
            errorContent.append(Build.VERSION.INCREMENTAL);
            errorContent.append(LINE_SEPARATOR);
            errorContent.append("************ FIRMWARE OF ANDROID ************");
            errorContent.append(LINE_SEPARATOR);

            Logger.printStackTrace(errorContent.toString(), ex);
            notifyDialog(errorContent);
            System.exit(1);
        } catch (Exception e) {
            Logger.e("Exception Logger Failed!", e);
        }
    }

    private void notifyDialog(StringBuilder errorContent) {
        try {
            Intent dialogIntent = new Intent(context, CrashReportDialog.class);
            dialogIntent.putExtra("errorContent", errorContent.toString());
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dialogIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

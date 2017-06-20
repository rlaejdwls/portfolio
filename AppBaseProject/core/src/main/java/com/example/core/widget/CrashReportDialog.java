package com.example.core.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.core.AppCore;
import com.example.core.R;
import com.example.core.util.NFUploader;
import com.example.core.util.event.OnUploadCompleteListener;

import java.io.File;

/**
 * Created by Hwang on 2016-10-16.
 * 작성자 : 황의택
 * 내용 : 오류를 레포트하기 위한 클래스
 *          v0.1.0 : 현재 오류 내용을 표시하기만 하고 전송을 눌러도 아무 동작없이 종료됨
 *                   추후 메일, DB, 파일 등의 처리과정을 추가 할 예정/상속하여 사용 할 수 있도록 검토
 *          v1.0.0 : 오류가 발생되었을 때 Logger 클래스의 printStackTrace를 이용하여 로그를 파일로 남김
 *                   남겨진 로그는 Collector 클래스에 의해 일정 주기로 서버에 전송됨
 *                   예외처리 되지 못하여 앱중지 CrashReport가 떴을 경우에는 제출 할 경우
 *                   Collector가 수집하는 것을 기다리지 않고 바로 오류를 서버로 전송함
 */
public class CrashReportDialog extends Activity implements OnClickListener {
    private Button btnSubmitReport;
    private Button btnCancelReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_crash_report);

        String errorContent = "";
        if (getIntent().getStringExtra("errorContent") != null) {
            errorContent = getIntent().getStringExtra("errorContent");
        }

        ((TextView) findViewById(R.id.lblTitle)).setText(getResources().getString(R.string.core_error_dialog_title));
        ((TextView) findViewById(R.id.lblContent)).setText(getResources().getString(R.string.core_error_dialog_content));
        ((TextView) findViewById(R.id.lblErrorContent)).setText(errorContent);
        btnSubmitReport = (Button) findViewById(R.id.btnSubmitReport);
        btnCancelReport = (Button) findViewById(R.id.btnCancelReport);
        btnSubmitReport.setOnClickListener(this);
        btnCancelReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSubmitReport) {
            new NFUploader().upload("app/api/upload", new OnUploadCompleteListener() {
                @Override
                public void onUploadComplete() {
                    File dir = new File(AppCore.get("DIR_ERROR").toString());
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File[] files = dir.listFiles();
                    for (final File file : files) {
                        try {
                            file.delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.exit(1);
                }
            });
        } else if (i == R.id.btnCancelReport) {
            System.exit(1);
        }
    }

    public void sendEmailErrorReport(String bodyContent) {

        final Intent emailIntent = new Intent(
                Intent.ACTION_SEND);

        // open with email supported apps
        emailIntent.setType("message/rfc822");

        // support mail from debug_strings.xml
//        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
//                new String[] { getString(R.string.support_email) });

        // AppName + Subject text from debug_strings.xml
//        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//                getString(R.string.app_name)+getString(R.string.support_crash_subj));

        // Inital body text from debug_strings.xml + crash log
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
//                getString(R.string.support_crash_body) + bodyContent);

        finish();

//        startActivity(Intent.createChooser(emailIntent, "Send Report"));

        // restart app
        // restartApp();
    }

    public void restartApp() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        finish();
        startActivity(i);
    }
}
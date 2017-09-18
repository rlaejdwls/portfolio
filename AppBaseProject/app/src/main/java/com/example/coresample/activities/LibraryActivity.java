package com.example.coresample.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;

import java.util.regex.Pattern;

/**
 * Created by tigris on 2017-09-15.
 */
public class LibraryActivity extends AppCompatActivity {
    public static final String WEB_PATTERN = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_ㄱ-ㅎ가-힣|!:,.;]*[-a-zA-Z0-9+&@#/%=~_ㄱ-ㅎ가-힣|]" +
        "|www\\d{0,3}[.][-a-zA-Z0-9+&@#/%?=~_ㄱ-ㅎ가-힣|!:,.;]*";
    public static final Pattern MATCHER_WEBTAG = Pattern.compile(WEB_PATTERN);

    public static final String WEB_AUTH = "com.example.coresample.webbrowser";
    public static final String URI_WEB = "content://"+ WEB_AUTH +"/";
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    @Bind private TextView txtLink;

    private boolean isLongClick = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this);
        Linkify.addLinks(txtLink, MATCHER_WEBTAG, URI_WEB);

        txtLink.setOnLongClickListener((v) -> {
            Toast.makeText(this, "Long Click", Toast.LENGTH_SHORT).show();
            isLongClick = true;
            return false;
        });
        txtLink.setOnTouchListener((v, motion) -> {
            switch(motion.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (isLongClick) {
                        isLongClick = false;
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    isLongClick = false;
                    break;
            }
            return super.onTouchEvent(motion);
        });

        Uri uri = getIntent().getData();
        if (uri != null && uri.getHost() != null) {
            if (uri.getHost().equals(WEB_AUTH)) {
//                String query = BaseCommonUtil.validateString(uri.getQuery()) ? "?" + uri.getQuery() : "";
                goToLink(uri.getPath()/* + query*/);
                finish();
            }
        }
    }

    public void goToLink(String url) {
        if (url != null) {
            if (url.startsWith("/"))
                url = url.substring(1, url.length());

            if (url.startsWith(HTTP)
                    || url.startsWith(HTTPS)
                    || url.startsWith("ftp"))
                goToWeb(url);
            else
                goToWebWithProtocol(url);
        }
    }
    public void goToWeb(String destinationAddress) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(destinationAddress)));
    }
    public void goToWebWithProtocol(String destinationAddress) {
        goToWeb(HTTP + destinationAddress);
    }
}

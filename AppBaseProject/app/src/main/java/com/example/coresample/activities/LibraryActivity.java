package com.example.coresample.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.manage.Binder;
import com.example.core.manage.Logger;
import com.example.core.manage.annotation.Bind;
import com.example.coresample.R;
import com.example.coresample.activities.restful.converter.TestDeserializer;
import com.example.coresample.activities.restful.model.TestModel;
import com.example.coresample.activities.restful.service.TestJsonService;
import com.example.coresample.utils.Performance;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tigris on 2017-09-15.
 */
public class LibraryActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String WEB_PATTERN = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_ㄱ-ㅎ가-힣|!:,.;]*[-a-zA-Z0-9+&@#/%=~_ㄱ-ㅎ가-힣|]" +
        "|www\\d{0,3}[.][-a-zA-Z0-9+&@#/%?=~_ㄱ-ㅎ가-힣|!:,.;]*";
    public static final Pattern MATCHER_WEBTAG = Pattern.compile(WEB_PATTERN);

    public static final String WEB_AUTH = "com.example.coresample.webbrowser";
    public static final String URI_WEB = "content://"+ WEB_AUTH +"/";
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    @Bind private TextView txtLink;

    private Retrofit retrofit;

    private boolean isLongClick = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this).onClick(R.id.btn_retrofit_custom, R.id.btn_retrofit_original);
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

        //init retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(new Uri.Builder().scheme("http").authority("www.treegames.co.kr").build().toString())
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Uri uri = getIntent().getData();
        if (uri != null && uri.getHost() != null) {
            if (uri.getHost().equals(WEB_AUTH)) {
                goToLink(uri.getPath() + uri.getQuery());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retrofit_custom:
                Performance.get().start();
                TestJsonService service1 = retrofit.create(TestJsonService.class);
                service1.test1().enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            Logger.d("check point 1:" + Performance.get().check());
                            ResponseBody body = response.body();
                            try {
                                Type type = new TypeToken<List<TestModel>>() {}.getType();
                                String data = body.string();
                                List<TestModel> results = new GsonBuilder()
                                        .registerTypeAdapter(type, new TestDeserializer())
                                        .create()
                                        .fromJson(data, type);
                                Logger.d("check point 2:" + Performance.get().check());
                                Logger.d(results.toString());
                            } catch (IOException e) {
                                Logger.printStackTrace(e);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Logger.printStackTrace(t);
                    }
                });
                break;
            case R.id.btn_retrofit_original:
                Performance.get().start();
                TestJsonService service2 = retrofit.create(TestJsonService.class);
                service2.test2().enqueue(new Callback<List<TestModel>>() {
                    @Override
                    public void onResponse(Call<List<TestModel>> call, Response<List<TestModel>> response) {
                        if (response.code() == 200) {
                            Logger.d("check point 1:" + Performance.get().check());
                            List<TestModel> results = response.body();
                            Logger.d("check point 2:" + Performance.get().check());
                            Logger.d(results.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<TestModel>> call, Throwable t) {
                        Logger.printStackTrace(t);
                    }
                });
                break;
        }
    }
}

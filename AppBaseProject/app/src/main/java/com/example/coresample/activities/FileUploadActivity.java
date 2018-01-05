package com.example.coresample.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;

import com.example.core.manage.Binder;
import com.example.coresample.R;
import com.example.coresample.activities.restful.service.DefaultService;
import com.example.coresample.utils.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hwang on 2017-12-01.
 *
 * Description :
 */
public class FileUploadActivity extends AppCompatActivity {
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this, R.layout.activity_file_upload);

        retrofit = new Retrofit.Builder()
                .baseUrl(new Uri.Builder().scheme("http").authority("www.treegames.co.kr").build().toString())
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileUtil.getExtension("abc.mp4"));
        if (mimeType == null) { mimeType = "image/*"; }

        Map<String, RequestBody> map = new HashMap<>();
        map.put("part1", RequestBody.create(MediaType.parse(mimeType), new File("")));

        retrofit.create(DefaultService.UploadService.class).upload(
                RequestBody.create(MediaType.parse("text/plain; charset=UTF-8"), "data1"),
                map);
    }
}

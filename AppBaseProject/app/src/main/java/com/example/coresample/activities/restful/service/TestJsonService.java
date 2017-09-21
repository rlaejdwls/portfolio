package com.example.coresample.activities.restful.service;

import com.example.coresample.activities.restful.model.TestModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hwang on 2017-05-02.
 */
public interface TestJsonService {
    @GET("/TestJson.php")
    Call<ResponseBody> test1();
    @GET("/TestJson.php")
    Call<List<TestModel>> test2();
}

package com.example.coresample.activities.restful.service;

import com.example.coresample.activities.restful.model.TestModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Hwang on 2017-12-01.
 *
 * Description :
 */
public interface DefaultService {
    interface TestJsonService {
        @GET("/TestJson.php")
        Call<ResponseBody> test1();
        @GET("/TestJson.php")
        Call<List<TestModel>> test2();
    }
    interface UploadService {
        @Multipart
        @POST("/test/upload_file.php")
        Call<String> upload(
                @Part("data1") RequestBody data1,
                @PartMap Map<String, RequestBody> files
        );
    }
}

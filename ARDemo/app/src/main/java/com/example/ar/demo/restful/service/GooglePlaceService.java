package com.example.ar.demo.restful.service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hwang on 2017-05-02.
 */
public interface GooglePlaceService {
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670,151.1957&radius=500&types=food&name=cruise&key=AIzaSyCLubyNDoEKID0OBvurp4yYQ6Nwy6qWH74
    @GET("/public/project/demo/ar/location.php")
    Call<HashMap> list(
            @Query("location")  String location,
            @Query("radius")    String radius,
            @Query("types")     String types,
            @Query("key")       String key
    );
}

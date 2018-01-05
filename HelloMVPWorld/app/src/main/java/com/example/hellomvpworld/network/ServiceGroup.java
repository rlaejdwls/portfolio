package com.example.hellomvpworld.network;

import com.example.hellomvpworld.data.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Hwang on 2018-01-04.
 *
 * Description :
 */
public interface ServiceGroup {
    @GET("")
    Observable<List<User>> getUsers(@Query("age") int age);
}
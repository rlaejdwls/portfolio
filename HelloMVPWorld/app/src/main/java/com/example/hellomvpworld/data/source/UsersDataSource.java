package com.example.hellomvpworld.data.source;

import com.example.hellomvpworld.data.User;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Hwang on 2018-01-04.
 *
 * Description :
 */
public interface UsersDataSource {
    Flowable<List<User>> getUsers(int age);
    Flowable<Optional<User>> getUser(String id);
}

package com.example.hellomvpworld.data.source.remote;

import com.example.hellomvpworld.data.User;
import com.example.hellomvpworld.data.source.UsersDataSource;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Hwang on 2018-01-04.
 *
 * Description :
 */
public class UsersRemoteDataSource implements UsersDataSource {
    private static UsersRemoteDataSource instance;

    public UsersRemoteDataSource() {
    }

    public synchronized static UsersRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new UsersRemoteDataSource();
        }
        return instance;
    }

    @Override
    public Flowable<List<User>> getUsers(int age) {
        return null;
    }
    @Override
    public Flowable<Optional<User>> getUser(String id) {
        return null;
    }
}

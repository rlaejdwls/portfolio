package com.example.hellomvpworld.data.source;

import com.example.hellomvpworld.data.User;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Hwang on 2018-01-04.
 *
 * Description :
 */
public class UsersRepository implements UsersDataSource {
    private static UsersRepository instance = null;

    private final UsersDataSource remoteDataSource;

    public UsersRepository(UsersDataSource remoteDataSource) {
        this.remoteDataSource = checkNotNull(remoteDataSource);
    }

    public synchronized static UsersRepository getInstance(UsersDataSource remoteDataSource) {
        if (instance == null) {
            instance = new UsersRepository(remoteDataSource);
        }
        return instance;
    }
    public static void destroyInstance() {
        instance = null;
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

package com.example.hellomvpworld.main;

import android.content.Intent;

import com.example.hellomvpworld.data.source.UsersRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Hwang on 2018-01-04.
 *
 * Description :
 */
public class MainPresenter implements MainContract.Presenter {
    private final UsersRepository repository;
    private final MainContract.View view;

    private CompositeDisposable composite;

    public MainPresenter(UsersRepository repository, MainContract.View view) {
        this.repository = repository;
        this.view = view;
        this.view.setPresenter(this);
    }

    /*****************************************************************************
     * Life cycles
     *****************************************************************************/
    @Override
    public void subscribe() {

    }
    @Override
    public void unsubscribe() {
        composite.clear();
    }
    @Override
    public void result(int requestCode, int resultCode, Intent data) {

    }

    /*****************************************************************************
     * Business Logic
     *****************************************************************************/
    public void request() {
    }
    @Override
    public void setMessage(String message) {

    }
}

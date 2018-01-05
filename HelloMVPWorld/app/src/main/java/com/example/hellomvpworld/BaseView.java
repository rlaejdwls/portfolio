package com.example.hellomvpworld;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}

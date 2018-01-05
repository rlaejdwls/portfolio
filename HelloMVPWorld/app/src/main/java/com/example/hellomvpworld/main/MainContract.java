package com.example.hellomvpworld.main;

import android.content.Intent;

import com.example.hellomvpworld.BasePresenter;
import com.example.hellomvpworld.BaseView;

/**
 * Created by Hwang on 2018-01-04.
 *
 * Description :
 */
public interface MainContract {
    interface View extends BaseView<Presenter> {

    }
    interface Presenter extends BasePresenter {
        /*****************************************************************************
         * Life cycles
         *****************************************************************************/
        void result(int requestCode, int resultCode, Intent data);


        /*****************************************************************************
         * Business Logic
         *****************************************************************************/
        void setMessage(String message);
    }
}

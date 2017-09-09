package com.example.coresample.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;

import io.reactivex.Observable;

/**
 * Created by rlaej on 2017-08-31.
 */
public class RxJavaActivity extends AppCompatActivity {
    @Bind private TextView txtResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this);

        Observable.just(txtResult.getText())
                .map(str -> str + "Rx")
                .subscribe(txt -> txtResult.setText(txt));
    }
}

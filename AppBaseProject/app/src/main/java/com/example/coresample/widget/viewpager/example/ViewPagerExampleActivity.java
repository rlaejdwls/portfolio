package com.example.coresample.widget.viewpager.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;
import com.example.coresample.widget.viewpager.example.adapter.DefaultPagerAdapter;

/**
 * Created by tigris on 2017-10-18.
 */
public class ViewPagerExampleActivity extends AppCompatActivity {
    @Bind ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this);
        pager.setAdapter(new DefaultPagerAdapter<>(this));
    }
}

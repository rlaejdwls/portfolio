package com.example.coresample.activities.widget.listview.expandable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.core.manage.Binder;

/**
 * Created by tigris on 2017-09-26.
 */
public class ExpandableRecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this);
    }
}
package com.example.coresample.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.core.manage.Binder;
import com.example.coresample.R;
import com.example.coresample.widget.imageview.multitouchzoom.ImageViewActivity;
import com.example.coresample.widget.listview.expandable.ExpandableRecyclerViewActivity;
import com.example.coresample.widget.viewpager.example.ViewPagerExampleActivity;

/**
 * Created by tigris on 2017-09-26.
 */
public class WidgetListActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this).onClick(
                R.id.btn_expandable_recycler_view_activity,
                R.id.btn_image_view_activity,
                R.id.btn_view_pager_example_activity
        );
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_expandable_recycler_view_activity:
                startActivity(ExpandableRecyclerViewActivity.class);
                break;
            case R.id.btn_image_view_activity:
                startActivity(ImageViewActivity.class);
                break;
            case R.id.btn_view_pager_example_activity:
                startActivity(ViewPagerExampleActivity.class);
                break;
        }
    }
    public void startActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }
}

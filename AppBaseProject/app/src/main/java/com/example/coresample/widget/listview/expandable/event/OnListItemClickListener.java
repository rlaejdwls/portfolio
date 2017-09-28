package com.example.coresample.widget.listview.expandable.event;

import android.view.View;

/**
 * Created by tigris on 2017-08-10.
 */
public interface OnListItemClickListener<T> {
    void onListItemClick(View v, int position, T obj);
}

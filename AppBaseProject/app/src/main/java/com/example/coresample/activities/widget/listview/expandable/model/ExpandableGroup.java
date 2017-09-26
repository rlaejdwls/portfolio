package com.example.coresample.activities.widget.listview.expandable.model;

import android.os.Parcelable;

import java.util.List;

/**
 * Created by tigris on 2017-09-26.
 */
public class ExpandableGroup<P extends Parcelable> {
    private String title;
    private List<P> child;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<P> getChild() {
        return child;
    }
    public void setChild(List<P> child) {
        this.child = child;
    }
}

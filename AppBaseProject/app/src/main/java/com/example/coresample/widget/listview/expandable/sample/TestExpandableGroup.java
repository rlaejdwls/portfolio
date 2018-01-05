package com.example.coresample.widget.listview.expandable.sample;

import com.example.coresample.widget.listview.expandable.model.ExpandableGroup;

import java.util.List;

/**
 * Created by tigris on 2017-09-26.
 */
public class TestExpandableGroup implements ExpandableGroup {
    private int layoutId;
    private String title;
    private List<TestExpandableChild> child;

    @Override
    public int getLayoutId() {
        return layoutId;
    }
    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public List<TestExpandableChild> getChildren() {
        return child;
    }
    public void setChild(List<TestExpandableChild> child) {
        this.child = child;
    }
}

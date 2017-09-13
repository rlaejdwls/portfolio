package com.example.coresample.activities.model;

import com.google.gson.annotations.Expose;

/**
 * Created by rlaej on 2017-09-13.
 */
public class SelectItemModel {
    @Expose private String id;
    @Expose private String display;
    @Expose private int value;
    @Expose private boolean checked;

    public SelectItemModel(String id, String display, int value, boolean checked) {
        this.id = id;
        this.display = display;
        this.value = value;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "SelectItemModel{" +
                "id='" + id + '\'' +
                ", display='" + display + '\'' +
                ", value=" + value +
                ", checked=" + checked +
                '}';
    }
}

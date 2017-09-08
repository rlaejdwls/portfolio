package com.example.coresample.dialog.model;

import java.util.HashMap;

/**
 * Created by needid on 2016-11-08.
 * 작성자 : 황의택
 * 내용 : 기본 콤보박스의 데이터
 */
public class SpinnerData {
    private String id;
    private String displayString;
    private String dropdownString;
    private HashMap data;

    public SpinnerData() {
    }
    public SpinnerData(String id, String displayString) {
        this.id = id;
        this.displayString = displayString;
        this.dropdownString = displayString;
        this.data = null;
    }
    public SpinnerData(String id, String displayString, HashMap data) {
        this.id = id;
        this.displayString = displayString;
        this.dropdownString = displayString;
        this.data = data;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDisplayString() {
        return displayString;
    }
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
    public String getDropdownString() {
        return dropdownString;
    }
    public void setDropdownString(String dropdownString) {
        this.dropdownString = dropdownString;
    }
    public HashMap getData() {
        return data;
    }
    public void setData(HashMap data) {
        this.data = data;
    }
    public String getData(String key) {
        if (data == null) {
            data = new HashMap();
        }
        return (String) data.get(key);
    }
    public void putData(String key, String value) {
        if (data == null) {
            data = new HashMap();
        }
        this.data.put(key, value);
    }

    @Override
    public String toString() {
        return "SpinnerData{" +
                "data=" + data +
                ", id='" + id + '\'' +
                ", displayString='" + displayString + '\'' +
                ", dropdownString='" + dropdownString + '\'' +
                '}';
    }
}

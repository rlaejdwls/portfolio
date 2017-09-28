package com.example.coresample.widget.listview.expandable.sample;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.coresample.widget.listview.expandable.model.ExpandableChild;

/**
 * Created by tigris on 2017-09-26.
 */
public class TestExpandableChild implements ExpandableChild, Parcelable {
    private int layoutId;
    private String title;

    public TestExpandableChild() {}
    protected TestExpandableChild(Parcel in) {
        title = in.readString();
    }

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
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TestExpandableChild> CREATOR = new Parcelable.Creator<TestExpandableChild>() {
        @Override
        public TestExpandableChild createFromParcel(Parcel in) {
            return new TestExpandableChild(in);
        }
        @Override
        public TestExpandableChild[] newArray(int size) {
            return new TestExpandableChild[size];
        }
    };
}

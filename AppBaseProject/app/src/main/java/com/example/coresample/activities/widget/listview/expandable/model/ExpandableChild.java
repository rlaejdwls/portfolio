package com.example.coresample.activities.widget.listview.expandable.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tigris on 2017-09-26.
 */
public class ExpandableChild implements Parcelable {
    private String title;

    public ExpandableChild() {}
    protected ExpandableChild(Parcel in) {
        title = in.readString();
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
    public static final Parcelable.Creator<ExpandableChild> CREATOR = new Parcelable.Creator<ExpandableChild>() {
        @Override
        public ExpandableChild createFromParcel(Parcel in) {
            return new ExpandableChild(in);
        }
        @Override
        public ExpandableChild[] newArray(int size) {
            return new ExpandableChild[size];
        }
    };
}

package com.example.demo.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Hwang on 2016-11-28
 * 작성자 : 황의택
 * 내용 : 16:9 wide layout
 */
public class Wide16_9Layout extends RelativeLayout {
    public Wide16_9Layout(Context context) {
        super(context);
    }

    public Wide16_9Layout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Wide16_9Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Wide16_9Layout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthPixels = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) Math.floor((9 * widthPixels) / 16);
        height = MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(widthMeasureSpec));
        super.onMeasure(widthMeasureSpec, height);
    }
}
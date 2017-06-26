package com.example.demo.widget.timestamp.model;

import android.graphics.Canvas;

/**
 * Created by Hwang on 2017-06-15.
 * 작성자 : 황의택
 * 내용 : View에 Shape를 그리기 위한 인터페이스
 */
public interface Shape {
    boolean isInShape(float x, float y);
    void onDraw(Canvas canvas);
}

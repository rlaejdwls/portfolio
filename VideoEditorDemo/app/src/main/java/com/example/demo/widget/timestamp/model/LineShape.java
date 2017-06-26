package com.example.demo.widget.timestamp.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Hwang on 2017-06-15.
 */
public class LineShape implements Shape {
    private Paint paint = new Paint();

    private float startX;
    private float startY;
    private float stopX;
    private float stopY;

    public LineShape(float startX, float startY, float stopX, float stopY, float width) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(width);
    }
    public LineShape(Paint paint, float startX, float startY, float stopX, float stopY) {
        this.paint = paint;
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }

    public void moveMarking(float x) {
        this.startX = x;
        this.stopX = x;
    }

    @Override
    public boolean isInShape(float x, float y) {
        return false;
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
}

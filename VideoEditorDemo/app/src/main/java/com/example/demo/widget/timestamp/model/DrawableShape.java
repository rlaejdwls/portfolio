package com.example.demo.widget.timestamp.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by Hwang on 2017-06-15.
 * 작성자 : 황의택
 * 내용 : Drawable을 그리기 위한 Shape 클래스
 */
public class DrawableShape implements Shape {
    private Context context;
    private Drawable drawable;

    private int id;
    private int bufferX;
    private int bufferY;
    private int x;
    private int y;
    private float correctX;
    private float correctY;
    private int width;
    private int height;

    private boolean isFocus;

    public DrawableShape(int id, Context context, Drawable drawable, int x, int y, int width, int height) {
        this.id = id;
        this.bufferX = 0;
        this.bufferY = 0;
        this.context = context;
        this.drawable = drawable;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isFocus = false;

        this.drawable.setBounds(x, y, x + width, y + height);
    }

    public Context getContext() {
        return context;
    }
    public Drawable getDrawable() {
        return drawable;
    }
    public int getId() {
        return id;
    }
    public void setBuffer(int bufferX, int bufferY) {
        this.bufferX = bufferX;
        this.bufferY = bufferY;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public float getCorrectX() {
        return correctX;
    }
    public float getCorrectY() {
        return correctY;
    }
    public void setLeft(int x) {
        this.x = x;
        this.drawable.setBounds(x, this.y, this.drawable.getBounds().right, this.y + this.height);
    }
    public void setRight(int right) {
        this.width = right - this.x;
        this.drawable.setBounds(this.x, this.y, right, this.y + this.height);
    }
    public void setBounds(int x, int max) {
        if ((x - correctX) < 0) {
            x = 0;
        } else if ((x - correctX) > (max - width)) {
            x = max - width;
        } else {
            x = (int) (x - correctX);
        }

        this.x = x;
        this.setBounds(x, this.y, this.width, this.height);
    }
    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.drawable.setBounds(x, y, x + width, y + height);
    }
    public boolean isFocus() {
        return isFocus;
    }
    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    @Override
    public boolean isInShape(float x, float y) {
        if ((this.x - this.bufferX) <= x && (this.x + this.width + this.bufferX) >= x
                && (this.y - this.bufferY) <= y && (this.y + this.height + this.bufferY) >= y) {
            correctX = x - (this.x + this.bufferX);
            correctY = y - (this.y + this.bufferY);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void onDraw(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }
}

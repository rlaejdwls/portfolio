package com.example.demo.widget.timestamp;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.demo.R;
import com.example.demo.widget.timestamp.model.DrawableShape;
import com.example.demo.widget.timestamp.model.LineShape;
import com.example.demo.widget.timestamp.model.Shape;
import com.example.core.manage.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hwang on 2017-06-15.
 * 작성자 : 황의택
 * 내용 : 동영상 편집시 사용되는 Timestamp View
 */
public class Timestamp extends View {
    private final int LEFT_STAMP = 1;
    private final int RECT = 2;
    private final int RIGHT_STAMP = 3;

    private OnOffsetChangedListener onOffsetChangedListener;
    private OnOffsetChangeListener onOffsetChangeListener;
    private OnLeftStampChangeListener onLeftStampChangeListener;
    private OnRightStampChangeListener onRightStampChangeListener;

    private List<DrawableShape> shapes = new ArrayList<>();
    private List<Bitmap> scenes = new ArrayList<>();
    private List<Integer> sceneWidths = new ArrayList<>();
    private LineShape line;

    private float density;
    private int width;
    private boolean isLineMove = false;

    public Timestamp(Context context) {
        super(context);
        init();
    }
    public Timestamp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public Timestamp(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Timestamp(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public void init() {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = getWidth();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DrawableShape leftStamp = new DrawableShape(LEFT_STAMP, getContext(), getResources().getDrawable(R.drawable.shape_timestamp_left_stamp, null),
                            0, 0, dpToPx(13), dpToPx(48));
                    leftStamp.setBuffer(dpToPx(4), dpToPx(2));
                    DrawableShape rightStamp = new DrawableShape(RIGHT_STAMP, getContext(), getResources().getDrawable(R.drawable.shape_timestamp_right_stamp, null),
                            width - dpToPx(13), 0, dpToPx(13), dpToPx(48));
                    rightStamp.setBuffer(dpToPx(4), dpToPx(2));
                    shapes.add(leftStamp);
                    shapes.add(rightStamp);
                    shapes.add(new DrawableShape(RECT, getContext(), getResources().getDrawable(R.drawable.shape_timestamp_rect, null),
                            dpToPx(13), 0, width - (dpToPx(13) * 2), dpToPx(48)));
                } else {
                    DrawableShape leftStamp = new DrawableShape(LEFT_STAMP, getContext(), getResources().getDrawable(R.drawable.shape_timestamp_left_stamp),
                            0, 0, dpToPx(13), dpToPx(48));
                    leftStamp.setBuffer(dpToPx(4), dpToPx(2));
                    DrawableShape rightStamp = new DrawableShape(RIGHT_STAMP, getContext(), getResources().getDrawable(R.drawable.shape_timestamp_right_stamp),
                            width - dpToPx(13), 0, dpToPx(13), dpToPx(48));
                    rightStamp.setBuffer(dpToPx(4), dpToPx(2));
                    shapes.add(leftStamp);
                    shapes.add(rightStamp);
                    shapes.add(new DrawableShape(RECT, getContext(), getResources().getDrawable(R.drawable.shape_timestamp_rect),
                            dpToPx(13), 0, width - (dpToPx(13) * 2), dpToPx(48)));
                }
                line = new LineShape(dpToPx(13), 0, dpToPx(13), dpToPx(48), dpToPx(2));

                int shareCnt = (int)((width - (13 * density * 2)) / (48 * density));
                int restCnt = (int)((width - (13 * density * 2)) % (48 * density));
                int surplus = restCnt % shareCnt;
                for (int i = 0; i < shareCnt; i++) {
                    int size = dpToPx(48);
                    if (restCnt > shareCnt) {
                        size += restCnt / shareCnt;
                    }
                    if (i < surplus) {
                        size++;
                    }
                    sceneWidths.add(size);
                }

                Logger.d("Share:" + (width - (13 * density * 2)) / (48 * density));
                Logger.d("Rest:" + (width - (13 * density * 2)) % (48 * density));
            }
        });
        density = getResources().getDisplayMetrics().density;
    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged(double offset);
    }
    public interface OnOffsetChangeListener {
        void onOffsetChange(double offset);
    }
    public interface OnLeftStampChangeListener {
        void onLeftStampChange(double offset);
    }
    public interface OnRightStampChangeListener {
        void onRightStampChange(double offset);
    }

    public void setOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        this.onOffsetChangedListener = onOffsetChangedListener;
    }
    public void setOnOffsetChangeListener(OnOffsetChangeListener onOffsetChangeListener) {
        this.onOffsetChangeListener = onOffsetChangeListener;
    }
    public void setOnLeftStampChangeListener(OnLeftStampChangeListener onLeftStampChangeListener) {
        this.onLeftStampChangeListener = onLeftStampChangeListener;
    }
    public void setOnRightStampChangeListener(OnRightStampChangeListener onRightStampChangeListener) {
        this.onRightStampChangeListener = onRightStampChangeListener;
    }

    public void setScenes(List<Bitmap> scenes) {
        this.scenes = scenes;
    }
    public List<Bitmap> getScenes() {
        return scenes;
    }
    public List<Integer> getSceneWidths() {
        return sceneWidths;
    }
    public void setSceneWidths(List<Integer> sceneWidths) {
        this.sceneWidths = sceneWidths;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        double offsetX;
        if (event.getX() < dpToPx(13)) {
            offsetX = dpToPx(13);
        } else if (event.getX() > (width - dpToPx(13))) {
            offsetX = (width - dpToPx(13));
        } else {
            offsetX = event.getX();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Logger.d("ACTION_DOWN:X:" + x + ":Offset:" + (x / width));
                for (DrawableShape shape : shapes) {
                    if (shape.isInShape(event.getX(), event.getY())) {
                        shape.setFocus(true);
                        break;
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
//                Logger.d("ACTION_MOVE:X:" + x + ":Offset:" + (x / width));
                if (onOffsetChangeListener != null) {
                    onOffsetChangeListener.onOffsetChange(((offsetX - dpToPx(13)) / (width - dpToPx(13) * 2)));
                }

                for (DrawableShape shape : shapes) {
                    if (shape.isFocus()) {
                        switch (shape.getId()) {
                            case LEFT_STAMP:
                                shape.setBounds((int) event.getX(), width);
                                break;
                            case RIGHT_STAMP:
                                shape.setBounds((int) event.getX(), width);
                                break;
                        }
                    }
                    switch (shape.getId()) {
                        case RECT:
                            for (DrawableShape check : shapes) {
                                switch (check.getId()) {
                                    case LEFT_STAMP:
                                        if (check.isFocus()) {
                                            shape.setLeft(check.getDrawable().getBounds().right);
                                            Logger.d("LEFT_STAMP:Right:" + check.getDrawable().getBounds().right + ":Offset:" + ((check.getDrawable().getBounds().right - dpToPx(13)) / ((double) width - dpToPx(13) * 2)));
                                            if (onLeftStampChangeListener != null) {
                                                onLeftStampChangeListener.onLeftStampChange((check.getDrawable().getBounds().right - dpToPx(13)) / ((double) width - dpToPx(13) * 2));
                                            }
                                        }
                                        break;
                                    case RIGHT_STAMP:
                                        if (check.isFocus()) {
                                            shape.setRight(check.getDrawable().getBounds().left);
                                            Logger.d("RIGHT_STAMP:Left:" + check.getDrawable().getBounds().left + ":Offset:" + ((check.getDrawable().getBounds().left - dpToPx(13)) / ((double) width - dpToPx(13) * 2)));
                                            if (onRightStampChangeListener != null) {
                                                onRightStampChangeListener.onRightStampChange((check.getDrawable().getBounds().left - dpToPx(13)) / ((double) width - dpToPx(13) * 2));
                                            }
                                        }
                                        break;
                                }
                            }
                            break;
                    }
                }

                if (isLineMove) {
                    line.moveMarking((float) offsetX);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Logger.d("ACTION_UP:X:" + offsetX + ":Offset:" + ((offsetX - dpToPx(13)) / (width - dpToPx(13) * 2)));
                if (onOffsetChangedListener != null) {
                    onOffsetChangedListener.onOffsetChanged(((offsetX - dpToPx(13)) / (width - dpToPx(13) * 2)));
                }

                for (DrawableShape shape : shapes) {
                    shape.setFocus(false);
                }

                if (isLineMove) isLineMove = false;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sum = dpToPx(13);
        for (int i = 0; i < scenes.size(); i++) {
            canvas.drawBitmap(scenes.get(i), new Rect(0, 0, scenes.get(i).getWidth(), scenes.get(i).getHeight()),
                    new Rect(sum, 0, sum + sceneWidths.get(i), dpToPx(48)), null);
            sum += sceneWidths.get(i);
        }
        for (Shape shape : shapes) {
            shape.onDraw(canvas);
        }
    }

    public int dpToPx(int dp) {
        return (int) (dp * density + .5f);
    }
}

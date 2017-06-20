package com.example.ar.demo.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.core.AppCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Hwang on 2017-05-02.
 * 작성자 : 황의택
 * 내용 : 화면에 정보성 데이터를 표시하기 위한 모델 클래스
 */
public class InformationObjet {
    private Map<String, String> infos = new HashMap<>();

    private Paint textPaint = new Paint();

    private float density;
    private float fontSize;
    private float margin;

    public InformationObjet() {
        density = AppCore.getApplication().getApplicationContext().getResources().getDisplayMetrics().density;

        margin = 5 * density;
        fontSize = 12 * density;

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(fontSize);
        textPaint.setColor(Color.RED);
    }

    public void put(String key, String value) {
        infos.put(key, value);
    }

    public void onDraw(Canvas canvas) {
        Iterator<?> iterator = infos.keySet().iterator();
        int i = 1;
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            canvas.drawText(infos.get(key), canvas.getWidth() / 2, (margin * i) + (fontSize * i), textPaint);
            i++;
        }
    }
}

package com.example.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.event.OnSingleClickListener;
import com.example.core.manage.Logger;
import com.example.demo.service.VideoEditService;
import com.example.demo.util.NFUtils;
import com.example.demo.widget.timestamp.Timestamp;

import java.util.ArrayList;

/**
 * Created by Hwang on 2017-06-15.
 * 작성자 : 황의택
 * 내용 : 비디오를 편집하는 화면 Activity
 */
public class VideoEditActivity extends AppCompatActivity {
    private int thumbSize = 5;

    private MediaMetadataRetriever retriever;

    private ImageView imgThumbnail;
    private TextView lblStartTime;
    private TextView lblStopTime;
    private Timestamp timestamp;

    private double leftOffset = 0;
    private double rightOffset = 0;

    private double startTime = 0;
    private double stopTime = 0;
    private long totalTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edit);

        imgThumbnail = ((ImageView) this.findViewById(R.id.imgThumbnail));
        lblStartTime = ((TextView) this.findViewById(R.id.lblStartTime));
        lblStopTime = ((TextView) this.findViewById(R.id.lblStopTime));
        timestamp = ((Timestamp) this.findViewById(R.id.timestamp));

        timestamp.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                timestamp.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //장면 썸네일 표시
                thumbSize = timestamp.getSceneWidths().size() - 1;
                ArrayList<Bitmap> film = new ArrayList<>();
                long interval = totalTime / thumbSize;
                for (int i = 0; i <= thumbSize; i++) {
                    film.add(retriever.getFrameAtTime(interval * 1000 * i,
                            MediaMetadataRetriever.OPTION_CLOSEST_SYNC));
                }
                timestamp.setScenes(film);
            }
        });

        final String filepath = getIntent().getStringExtra("srcPath");

        retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filepath);

        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        totalTime = Long.parseLong(time);
        startTime = 0;
        stopTime = totalTime;

        lblStartTime.setText(NFUtils.getFormatTime((long) startTime));
        lblStopTime.setText(NFUtils.getFormatTime((long) stopTime));

        //이벤트
        timestamp.setOnOffsetChangeListener(new Timestamp.OnOffsetChangeListener() {
            @Override
            public void onOffsetChange(double offset) {
//                display(offset);
            }
        });
        timestamp.setOnLeftStampChangeListener(new Timestamp.OnLeftStampChangeListener() {
            @Override
            public void onLeftStampChange(double offset) {
                if (leftOffset == offset) return;

                startTime = totalTime * offset;
                display(offset);
                leftOffset = offset;

                lblStartTime.setText(NFUtils.getFormatTime((long) startTime));
                Logger.d("StartTime:" + startTime + ":" + NFUtils.getFormatTime((long) startTime));
            }
        });
        timestamp.setOnRightStampChangeListener(new Timestamp.OnRightStampChangeListener() {
            @Override
            public void onRightStampChange(double offset) {
                if (rightOffset == offset) return;

                stopTime = totalTime * offset;
                display(offset);
                leftOffset = offset;

                lblStopTime.setText(NFUtils.getFormatTime((long) stopTime));
                Logger.d("StopTime:" + stopTime + ":" + NFUtils.getFormatTime((long) stopTime));
            }
        });
        this.findViewById(R.id.imgBack).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        this.findViewById(R.id.imgConfirm).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideoEditService.class);
                intent.putExtra("srcPath", filepath);
                intent.putExtra("targetPath", Environment.getExternalStorageDirectory() + "/NFData/video/output.mp4");
                intent.putExtra("startTime", startTime);
                intent.putExtra("stopTime", stopTime);
                startService(intent);
            }
        });

        display(0.0);
    }

    public void display(double offset) {
        imgThumbnail.setImageBitmap(retriever.getFrameAtTime((long)(totalTime * 1000 * offset),
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC));
    }
}

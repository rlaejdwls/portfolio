package com.example.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.demo.runner.VideoDecoderThread;

import java.io.File;

/**
 * Created by Hwang on 2017-06-13.
 */
public class VideoDecoderActivity extends Activity implements SurfaceHolder.Callback {
    private VideoDecoderThread mVideoDecoder;

    private String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NFData";
    private String FILE_PATH = root + "/video/output.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new File(FILE_PATH).exists()) {
            SurfaceView surfaceView = new SurfaceView(this);
            surfaceView.getHolder().addCallback(this);
            setContentView(surfaceView);

            mVideoDecoder = new VideoDecoderThread();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
        if (mVideoDecoder != null) {
            if (mVideoDecoder.init(holder.getSurface(), FILE_PATH)) {
                mVideoDecoder.start();
            } else {
                mVideoDecoder = null;
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mVideoDecoder != null) {
            mVideoDecoder.close();
        }
    }
}
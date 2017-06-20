package com.example.opencv.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.opencv.demo.detect.FaceDetectCameraActivity;
import com.example.opencv.demo.effect.EffectCameraActivity;
import com.example.opencv.demo.event.DemoPermisstionListener;
import com.example.opencv.demo.example.PlaygroundCameraActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

/**
 * Created by Hwang on 2017-04-20.
 * 작성자 : 황의택
 * 내용 : 영상 분석을 위한 데모 앱
 */
public class MainActivity extends AppCompatActivity {
    private PermissionListener permissionlistener = new DemoPermisstionListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //이미지 접근
//        String filename1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Temp/test_cup_1.jpg";
//        String filename2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Temp/test_cup_2.jpg";
//
//        Logger.d("Image::" + filename2 + "::" + new File(filename2).exists());

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

        this.findViewById(R.id.btnOnFaceDetectCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FaceDetectCameraActivity.class));
            }
        });
        this.findViewById(R.id.btnOnEffectCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EffectCameraActivity.class));
            }
        });
        this.findViewById(R.id.btnOnPlayground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlaygroundCameraActivity.class));
            }
        });
        this.findViewById(R.id.btnCameraTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AndroidCameraApi.class));
            }
        });
    }
}

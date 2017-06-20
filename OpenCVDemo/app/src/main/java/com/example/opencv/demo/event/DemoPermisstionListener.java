package com.example.opencv.demo.event;

import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;

/**
 * Created by Hwang on 2017-04-21.
 */
public class DemoPermisstionListener implements PermissionListener {
    @Override
    public void onPermissionGranted() {}
    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {}
}

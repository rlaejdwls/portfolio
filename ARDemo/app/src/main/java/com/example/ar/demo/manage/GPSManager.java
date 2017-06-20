package com.example.ar.demo.manage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.ar.demo.event.OnReceiveDataListener;
import com.example.core.manage.Logger;

/**
 * Created by Hwang on 2017-05-03.
 * 작성자 : 황의택
 * 내용 : GPS를 통해 현재 위치정보를 저장하고 관리하는 매니저 클래스
 */
public class GPSManager implements LocationListener {
    private Context context = null;
    private Location location = null;
    private LocationManager locationManager = null;

    private OnReceiveDataListener onReceiveDataListener;

    private static GPSManager manager;

    private boolean isFirst = true;

    private GPSManager() {}

    public synchronized static GPSManager getInstance() {
        if (manager == null) {
            manager = new GPSManager();
        }
        return manager;
    }

    public synchronized static GPSManager with(Context context) {
        getInstance().setContext(context);
        getInstance().init();
        return getInstance();
    }

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public Location getLocation() {
        return location;
    }
    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    public void init() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String best = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(best, 50, 0, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationManager.registerGnssStatusCallback(new GnssStatus.Callback() {
                @Override
                public void onSatelliteStatusChanged(GnssStatus status) {
                    super.onSatelliteStatusChanged(status);
                }
            });
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (isFirst) {
            isFirst = false;
            if (onReceiveDataListener != null) {
                this.onReceiveDataListener.onReceiveData(location);
            }
        }
        this.location = location;
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Logger.d("GPS::onStatusChanged");
    }
    @Override
    public void onProviderEnabled(String provider) {
        Logger.d("GPS::onProviderEnabled");
    }
    @Override
    public void onProviderDisabled(String provider) {
        Logger.d("GPS::onProviderDisabled");
    }
}

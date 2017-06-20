package com.example.ar.demo.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.ar.demo.filter.KalmanFilter;
import com.example.ar.demo.model.Objet;
import com.example.core.manage.Logger;
import com.example.core.runner.Runner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hwang on 2017-04-26.
 */
public class _BackupOverlayKalmanFilterView extends View implements SensorEventListener, LocationListener {
    private final int FRAME = 60;
    private final int FONT_SIZE = 35;
    private final int MARGIN = 10;

    private List<Objet> objets = new ArrayList<>();

    private OverlayRunner runner = null;
    private Location location = null;
    private LocationManager locationManager = null;

    private Paint target = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint content = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);

    private String accelData = "Accelerometer Data";
    private String compassData = "Compass Data";
    private String gyroData = "Gyro Data";

    float verticalFOV = 0.0f;
    float horizontalFOV = 0.0f;
    private KalmanFilter[] accelerometerFilters = new KalmanFilter[2];
    private KalmanFilter[] compassFilters = new KalmanFilter[2];
    private KalmanFilter[] orientationFilters = new KalmanFilter[3];
    private float[] lastAccelerometer = null;
    private float[] lastCompass = null;
    private float[] prevCompass = null;
    private float orientation[] = new float[3];

    private KalmanFilter dyf = new KalmanFilter(0.0f);
    private KalmanFilter dxf = new KalmanFilter(0.0f);
    private float curBearingToMW;
    private String distance = "";

    private boolean isPositiveAngle = false;

    private final static Location mountWashington = new Location("방배경찰서");
//    static {
//        mountWashington.setLatitude(37.478175d);
//        mountWashington.setLongitude(126.984553d);
//        mountWashington.setAltitude(24.07789039611816d);
//    }

    static {
        mountWashington.setLatitude(37.4813957d);
        mountWashington.setLongitude(126.9830805d);
        mountWashington.setAltitude(21.36230087280273d);
    }

    public _BackupOverlayKalmanFilterView(Context context) {
        super(context);
        init(context);
    }

    public _BackupOverlayKalmanFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public _BackupOverlayKalmanFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        //init
//        objets.add(new LocationObjet("방배경찰서"));

        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(FONT_SIZE);
        text.setColor(Color.GREEN);
        content.setTextAlign(Paint.Align.CENTER);
        content.setTextSize(FONT_SIZE);
        content.setColor(Color.RED);
        target.setColor(Color.GREEN);

        accelerometerFilters[0] = new KalmanFilter(0.0f);
        accelerometerFilters[1] = new KalmanFilter(0.0f);
        compassFilters[0] = new KalmanFilter(0.0f);
        compassFilters[1] = new KalmanFilter(0.0f);
        orientationFilters[0] = new KalmanFilter(0.0f);
        orientationFilters[1] = new KalmanFilter(0.0f);
        orientationFilters[2] = new KalmanFilter(0.0f);

        Camera.Parameters params = Camera.open().getParameters();
        verticalFOV = params.getVerticalViewAngle();
        horizontalFOV = params.getHorizontalViewAngle();

        if (runner == null) {
            runner = new OverlayRunner();
            runner.startRunning();
        }

        SensorManager sensors = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensors.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensors.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensors.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);

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

    public void onDestroy() {
        if (this.runner != null) {
            this.runner.stopRunning();
        }
    }

    private StringBuilder info = new StringBuilder();

    /*
    //!issue
    칼만필터의 적용으로 x좌표가 카메라의 가로 크기를
    넘어 갈 경우 0으로 바뀌면서 중간을 가로지르며 사라짐
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(accelData, canvas.getWidth() / 2, MARGIN + FONT_SIZE, content);
        canvas.drawText(compassData, canvas.getWidth() / 2, MARGIN * 2 + FONT_SIZE * 2, content);
        canvas.drawText(gyroData, canvas.getWidth() / 2, MARGIN * 3 + FONT_SIZE * 3, content);

        if (orientation == null) return;

        info.delete(0, info.length());
        info.append("Info:");
        for (float item : orientation) {
            info.append("[").append(item).append("]");
        }
        canvas.drawText(info.toString(), canvas.getWidth() / 2, MARGIN * 4 + FONT_SIZE * 4, content);
        boolean isVisible = false;

        synchronized (orientation) {
            // use roll for screen rotation
            canvas.rotate((float) (0.0f - Math.toDegrees(orientation[2])), canvas.getWidth() / 2, 0.0f);
            // Translate, but normalize for the FOV of the camera -- basically, pixels per degree, times degrees == pixels
            float dx;
        /*
        width(가로 길이) / FOV(시야각) = 1도 당 주어진 가로 길이
        */
//        if (orientation[0] < 0) {
//            dx = (float) ((canvas.getWidth() / horizontalFOV/*시야각*/) * ((Math.toDegrees(orientation[0]) - 180)/* - curBearingToMW*/));
//        } else {
//            dx = (float) ((canvas.getWidth() / horizontalFOV/*시야각*/) * ((Math.toDegrees(orientation[0]) - 180)/* - curBearingToMW*/));
//        }
            float degree = (float) Math.toDegrees(orientation[0]);
            float bearing = curBearingToMW;
            float right = bearing + 120;//(horizontalFOV / 2);
            float left = bearing - 120;//(horizontalFOV / 2);

            //visible 영역
            if (right > 180) {
                if (left < degree || degree < (-180 + (right - 180))) {
                    isVisible = true;
                }
            } else if (left < -180) {
                if ((180 - -(left + 180)) < degree || degree < right) {
                    isVisible = true;
                }
            } else {
                if (left < degree && degree < right) {
                    isVisible = true;
                }
            }

            boolean isLeft = true;
            float angle;

            //방향
            if (bearing >= 0) {
                if (bearing < degree || ((-180 < degree) && (degree < bearing - 180))) {    //right
                    if (degree > 0) {
                        angle = degree - bearing;
                    } else {
                        angle = (180 - bearing) + (180 + degree);
//                        angle += 180 + degree;
                    }
                    isLeft = false;
                } else {            //left
                    if (degree > 0) {
                        angle = -(bearing - degree);
                    } else {
                        angle = -(bearing - degree);
                    }
                }
            } else {
                if ((bearing + 180 < degree && degree < 180) || degree < bearing) {     //left
                    if (degree < 0) {
                        angle = -(bearing - degree);
                    } else {
                        angle = -((180 + bearing) + (180 - degree));
                    }
                } else {            //right
                    if (degree < 0) {
                        angle = degree - bearing;
                    } else {
                        angle = degree - bearing;
                    }
                    isLeft = false;
                }
            }



            dx = (float) (canvas.getWidth() / horizontalFOV/*시야각*/) * angle;
            float dy = (float) ((canvas.getHeight() / verticalFOV) * Math.toDegrees(orientation[1]));

            dy = (float) dyf.update(dy);
            dx = (float) dxf.update(dx);
            canvas.drawText("degree:" + degree + ", " + "angle:" + (isLeft ? "LEFT" : "RIGHT") + ":" + angle, canvas.getWidth() / 2, MARGIN * 5 + FONT_SIZE * 5, content);
            canvas.drawText("bearing:" + curBearingToMW, canvas.getWidth() / 2, MARGIN * 6 + FONT_SIZE * 6, content);

            // wait to translate the dx so the horizon doesn't get pushed off
//        canvas.translate(0.0f, 0.0f - dy);

            // make our line big enough to draw regardless of rotation and translation
//        canvas.drawLine(0f - canvas.getHeight(), canvas.getHeight()/2, canvas.getWidth()+canvas.getHeight(), canvas.getHeight()/2, target);

            // now translate the dx
//        canvas.translate(0.0f - dx, 0.0f);

//        if (isPositiveAngle) {
//////            canvas.translate(0.0f - dx, 0.0f);
//        } else {
//////            canvas.translate(0.0f - (-dx), 0.0f);

            //canvas line
            canvas.drawLine(0f, 0f, canvas.getWidth(), 0f, content);
            canvas.drawLine(0f, canvas.getHeight(), canvas.getWidth(), canvas.getHeight(), content);
            canvas.drawLine(0f, 0f, 0f, canvas.getHeight(), content);
            canvas.drawLine(canvas.getWidth(), 0f, canvas.getWidth(), canvas.getHeight(), content);

            if (isVisible) {
                canvas.drawText(mountWashington.getProvider(), (canvas.getWidth() / 2) - dx, (canvas.getHeight() / 2) - dy - (MARGIN + FONT_SIZE), text);
                //canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 12.0f, target);
                canvas.drawCircle((canvas.getWidth() / 2) - dx, (canvas.getHeight() / 2) - dy, 12.0f, target);
                canvas.drawText(distance, (canvas.getWidth() / 2) - dx, (canvas.getHeight() / 2) - dy + (MARGIN + FONT_SIZE), text);
            }

//            if (isVisible) {
//                canvas.drawText(mountWashington.getProvider(), (canvas.getWidth() / 2), (canvas.getHeight() / 2) - (MARGIN + FONT_SIZE), text);
//                //canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 12.0f, target);
//                canvas.drawCircle((canvas.getWidth() / 2), (canvas.getHeight() / 2), 12.0f, target);
//                canvas.drawText(distance, (canvas.getWidth() / 2), (canvas.getHeight() / 2) + (MARGIN + FONT_SIZE), text);
//            }
//        for (Objet objet : objets) {
//            objet.onDraw(canvas);
//        }
        }
    }

    public class OverlayRunner extends Runner {
        private int framePerSecond;

        public OverlayRunner() {
            framePerSecond = 1000 / FRAME;
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    if (location == null | lastAccelerometer == null | lastCompass == null)
                        continue;

                    /*
                    for (Objet objet: objets) {
                        LocationObjet locationObjet = ((LocationObjet) objet);
                        locationObjet.adjustment(location);
                    }*/
                    curBearingToMW = location.bearingTo(mountWashington);
                    distance = location.distanceTo(mountWashington) + "m";

                    // compute rotation matrix
                    float rotation[] = new float[9];
                    float identity[] = new float[9];
                    boolean gotRotation = SensorManager.getRotationMatrix(rotation,
                            identity, lastAccelerometer, lastCompass);

                    float cameraRotation[] = new float[9];
                    if (gotRotation) {
                        // remap such that the camera is pointing straight down the Y axis
                        SensorManager.remapCoordinateSystem(rotation, SensorManager.AXIS_X,
                                SensorManager.AXIS_Z, cameraRotation);
                        // orientation vector
                        SensorManager.getOrientation(cameraRotation, orientation);
//                        orientation[0] = (float) orientationFilters[0].update(orientation[0]);
                        orientation[1] = (float) orientationFilters[1].update(orientation[1]);
                        orientation[2] = (float) orientationFilters[2].update(orientation[2]);
                    }
                    Thread.sleep(framePerSecond);
                }
            } catch (Exception e) {
                Logger.printStackTrace(e);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        float output[] = event.values.clone();
        float output[] = lowPass(event.values, new float[event.values.length]);//new float[event.values.length];
        StringBuilder msg = new StringBuilder(event.sensor.getName()).append(" ");
        for (float value : /*event.values*/output) {
            msg.append("[").append(value).append("]");
        }

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                lastAccelerometer = /*event.values*/output.clone();
                accelData = msg.toString();
//                lastAccelerometer[0] = (float) accelerometerFilters[0].update(lastAccelerometer[0]);
//                lastAccelerometer[1] = (float) accelerometerFilters[1].update(lastAccelerometer[1]);
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroData = msg.toString();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                if (prevCompass == null) {
                    prevCompass = new float[/*event.values*/output.length];
                }
                if (lastCompass != null) {
                    for (int i = 0; i < lastCompass.length; i++) {
                        prevCompass[i] = lastCompass[i];
                    }
                }
                lastCompass = /*event.values*/output.clone();
                compassData = msg.toString();
                /*
                //!comment
                compass의 0번째 데이터가 x좌표에 영향을 미치는데 필터를 적용하면 x축 이동이 너무 느려짐
                 */
//                lastCompass[0] = (float) compassFilters[0].update(lastCompass[0]);
//                lastCompass[1] = (float) compassFilters[1].update(lastCompass[1]);
                break;
        }

        this.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    static final float ALPHA = 0.5f; // if ALPHA = 1 OR 0, no filter applies.

    protected float[] lowPass(float[] input, float[] output) {
        if (output == null) return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad / Math.PI * 180.0);
    }
/*
    private int getGpsSatelliteCount() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return TODO;
//            }
//            locationManager.registerGnssStatusCallback(new GnssStatus.Callback() {
//                @Override
//                public void onSatelliteStatusChanged(GnssStatus status) {
//                    super.onSatelliteStatusChanged(status);
//                }
//            });
//        }

//        final GnssStatus gs = locationManager.get;
//        gs.getSatelliteCount();
        final GpsStatus gs = locationManager.getGpsStatus(null);

        int i = 0, j = 0;
        final Iterator<GpsSatellite> it = gs.getSatellites().iterator();

        while(it.hasNext()) {
            GpsSatellite satellite = it.next();

            // [수정 : 2013/10/25]
            // 단순 위성 갯수가 아니라 사용할 수 있게 잡히는 위성의 갯수가 중요하다.
            if (satellite.usedInFix()) {
                j++; // i 값 보다는 이 값이 GPS 위성 사용 여부를 확인하는데 더 중요하다.
            }
            i++;
        }

        return j;
    }*/
}

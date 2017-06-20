package com.example.ar.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.ar.demo.filter.KalmanFilter;
import com.example.ar.demo.manage.GPSManager;
import com.example.ar.demo.model.InformationObjet;
import com.example.ar.demo.model.LocationObjet;
import com.example.core.manage.Logger;
import com.example.core.runner.Runner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hwang on 2017-04-26.
 * 작성자 : 황의택
 * 내용 : 주변 지역 정보를 카메라에 오버레이로 표시하는 View
 *        센터 데이터를 부드럽게 바꾸기 위해 칼만필터, low pass 사용
 */
public class OverlayKalmanFilterView extends View implements SensorEventListener {
    private final int FRAME = 60;

    private InformationObjet info = new InformationObjet();
    private Map<String, LocationObjet> objets = new HashMap<>();

    private OverlayRunner runner = null;

    private KalmanFilter[] orientationFilters = new KalmanFilter[3];
    private float[] lastAccelerometer = null;
    private float[] lastCompass = null;
    private float orientation[] = new float[3];

    public OverlayKalmanFilterView(Context context) {
        super(context);
        init(context);
    }

    public OverlayKalmanFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OverlayKalmanFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        orientationFilters[0] = new KalmanFilter(0.0f);
        orientationFilters[1] = new KalmanFilter(0.0f);
        orientationFilters[2] = new KalmanFilter(0.0f);

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
    }

    public Map<String, LocationObjet> getObjets() {
        return objets;
    }
    public void setObjets(Map<String, LocationObjet> objets) {
        this.objets = objets;
    }

    public void onDestroy() {
        if (this.runner != null) {
            this.runner.stopRunning();
        }
        for (String key : objets.keySet()) {
            objets.get(key).onDestroy();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        info.onDraw(canvas);

        if (orientation == null) return;

        canvas.rotate((float) (0.0f - Math.toDegrees(orientation[2])), canvas.getWidth() / 2, canvas.getHeight() / 2);
        for (String key : objets.keySet()) {
            objets.get(key).onDraw(canvas, orientation);
        }
    }

    /**
     * 카메라 위에 정보성 데이터를 표시하기 위한 오버레이 쓰레드
     */
    public class OverlayRunner extends Runner {
        private int framePerSecond;

        public OverlayRunner() {
            framePerSecond = 1000 / FRAME;
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    Location location = GPSManager.getInstance().getLocation();
                    if (location == null | lastAccelerometer == null | lastCompass == null)
                        continue;

                    for (String key : objets.keySet()) {
                        objets.get(key).adjustment(location);
                    }

                    float rotation[] = new float[9];
                    float identity[] = new float[9];
                    boolean gotRotation = SensorManager.getRotationMatrix(rotation,
                            identity, lastAccelerometer, lastCompass);

                    float cameraRotation[] = new float[9];
                    if (gotRotation) {

                        SensorManager.remapCoordinateSystem(rotation, SensorManager.AXIS_X,
                                SensorManager.AXIS_Z, cameraRotation);

                        SensorManager.getOrientation(cameraRotation, orientation);
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
                info.put("ACCELEROMETER", msg.toString());
                break;
            case Sensor.TYPE_GYROSCOPE:
                info.put("GYROSCOPE", msg.toString());
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                lastCompass = /*event.values*/output.clone();
                info.put("COMPASS", msg.toString());
                break;
        }

        this.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    static final float ALPHA = 0.5f; // if ALPHA = 1 OR 0, no filter applies.

    protected float[] lowPass(float[] input, float[] output) {
        if (output == null) return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }



    //----------------------보류------------------------------
    //거리 측정(Location 클래스에 거리측정 메소드가 있어서 필요 없어짐, 서버에서 사용할 일이 있을까봐 보류해둠)
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

    //위성 개수 측정
    //  - gps가 삼각측량으로 위치를 도출함으로 위성의 개수가 3개 미만일경우 정확도가 떨어짐 그래서 위성 개수를 측정하여 정확도를 판별하는데
    //    최근 사용법이 많이 바뀌었기 때문에 다시 알아볼 필요성이 있어서 잠시 보류해둠
/*    private int getGpsSatelliteCount() {
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

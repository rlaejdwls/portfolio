package com.example.ar.demo.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.location.Location;

import com.example.ar.demo.filter.KalmanFilter;
import com.example.core.AppCore;
import com.example.core.runner.Runner;

/**
 * Created by Hwang on 2017-05-01.
 * 작성자 : 황의택
 * 내용 : 주변의 위치정보(위도, 경도, 명칭 등)를 기억하고 그 정보를 실제 화면에
 *        어떻게 표시할 것인가에 대한 정보를 담고 있는 모델 클래스
 */
public class LocationObjet {
    private Location location;
    private LocationRunner runner = null;

    private Paint drawPaint = new Paint();
    private Paint textPaint = new Paint();

    private KalmanFilter dyf = new KalmanFilter(0.0f);
    private KalmanFilter dxf = new KalmanFilter(0.0f);

    private float[] orientation;
    private float density;
    private float fontSize;
    private float drawSize;
    private float distance;
    private float bearing;
    private float margin;
    private float width;
    private float height;
    private float dx;
    private float dy;

    private float verticalFOV = 0.0f;
    private float horizontalFOV = 0.0f;

    public LocationObjet(String provider, double lat, double lng) {
        this(provider, lat, lng, 0.0d);
    }
    public LocationObjet(String provider, double lat, double lng, double elv) {
        location = new Location(provider);
        if (runner == null) {
            runner = new LocationRunner();
//            runner.startRunning();
        }
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setAltitude(elv);
        density = AppCore.getApplication().getApplicationContext().getResources().getDisplayMetrics().density;

        fontSize = (11/*dp*/ * density);
        drawSize = (8/*dp*/ * density);
        margin = (4/*dp*/ * density);
        drawPaint.setColor(Color.GREEN);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(fontSize);
        textPaint.setColor(Color.GREEN);

        Camera.Parameters params = Camera.open().getParameters();
        verticalFOV = params.getVerticalViewAngle();
        horizontalFOV = params.getHorizontalViewAngle();
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public float getBearing() {
        return bearing;
    }
    public void setBearing(float bearing) {
        this.bearing = bearing;
    }
    public float getDx() {
        return dx;
    }
    public void setDx(float dx) {
        this.dx = dx;
    }
    public float getDy() {
        return dy;
    }
    public void setDy(float dy) {
        this.dy = dy;
    }

    public class LocationRunner extends Runner {
        @Override
        public void run() {
            while (isRunning) {
                if (orientation == null) continue;

                boolean isVisible = false;

                float degree = (float) Math.toDegrees(orientation[0]);
                float right = bearing + 90;//(horizontalFOV / 2);
                float left = bearing - 90;//(horizontalFOV / 2);

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

                float angle;

                //방향
                if (bearing >= 0) {
                    if (bearing < degree || ((-180 < degree) && (degree < bearing - 180))) {    //right
                        if (degree > 0) {
                            angle = degree - bearing;
                        } else {
                            angle = (180 - bearing) + (180 + degree);
                        }
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
                    }
                }

                dy = (float) ((height / verticalFOV) * Math.toDegrees(orientation[1]));
                dx = (width / horizontalFOV/*시야각*/) * angle;

                dy = (float) dyf.update(dy);
                dx = (float) dxf.update(dx);
            }
        }
    }

    public void adjustment(Location location) {
        this.distance = location.distanceTo(this.location);
        this.bearing = location.bearingTo(this.location);
    }

    public void onDraw(Canvas canvas, float[] orientation) {
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.orientation = orientation.clone();

        boolean isVisible = false;

        float degree = (float) Math.toDegrees(orientation[0]);
        float right = bearing + 90;//(horizontalFOV / 2);
        float left = bearing - 90;//(horizontalFOV / 2);

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

        float angle;

        //방향
        if (bearing >= 0) {
            if (bearing < degree || ((-180 < degree) && (degree < bearing - 180))) {    //right
                if (degree > 0) {
                    angle = degree - bearing;
                } else {
                    angle = (180 - bearing) + (180 + degree);
                }
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
            }
        }

        dy = (float) ((height / verticalFOV) * Math.toDegrees(orientation[1]));
        dx = (width / horizontalFOV/*시야각*/) * angle;

        dy = (float) dyf.update(dy);
        dx = (float) dxf.update(dx);

        if (isVisible) {
            canvas.drawText(location.getProvider(), (canvas.getWidth() / 2) - dx, (canvas.getHeight() / 2) - dy - drawSize - margin, textPaint);
            canvas.drawCircle((canvas.getWidth() / 2) - dx, (canvas.getHeight() / 2) - dy, drawSize, drawPaint);
            canvas.drawText(distance + "m", (canvas.getWidth() / 2) - dx, (canvas.getHeight() / 2) - dy + drawSize + fontSize + margin, textPaint);
        }
    }

    public void onDestroy() {
        if (runner != null) {
            runner.stopRunning();
        }
    }
}

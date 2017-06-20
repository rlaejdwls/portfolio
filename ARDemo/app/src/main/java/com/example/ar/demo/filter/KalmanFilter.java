package com.example.ar.demo.filter;

/**
 * Created by Hwang on 2017-04-26.
 * 작성자 : 황의택
 * 내용 : 칼만필터
 */
public class KalmanFilter {
    private double Q = 0.00001;
    private double R = 0.001;
    private double X = 0, P = 1, K;

    public KalmanFilter(double initValue) {
        X = initValue;
    }

    private void measurementUpdate(){
        K = (P + Q) / (P + Q + R);
        P = R * (P + Q) / (R + P + Q);
    }

    public double update(double measurement){
        measurementUpdate();
        X = X + (measurement - X) * K;
        return X;
    }
}

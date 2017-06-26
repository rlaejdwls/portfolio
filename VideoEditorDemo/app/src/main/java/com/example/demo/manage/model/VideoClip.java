package com.example.demo.manage.model;

/**
 * Created by Hwang on 2017-06-14.
 * 작성자 : 황의택
 * 내용 : 비디오에서 편집 될 부분의 시간을 저장하는 모델
 */
public class VideoClip {
    private double startTime;
    private double stopTime;

    public VideoClip(double startTime, double stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public double getStartTime() {
        return startTime;
    }
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
    public double getStopTime() {
        return stopTime;
    }
    public void setStopTime(double stopTime) {
        this.stopTime = stopTime;
    }

    @Override
    public String toString() {
        return "VideoClip{" +
                "startTime=" + startTime +
                ", stopTime=" + stopTime +
                '}';
    }
}

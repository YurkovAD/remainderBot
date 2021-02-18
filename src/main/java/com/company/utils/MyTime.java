package com.company.utils;

/**
 * Created by yurkov.ad on 03.02.2021.
 */
public class MyTime {
    private int hour;
    private int minute;
//    private int second;

    public MyTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

//    public MyTime(int hour, int minute, int second) {
//        this.hour = hour;
//        this.minute = minute;
//        this.second = second;
//    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

//    public int getSecond() {
//        return second;
//    }
//
//    public void setSecond(int second) {
//        this.second = second;
//    }
}

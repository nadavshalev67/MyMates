package com.example.mymatess;

public class Date {
    public int day;
    public int month;
    public int year;
    public boolean mIsChecked;

    public Date(int day, int month, int year, boolean isChecked) {
        this.day = day;
        this.month = month;
        this.year = year;
        mIsChecked = isChecked;
    }
}
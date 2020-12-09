package com.example.mymatess.model;

import androidx.annotation.Nullable;

public class Date {
    public int day;
    public int month;


    public int year;
    public boolean mIsChecked;

    @Override
    public boolean equals(@Nullable Object obj) {
        Date date = (Date) obj;
        if (this.day == date.day && this.month == date.month && this.year == date.year) {
            return true;
        }
        return false;
    }

    public Date(int day, int month, int year, boolean isChecked) {
        this.day = day;
        this.month = month;
        this.year = year;
        mIsChecked = isChecked;
    }

    @Override
    public String toString() {
        return String.format("%d-%d-%d", day, month, year);
    }
}
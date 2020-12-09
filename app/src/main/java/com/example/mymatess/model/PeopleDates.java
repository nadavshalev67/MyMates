package com.example.mymatess.model;

import java.util.ArrayList;

public class PeopleDates {
    public Date date;

    public PeopleDates(Date date, ArrayList<String> uuids) {
        this.date = date;
        this.uuids = uuids;
    }

    public ArrayList<String> uuids;
}

package com.example.mymatess.model;

import java.util.ArrayList;

public class DataApplication {


    public ArrayList<Profile> profiles = new ArrayList<>();
    public ArrayList<PeopleDates> peopleDates = new ArrayList<>();


    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    public void setPeopleDates(ArrayList<PeopleDates> peopleDates) {
        this.peopleDates = peopleDates;
    }
}

package com.example.mymatess.model;

import android.text.TextUtils;

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

    public ArrayList<String> getAllPeople(Date date) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> listOfUuids = new ArrayList<>();
        for (PeopleDates peopleDates : peopleDates) {
            if (peopleDates.date.equals(date)) {
                listOfUuids = peopleDates.uuids;
            }
        }
        for (Profile profile : profiles) {
            for (String uuid : listOfUuids) {
                if (TextUtils.equals(profile.uuid, uuid)) {
                    result.add(profile.fullName);
                    break;
                }
            }

        }
        return result;
    }
}

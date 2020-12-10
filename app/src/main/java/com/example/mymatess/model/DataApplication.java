package com.example.mymatess.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public HashMap<String, Integer> getAllHobbies(Date date) {
        HashMap<String, Integer> result = new HashMap<>();
        ArrayList<String> listOfUuids = new ArrayList<>();
        for (PeopleDates peopleDates : peopleDates) {
            if (peopleDates.date.equals(date)) {
                listOfUuids = peopleDates.uuids;
            }
        }
        for (Profile profile : profiles) {
            for (String uuid : listOfUuids) {
                if (TextUtils.equals(profile.uuid, uuid)) {
                    List<String> hobbies = profile.hobbies;
                    if (hobbies != null) {
                        for (String hobbie : hobbies) {
                            int number = result.get(hobbie) == null ? 0 : result.get(hobbie);
                            number++;
                            if (!TextUtils.isEmpty(hobbie)) {
                                result.put(hobbie, number);
                            }
                        }
                    }

                    break;
                }
            }

        }
        return result;
    }
}

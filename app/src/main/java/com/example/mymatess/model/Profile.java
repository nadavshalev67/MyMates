package com.example.mymatess.model;

import java.util.List;

public class Profile {
    public String uuid;
    public String email;
    public String fullName;
    public String groupTeam;
    public List<String> hobbies;

    public Profile(String uuid, String email, String fullName, String groupTeam, List<String> hobbies) {
        this.uuid = uuid;
        this.email = email;
        this.fullName = fullName;
        this.groupTeam = groupTeam;
        this.hobbies = hobbies;
    }
}

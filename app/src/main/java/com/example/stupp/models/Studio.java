package com.example.stupp.models;

import java.io.Serializable;

public class Studio implements Serializable {

    public Studio(String studio_name, String studio_id, String avatar, String details) {
        this.studio_name = studio_name;
        this.studio_id = studio_id;
        this.avatar = avatar;
        this.details = details;
    }

    public Studio(){
    }

    public String studio_name;
    public String studio_id;
    public String avatar;
    public String details;
}

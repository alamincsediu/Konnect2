package com.example.shashankmohabia.konnect.Matches;

/**
 * Created by shash on 2/27/2018.
 */

public class MatchesObject {
    private String userID, name, profilePicUrl;

    public MatchesObject(String userID, String name, String profilePicUrl) {
        this.userID = userID;
        this.name = name;
        this.profilePicUrl = profilePicUrl;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getName() {
        return this.name;
    }

    public void setname(String name) {
        this.name = name;
    }


    public String getprofilePicUrl() {
        return this.profilePicUrl;
    }

    public void setprofilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}

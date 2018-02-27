package com.example.shashankmohabia.konnect.Card;

/**
 * Created by Shashank Mohabia on 2/15/2018.
 */

public class Cards {
    private String userID, name, profileImageUrl;
    public Cards(String userID, String name, String profileImageUrl){
        this.userID = userID;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserID(){
        return this.userID;
    }
    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getprofileImageUrl(){
        return this.profileImageUrl;
    }
    public void setprofileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }
}

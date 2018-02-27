package com.example.shashankmohabia.konnect.Chat;

/**
 * Created by shash on 2/27/2018.
 */

public class ChatsObject {
     private String message;
     private Boolean currentUserBool;

    public ChatsObject(String message, Boolean currentUserBool) {
        this.currentUserBool = currentUserBool;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getCurrentUserBool() {
        return this.currentUserBool;
    }

    public void setCurrentUserBool(Boolean currentUserBool) {
        this.currentUserBool = currentUserBool;
    }
}

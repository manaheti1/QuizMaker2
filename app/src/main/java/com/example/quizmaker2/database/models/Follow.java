package com.example.quizmaker2.database.models;

import java.io.Serializable;

public class  Follow implements Serializable {
    int userID;
    int followID;

    public Follow(int userID, int followID) {
        this.userID = userID;
        this.followID = followID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFollowID() {
        return followID;
    }

    public void setFollowID(int followID) {
        this.followID = followID;
    }
}

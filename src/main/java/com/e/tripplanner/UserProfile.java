package com.e.tripplanner;

import java.util.ArrayList;

public class UserProfile {
    public String userName,userEmail,userPhone;
    String suggestionTest;

    public UserProfile(String userName, String userEmail, String userPhone, String suggestionTest) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.suggestionTest=suggestionTest;
    }
}
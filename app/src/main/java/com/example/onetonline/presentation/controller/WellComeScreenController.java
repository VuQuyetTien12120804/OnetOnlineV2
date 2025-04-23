package com.example.onetonline.presentation.controller;

import com.example.onetonline.business.UserData;
import com.example.onetonline.data.User;
import com.example.onetonline.presentation.view.WellComeScreen;

public class WellComeScreenController {
    private WellComeScreen wellComeScreen;
    private UserData userData;

    public WellComeScreenController(WellComeScreen wellComeScreen) {
        this.wellComeScreen = wellComeScreen;
        this.userData = new UserData(wellComeScreen);
    }

    public void handleHasRecord(Class<?> activityClass){
        if(userData.hasRecords()){
            wellComeScreen.navigateTo(activityClass);
        }
        else return;
    }

    public void handleLogin(Class<?> activityClass){
        wellComeScreen.navigateTo(activityClass);
    }

    public void handleRegister(Class<?> activityClass){
        wellComeScreen.navigateTo(activityClass);
    }

    public void handleGuest(Class<?> activityClass){
        userData.insert(new User());
        wellComeScreen.navigateTo(activityClass);
        System.out.println("WelcomeController chuyen sang MenuGameForm");
    }
}

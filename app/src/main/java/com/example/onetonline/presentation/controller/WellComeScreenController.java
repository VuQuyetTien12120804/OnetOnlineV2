package com.example.onetonline.presentation.controller;

import com.example.onetonline.presentation.view.WellComeScreen;

public class WellComeScreenController {
    private WellComeScreen wellComeScreen;

    public WellComeScreenController(WellComeScreen wellComeScreen) {
        this.wellComeScreen = wellComeScreen;
    }
    public void handleLogin(Class<?> activityClass){
        wellComeScreen.navigateTo(activityClass);
    }
    public void handleRegister(Class<?> activityClass){
        wellComeScreen.navigateTo(activityClass);
    }
    public void handleGuest(Class<?> activityClass){
        wellComeScreen.navigateTo(activityClass);
    }
}

package com.example.onetonline.presentation.model;

public class MenuModel {
    String selectedAction;
    public MenuModel() {
        selectedAction = null;
    }
    public String getSelectedAction() {
        return selectedAction;
    }
    public void setSelectedAction(String action) {
        selectedAction = action;
    }
}

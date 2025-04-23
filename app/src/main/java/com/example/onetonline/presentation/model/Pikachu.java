package com.example.onetonline.presentation.model;

import android.content.Context;

import androidx.annotation.NonNull;

public class Pikachu {
    private int xPoint;
    private int yPoint;
    private int imageID;
    private boolean isSelected;
    public Pikachu(int xPoint, int yPoint, int imageID){
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.imageID = imageID;
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getxPoint() {
        return xPoint;
    }

    public void setxPoint(int xPoint) {
        this.xPoint = xPoint;
    }

    public int getyPoint() {
        return yPoint;
    }

    public void setyPoint(int yPoint) {
        this.yPoint = yPoint;
    }

    public int getImageID() {
        return imageID;
    }
    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    @NonNull
    @Override
    public java.lang.String toString() {
        return String.valueOf(imageID);
    }
}


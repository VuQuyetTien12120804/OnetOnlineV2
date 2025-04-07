package com.example.onetonline.presentation.model;

import android.content.Context;

public class Pikachu {
    private int xPoint;
    private int yPoint;
    private int imageID;

    public Pikachu(int ImageID){
        this.imageID = ImageID;
    }
    public Pikachu(int xPoint, int yPoint){
        this.xPoint = xPoint;
        this.yPoint = yPoint;
    }
    public Pikachu(int xPoint, int yPoint, int imageID){
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.imageID = imageID;
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
}


package com.example.onetonline.presentation.model;

import android.content.Context;

public class Pikachu extends androidx.appcompat.widget.AppCompatButton {
    private int xPoint;
    private int yPoint;

    public Pikachu(Context context) {
        super(context);
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
}


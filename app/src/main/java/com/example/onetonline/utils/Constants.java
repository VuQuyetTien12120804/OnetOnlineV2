package com.example.onetonline.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Constants {
    public static final int MAP_COL = 16;
    public static final int MAP_ROW = 9;
    public static final int MAX_LEVEL = 100;
    public static final int MIN_LEVEL = 1;
    public static final int PIKACHU_NUMBER = 36;
    public static final int COUPLE = MAP_COL * MAP_ROW / 2;
    public static final int MINUTE = 6;
    public static final int SECOND = 15;
    public static final int TIME = MINUTE * 60 + SECOND;
    public static final int plusScore = 10;
    public static final int plusExp = 10;
    public static final int expStartLevelCap = 20;
    public static final String DEFAULT_AVATAR_FILENAME = "avatar_image";
    public static final int STORAGE_PERMISSION_CODE = 100;
    public static final String SYNC_SUCCESS_ACTION = "SYNC_SUCCESS";
    public static final String now(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date now = new Date();
        return sdf.format(now) + "000+00:00";
    }
    public static int position (int x, int y){
        return (x - 1) * MAP_COL + y - 1;
    }
    public static int X (int position){
        return position / (MAP_COL) + 1;
    }
    public static int Y (int position){
        return position % (MAP_COL) + 1;
    }

}

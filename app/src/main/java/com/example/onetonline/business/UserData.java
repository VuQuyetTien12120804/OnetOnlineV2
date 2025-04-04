package com.example.onetonline.business;

import android.content.Context;
import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.onetonline.data.myDBHelper;
import com.example.onetonline.presentation.model.UserInf;
import com.example.onetonline.utils.*;

public class UserData {
    private myDBHelper myDB;
    public UserData(Context context) {
        this.myDB = new myDBHelper(context);
    }

    public UserInf getData(){
        myDB.openDB();
        Cursor cursor = myDB.filteredData();
        if(cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.NAME()));
            String level = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.LEVEL()));
            String exp = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.EXP()));
            String score = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.SCORE()));
            String last_update = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.LAST_UPDATE()));
            UserInf userInf = new UserInf(name, Integer.parseInt(level), Integer.parseInt(score), Integer.parseInt(exp), last_update);
            return userInf;
        }
        myDB.close();
        return null;
    }
    public UserInf update(String name, int level, int scoreView, int exp, String last_update){
        myDB.openDB();
        int oldLv = level;
        int newScore = 0;
        exp = exp + Constants.plusExp;
        level += exp / (Constants.expCap(level));
        exp = exp % Constants.expCap(oldLv);
        Cursor cursor = myDB.filteredData();
        if(cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            int scoreUser = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.SCORE())));
            newScore = Math.max(scoreView,scoreUser);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm:ss a", Locale.ENGLISH);
        UserInf userInf = new UserInf(name, level, newScore, exp,last_update);
        myDB.Update(level,newScore,exp, sdf.format(new Date()));
        myDB.close();
        return userInf;
    }

}

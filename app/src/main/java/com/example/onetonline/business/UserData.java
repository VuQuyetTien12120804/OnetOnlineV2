package com.example.onetonline.business;

import static com.example.onetonline.utils.Constants.expStartLevelCap;
import static com.example.onetonline.utils.Constants.now;

import android.content.Context;
import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.onetonline.data.User;
import com.example.onetonline.data.UserRepo;
import com.example.onetonline.data.myDBHelper;
import com.example.onetonline.data.userRanking;
import com.example.onetonline.presentation.model.UserInf;
import com.example.onetonline.utils.*;

public class UserData {
    private myDBHelper myDB;
    private UserRepo userRepo;

    public UserData(Context context) {
        this.myDB = new myDBHelper(context);
        userRepo = new UserRepo(context);
    }

    public void getRankingList(UserRepo.GetTopUserCallBack callBack){
        userRepo.getTopUser(new UserRepo.GetTopUserCallBack() {
            @Override
            public void onSuccess(List<userRanking> rankingList) {
                callBack.onSuccess(rankingList);
            }

            @Override
            public void onFailure(String err) {
                callBack.onFailure(err);
            }
        });
    }

    public UserInf getData(){
        myDB.openDB();
        Cursor cursor = myDB.filteredData();
        UserInf userInf = null;
        if(cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.NAME()));
            int level = cursor.getInt(cursor.getColumnIndexOrThrow(myDBHelper.LEVEL()));
            int exp = cursor.getInt(cursor.getColumnIndexOrThrow(myDBHelper.EXP()));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(myDBHelper.SCORE()));
            String last_update = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.LAST_UPDATE()));
            userInf = new UserInf(name, level, score, exp, last_update);
        }
        cursor.close();
        myDB.close();
        return userInf;
    }

    public void insert(User user){
        myDB.Insert(user);
    }

    public void update(User user){
        myDB.openDB();
        myDB.Update(user);
        myDB.close();
    }

    public UserInf update(String name, int level, int scoreView, int exp, String last_update){
        myDB.openDB();
        int oldLv = level;
        int newScore = 0;
        exp = exp + Constants.plusExp;
        level += exp / expCap(level);
        exp = exp % expCap(oldLv);
        Cursor cursor = myDB.filteredData();
        if(cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            int scoreUser = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.SCORE())));
            newScore = Math.max(scoreView,scoreUser);
        }
        UserInf userInf = new UserInf(name, level, newScore, exp, last_update);
        myDB.Update(level, newScore, exp, now());
        myDB.close();
        return userInf;
    }

    public static int expCap(int level){
        return expStartLevelCap * level;
    }


    public boolean hasRecords(){
        return myDB.hasRecords();
    }
}

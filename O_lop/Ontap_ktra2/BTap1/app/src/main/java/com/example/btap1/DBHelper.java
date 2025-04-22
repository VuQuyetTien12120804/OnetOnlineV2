package com.example.btap1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "mydb";
    public static final String Table = "Songs";
    public static final int Version = 1;
    public static final String Id = "Id";
    public static final String Name = "Name";
    public static final String Singer = "Singer";
    public static final String Time = "Time";
    private SQLiteDatabase myDB;

    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table " + Table + " (" +
                Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Name + " TEXT NOT NULL, " +
                Singer + " TEXT NOT NULL, " +
                Time+ " FLOAT)";
        sqLiteDatabase.execSQL(sql);

        insertSampleData(sqLiteDatabase);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Dữ liệu mẫu
        String[][] sampleData = {
                {"Phút cuối", "Bằng Kiều", "3.27"},
                {"Bông hồng thủy tinh", "Bức Tường", "4.18"},
                {"Hà Nội mùa thu", "Mỹ Linh", "4.11"},
                {"Bà tôi", "Tùng Dương", "3.51"},
                {"Gót Hồng", "Quang Dũng", "4.01"},
                {"Đêm đông", "Bằng Kiều", "4.12"}
        };

        for (String[] data : sampleData) {
            ContentValues values = new ContentValues();
            values.put(Name, data[0]);
            values.put(Singer, data[1]);
            values.put(Time, Float.parseFloat(data[2]));
            db.insert(Table, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public synchronized void close() {
        if (myDB != null && myDB.isOpen()){
            myDB.close();
        }
        super.close();
    }

    public void openDB(){
        myDB = getWritableDatabase();
    }

    public static String Id() {
        return Id;
    }

    public static String Name() {
        return Name;
    }

    public static String Singer() {
        return Singer;
    }

    public static String Time() {
        return Time;
    }

    public Cursor Display(){
        openDB();
        String queryDisplay = "Select * From " + Table + " Order by " + Time + " ASC";
        return myDB.rawQuery(queryDisplay, null);
    }

    public long Insert(String name, String singer, float time){
        openDB();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, name);
        values.put(Singer, singer);
        values.put(Time, time);

        return db.insert(Table, null, values);
    }
}

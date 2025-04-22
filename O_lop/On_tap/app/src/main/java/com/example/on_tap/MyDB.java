package com.example.on_tap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {
    private static final String DBName = "mydb.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "qlcasi";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String SINGER = "singer";
    private static final String TIME = "time";
    private SQLiteDatabase mydatabase;

    public MyDB(@Nullable Context context) {
        super(context, DBName, null, VERSION);
    }

    public static String getDBName(){return DBName;}
    public static String ID(){return ID;}
    public static String NAME(){return NAME;}
    public static String SINGER(){return SINGER;}
    public static String TIME(){return TIME;}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT NOT NULL, " +
                SINGER + " TEXT NOT NULL, " +
                TIME + " FLOAT NOT NULL)";

        db.execSQL(queryTable);

        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        String[][] sampleData = {
                {"Phút cuối", "Bằng Kiều", "3.27"},
                {"Nắng ấm xa dần", "Sơn Tùng", "3.60"},
                {"Đom đóm", "J97", "4.02"},
                {"Gạt đi nước mắt", "Noo Phước Thịnh", "4.22"},
                {"Tràn bộ nhớ", "Dương Domic", "4.44"},
                {"DG House", "Rhyder", "5.11"}
        };
        for (String[] data: sampleData){
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, data[0]);
            contentValues.put(SINGER, data[1]);
            contentValues.put(TIME, data[2]);
            db.insert(TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (mydatabase!=null && mydatabase.isOpen()){
            mydatabase.close();
        }
        super.close();
    }

    public void openDB(){
        mydatabase =getWritableDatabase();
    }

    public void closeDB(){
        if (mydatabase!=null && mydatabase.isOpen()){
            mydatabase.close();
        }
    }

    public Cursor DisplayAll(){
        String queryDisplay = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + TIME + " ASC";
        return mydatabase.rawQuery(queryDisplay, null);
    }

    public long Insert(String name, String singer, String time){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(SINGER, singer);
        contentValues.put(TIME, time);

        return mydatabase.insert(TABLE_NAME, null, contentValues);
    }

    public long Update(String id, String name, String singer, String time){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(SINGER, singer);
        contentValues.put(TIME, time);
        String where = ID + " = " + id;
        return mydatabase.update(TABLE_NAME, contentValues, where, null);
    }

    public long Delete(String id){
        String where = ID + " = " + id;
        return mydatabase.delete(TABLE_NAME, where, null);
    }
}

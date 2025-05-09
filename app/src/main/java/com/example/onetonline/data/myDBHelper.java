package com.example.onetonline.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class myDBHelper extends SQLiteOpenHelper {
    private static final String DBName = "mydb.db";
    private static final int _VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String ID = "user_id";
    private static final String NAME = "user_name";
    private static final String LEVEL = "level";
    private static final String SCORE = "score";
    private static final String EXP = "exp";
    private static final String LAST_UPDATE = "last_update";
    private static final String IS_DELETED = "is_deleted";
    private SQLiteDatabase myDB;

    public myDBHelper(@Nullable Context contexts) {
        super(contexts, DBName,null, _VERSION);
    }

    public static String SCORE() {
        return SCORE;
    }

    public static String EXP() {
        return EXP;
    }

    public static String DBName() {
        return DBName;
    }

    public static String LAST_UPDATE() {
        return LAST_UPDATE;
    }

    public static String LEVEL() {
        return LEVEL;
    }

    public static String IS_DELETED() {
        return IS_DELETED;
    }

    public static String NAME() {
        return NAME;
    }

    public static String ID() {
        return ID;
    }

    public static String TABLE_NAME() {
        return TABLE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table " + TABLE_NAME + " (" +
                ID + " TEXT PRIMARY KEY, " +
                NAME + " TEXT NOT NULL, " +
                LEVEL + " INTEGER DEFAULT 1, " +
                SCORE + " INTEGER DEFAULT 0, " +
                EXP + " INTEGER DEFAULT 0, " +
                LAST_UPDATE + " TEXT NOT NULL, " +
                IS_DELETED + " INTEGER DEFAULT 0)";
        db.execSQL(sql);
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
        if (myDB != null && myDB.isOpen()){
            myDB.close();
        }
        super.close();
    }

    public void openDB(){
        myDB = getWritableDatabase();
    }

    public Cursor Display(){
        String queryDisplay = "Select * From " + TABLE_NAME;
        return myDB.rawQuery(queryDisplay, null);
    }

    public Cursor filteredData(){
        String queryDisplay = "Select " +
                NAME + ", " +
                LEVEL + ", " +
                SCORE + ", " +
                EXP + ", " +
                LAST_UPDATE +
                " From "+ TABLE_NAME;
        return myDB.rawQuery(queryDisplay, null);
    }

    public long Insert(User user){
        openDB();
        ContentValues values = new ContentValues();
        values.put(ID, user.id());
        values.put(NAME, user.userName());
        values.put(LEVEL, user.level());
        values.put(SCORE, user.score());
        values.put(EXP, user.exp());
        values.put(LAST_UPDATE, user.lastUpdate());
        values.put(IS_DELETED, user.isDeleted() ? 1 :0);

        return myDB.insert(TABLE_NAME, null, values);
    }

    public long Update(User user){
        ContentValues values = new ContentValues();
        values.put(NAME, user.userName());
        values.put(LEVEL, user.level());
        values.put(SCORE, user.score());
        values.put(EXP, user.exp());
        values.put(LAST_UPDATE, user.lastUpdate());
        values.put(IS_DELETED,user.isDeleted());
        String where = ID + " = " + "'" + user.id() + "'";

        return myDB.update(TABLE_NAME, values, where, null);
    }

    public long Update(int level, int score, int exp, String last_update){
        ContentValues values = new ContentValues();
        values.put(LEVEL, level);
        values.put(SCORE, score);
        values.put(EXP, exp);
        values.put(LAST_UPDATE, last_update);

        return myDB.update(TABLE_NAME, values, null , null);
    }

    public long Delete(String id){
        String where = ID + " = " + id;

        return myDB.delete(TABLE_NAME, where, null);
    }

    public long DeleteAll(){
        return myDB.delete(TABLE_NAME, null, null);
    }

    public long UpdateVersion(String last_update){
        ContentValues values = new ContentValues();
        values.put(LAST_UPDATE, last_update);

        return myDB.update(TABLE_NAME, values, null , null);
    }

    public boolean hasRecords() {
        openDB();
        Cursor cursor = Display();
        if(cursor != null && cursor.getCount() != 0){
            cursor.close();
            return true;
        }
        return false;
    }
}

package com.example.on_tap_de_thi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sqlite extends SQLiteOpenHelper {
    private static final  String DATABASE_NAME = "contact.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contact";

    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String PHONE_NUMBER = "phone_number";
    public Sqlite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT, "
                + PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(createQuery);

        List<Student> listStudent = new ArrayList<>();
        listStudent.add(new Student(1, "Nam", "12121212"));

        Collections.sort(listStudent, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Student student:listStudent){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, student.getId());
            contentValues.put(NAME, student.getName());
            contentValues.put(PHONE_NUMBER, student.getPhone_number());
            db.insert(TABLE_NAME, null, contentValues);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXiSTS " + TABLE_NAME);
    }

    public List<Student> getStidemt(){
        List<Student> student = new ArrayList<>();
        SQLiteDatabase readableDatabase = this.getReadableDatabase();


    }
}

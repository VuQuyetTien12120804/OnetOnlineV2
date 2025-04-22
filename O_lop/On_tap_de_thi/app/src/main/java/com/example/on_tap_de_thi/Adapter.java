package com.example.on_tap_de_thi;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayList<Student> {
    private Context context;
    private List<Student> listStudent;
    private int layout;
    public Adapter(@NonNull Context context, List<Student> listStudent) {
        super(context, R.layout.layout_student, listStudent);
        this.context = context;
        this.listStudent = listStudent;
    }
}

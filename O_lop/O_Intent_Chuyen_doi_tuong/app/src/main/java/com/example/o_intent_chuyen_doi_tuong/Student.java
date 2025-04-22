package com.example.o_intent_chuyen_doi_tuong;

import java.io.Serializable;

public class Student implements Serializable {
    private String Hoten;
    private int Namsinh;

    public Student(){

    }

    public Student(String hoten, int namsinh) {
        Hoten = Hoten;
        Namsinh = namsinh;
    }

    public int getNamsinh() {
        return Namsinh;
    }

    public void setNamsinh(int namSinh) {
        Namsinh = namSinh;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }
}

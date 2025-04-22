package com.example.restapi_basic;

public class CarModel {
    private String _id;
    private String ten;
    private int namSX;
    private String hang;
    private double gia;

    public CarModel(String ten, int namSX, String hang, double gia) {
        this.ten = ten;
        this.namSX = namSX;
        this.hang = hang;
        this.gia = gia;
    }

    public CarModel(String _id, double gia, String hang, int namSX, String ten) {
        this._id = _id;
        this.gia = gia;
        this.hang = hang;
        this.namSX = namSX;
        this.ten = ten;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public int getNamSX() {
        return namSX;
    }

    public void setNamSX(int namSX) {
        this.namSX = namSX;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}

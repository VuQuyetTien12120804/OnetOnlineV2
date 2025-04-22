package com.example.custom_gridview;

public class Item {
    private int image;
    private String name;
    private int price;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item(int image, String name, int price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }
}

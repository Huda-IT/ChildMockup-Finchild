package com.example.tmp_sda_1184.child_mockup;

import android.os.Parcel;
import android.os.Parcelable;

public class Item  {

    private String name;
    private double quantity;
    private double price;
    private String category;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Item(String name, double quantity, double price, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }
}

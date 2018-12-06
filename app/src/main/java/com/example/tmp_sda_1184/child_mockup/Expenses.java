package com.example.tmp_sda_1184.child_mockup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Expenses {

     String expensesId;
    private String store;
    private String date;
    private double totalAmount;
    private ArrayList<Item> items= new ArrayList <>();

    public Expenses() {
    }

    public Expenses(String expensesId, String store, String date,double totalAmount, ArrayList <Item> items) {
        this.expensesId = expensesId;
        this.store = store;
        this.date = date;
        this.items = items;
        this.totalAmount=totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getExpensesId() {
        return expensesId;
    }

    public String getStore() {
        return store;
    }

    public String getDate() {
        return date;
    }

    public ArrayList <Item> getItems() {
        return items;
    }

    public void setExpensesId(String expensesId) {
        this.expensesId = expensesId;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}

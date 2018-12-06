package com.example.tmp_sda_1184.child_mockup;

public class ParentAccount {

    private String parentAccountNumber;
    private String parentCardNumber;
    private String name;
    private boolean status;

    public ParentAccount() {
    }

    public ParentAccount(String parentAccountNumber, String parentCardNumber, String name, boolean status) {
        this.parentAccountNumber = parentAccountNumber;
        this.parentCardNumber = parentCardNumber;
        this.name = name;
        this.status = status;
    }

    public String getParentAccountNumber() {
        return parentAccountNumber;
    }

    public String getParentCardNumber() {
        return parentCardNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setParentAccountNumber(String parentAccountNumber) {
        this.parentAccountNumber = parentAccountNumber;
    }

    public void setParentCardNumber(String parentCardNumber) {
        this.parentCardNumber = parentCardNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

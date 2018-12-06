package com.example.tmp_sda_1184.child_mockup;

public class ChildInfo {
    private String childAccNo;
    private double balance;
    String parentAccNo;
    String childAccNoID;

    public ChildInfo(String childAccNo, double balance, String parentAccNo, String childAccNoID) {
        this.childAccNo = childAccNo;
        this.balance = balance;
        this.parentAccNo = parentAccNo;
        this.childAccNoID = childAccNoID;
    }


    public String getParentAccNo() {
        return parentAccNo;
    }

    public void setParentAccNo(String parentAccNo) {
        this.parentAccNo = parentAccNo;
    }

    public String getChildAccNoID() {
        return childAccNoID;
    }

    public void setChildAccNoID(String childAccNoID) {
        this.childAccNoID = childAccNoID;
    }


    public String getChildAccNo() {
        return childAccNo;
    }

    public void setChildAccNo(String childAccNo) {
        this.childAccNo = childAccNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals( Object obj) {
        return super.equals(obj);
    }

   @Override
    public String toString (){
        return "AccountNo: " + childAccNo + " balance: " + balance + " ParentAccNo" + parentAccNo + "ChildID" + childAccNoID;
    }
}

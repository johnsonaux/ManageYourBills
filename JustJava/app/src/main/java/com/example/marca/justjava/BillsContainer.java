package com.example.marca.justjava;

/**
 * Created by marca on 02.10.2017.
 */

public class BillsContainer {

    private static BillsContainer unique = null;
    private String mWho;
    private Bill mBill;


    //Constructor singleton pattern
    private BillsContainer() {
    }

    public static BillsContainer instance() {
        if (unique == null) {
            unique = new BillsContainer();
        }
        return unique;
    }

    public String getmWho() {
        return mWho;
    }

    public void setmWho(String mWho) {
        this.mWho = mWho;
    }

    public Bill getmBill() {
        return mBill;
    }

    public void setmBill(Bill mBill) {
        this.mBill = mBill;
    }
}

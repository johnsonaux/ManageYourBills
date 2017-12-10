package com.example.marca.justjava;

/**
 * Created by marca on 26.09.2017.
 */

public class Bill {

    private int mId;
    private String mName;
    private long mDate;
    private String mText;
    private String mAmount;
    private String mCategory;
    private int mGender;

    public Bill(int id, String name, long datum, String text, String betrag, String category, int gender) {
        mId = id;
        mName = name;
        mDate = datum;
        mText = text;
        mAmount = betrag;
        mCategory = category;
        mGender = gender;
    }

    public Bill(String name, long datum, String text, String betrag, String cat, int gender) {
        mName = name;
        mDate = datum;
        mText = text;
        mAmount = betrag;
        mCategory = cat;
        mGender = gender;
    }

    //Constructor when sending data via email
    public Bill(int id, String name, long datum, String text, String betrag, String cat) {
        mId = id;
        mName = name;
        mDate = datum;
        mText = text;
        mAmount = betrag;
        mCategory = cat;
    }

    protected String getmName() {
        return mName;
    }

    protected int getmId() {
        return mId;
    }

    protected long getmDate() {
        return mDate;
    }

    protected String getmText() {
        return mText;
    }

    protected String getmAmount() {
        return mAmount;
    }

    protected String getmCategory() {
        return mCategory;
    }

    protected int getmGender() {
        return mGender;
    }
}

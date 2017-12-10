package com.example.marca.justjava;

/**
 * Created by marca on 27.11.2017.
 */

public class User {

    private String mName;
    private int mGender;

    public User(String name, int gender) {
        mName = name;
        mGender = gender;
    }

    public String getmName() {
        return mName;
    }

    public int getmGender() {
        return mGender;
    }
}

package com.example.marca.justjava;

/**
 * Created by marca on 28.09.2017.
 */

public class FilterContainer {

    private static FilterContainer unique = null;
    private boolean mPersonSelected = false;
    private String mWho;

    private boolean mCategorySelected = false;
    private String mWhichCategory;

    //Constructor singleton pattern
    private FilterContainer() {
    }

    public static FilterContainer instance() {
        if (unique == null) {
            unique = new FilterContainer();
        }
        return unique;
    }

    //Getter Setter is User selected?
    public boolean ismPersonSelected() {
        return mPersonSelected;
    }

    public void setmPersonSelected(boolean mPersonSelected) {
        this.mPersonSelected = mPersonSelected;
    }

    //Getter Setter selected User
    public String getmWho() {
        return mWho;
    }

    public void setmWho(String mWho) {
        this.mWho = mWho;
    }

    //Getter Setter is Cat selected?
    public boolean ismCategorySelected() {
        return mCategorySelected;
    }

    public void setmCategorySelected(boolean mCategorySelected) {
        this.mCategorySelected = mCategorySelected;
    }

    //Getter Setter selected Cat
    public String getmWhichCategory() {
        return mWhichCategory;
    }

    public void setWhichCat(String whichCat) {
        this.mWhichCategory = whichCat;
    }
}

package com.example.marca.justjava;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by marca on 07.10.2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles = new String[]{"Startseite", "Monate", "Alle"};

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new StartFragment();
        }
        if (position == 1) {
            return new MonthListFragment();
        } else {
            return new AllBillsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}

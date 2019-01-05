package com.opalfire.foodorder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.opalfire.foodorder.fragments.RestaurantSearchFragment;
import com.opalfire.foodorder.fragments.SearchFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.mNumOfTabs = i;
    }

    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new RestaurantSearchFragment();
            case 1:
                return new SearchFragment();
            default:
                return 0;
        }
    }

    public int getCount() {
        return this.mNumOfTabs;
    }
}

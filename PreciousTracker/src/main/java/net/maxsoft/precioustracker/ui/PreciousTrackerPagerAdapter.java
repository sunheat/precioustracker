package net.maxsoft.precioustracker.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PreciousTrackerPagerAdapter extends FragmentPagerAdapter {

    public static final int TAB_COUNT = 3;
    public static final int TAB_PRECIOUS_MOVES = 0;
    public static final int TAB_PRECIOUS_ITEMS = 1;
    public static final int TAB_PRECIOUS_CATEGORY = 2;

    public PreciousTrackerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
        case TAB_PRECIOUS_MOVES:
            return new PreciousMovesFragment();
        case TAB_PRECIOUS_ITEMS:
            return new PreciousItemsFragment();
        case TAB_PRECIOUS_CATEGORY:
            return new PreciousCategoryFragment();
        default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

}

package com.maxsoft.precioustracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PreciousTrackerPagerAdapter extends FragmentPagerAdapter {

	public static final int TAB_COUNT = 3;
	public static final int TAB_PRECIOUS_MOVES = 0;
	public static final int TAB_PRECIOUS_ITEMS = 1;
	public static final int TAB_PRECIOUS_CATEGORY = 2;

	private PreciousMovesFragment movesFragment;
	private PreciousItemsFragment itemsFragment;
	private PreciousCategoryFragment categoryFragment;

	public PreciousTrackerPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case TAB_PRECIOUS_MOVES:
			if (movesFragment == null) {
				movesFragment = new PreciousMovesFragment();
			}
			return movesFragment;
		case TAB_PRECIOUS_ITEMS:
			if (itemsFragment == null) {
				itemsFragment = new PreciousItemsFragment();
			}
			return itemsFragment;
		case TAB_PRECIOUS_CATEGORY:
			if (categoryFragment == null) {
				categoryFragment = new PreciousCategoryFragment();
			}
			return categoryFragment;
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}

}

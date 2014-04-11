package com.maxsoft.precioustracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maxsoft.precioustracker.model.PreciousTrackerModel;

public class PreciousTrackerPagerAdapter extends FragmentPagerAdapter {

	public static final int TAB_COUNT = 2;
	public static final int TAB_PRECIOUS_MOVES = 0;
	public static final int TAB_PRECIOUS_ITEMS = 1;

	private PreciousMovesFragment movesFragment;
	private PreciousItemsFragment itemsFragment;

	public PreciousTrackerPagerAdapter(FragmentManager fm, PreciousTrackerModel model) {
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
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}

	public void setUpdate(boolean update) {
		if (movesFragment == null) {
			movesFragment = new PreciousMovesFragment();
		}
		movesFragment.setUpdate(update);
	}

}

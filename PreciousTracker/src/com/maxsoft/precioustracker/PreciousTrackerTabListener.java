package com.maxsoft.precioustracker;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class PreciousTrackerTabListener<T extends Fragment> implements TabListener {

	private Fragment fragment;
	private Activity activity;
	private String tag;
	private Class<T> clz;

	public PreciousTrackerTabListener(Activity activity, String tag, Class<T> clz) {
		this.activity = activity;
		this.tag = tag;
		this.clz = clz;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Check if the fragment is already initialized
		if (fragment == null) {
			// If not, instantiate and add it to the activity
			fragment = Fragment.instantiate(activity, clz.getName());
			ft.add(android.R.id.content, fragment, tag);
		} else {
			// If it exists, simply attach it in order to show it
			ft.attach(fragment);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (fragment != null) {
			// Detach the fragment, because another one is being attached
			ft.detach(fragment);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User selected the already selected tab. Usually do nothing.
	}

}

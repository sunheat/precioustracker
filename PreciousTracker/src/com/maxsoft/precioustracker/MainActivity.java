package com.maxsoft.precioustracker;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.maxsoft.precioustracker.model.PreciousTrackerModel;

public class MainActivity extends FragmentActivity {

	public static final String INTENT_MSG_UPDATE = "intent.msg.update";

	private PreciousTrackerModel model;

	private PreciousTrackerPagerAdapter pagerAdapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		model = PreciousTrackerModel.getInstance(this);

		pagerAdapter = new PreciousTrackerPagerAdapter(getSupportFragmentManager(), model);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			public void onPageSelected(int position) {
				// When swiping between pages, select the
				// corresponding tab.
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		// enable tabs in action bar
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// adds tab listeners
		TabListener tabListener = new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};

		// add tabs with listener
		actionBar.addTab(actionBar.newTab().setText(R.string.moves).setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText(R.string.items).setTabListener(tabListener));

	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null) {
			boolean update = intent.getExtras().getBoolean(INTENT_MSG_UPDATE);
			pagerAdapter.setUpdate(update);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:
			switch (viewPager.getCurrentItem()) {
			case PreciousTrackerPagerAdapter.TAB_PRECIOUS_MOVES:
				Intent intent = new Intent(getBaseContext(), AddMoveActivity.class);
				startActivity(intent);
				return true;
			case PreciousTrackerPagerAdapter.TAB_PRECIOUS_ITEMS:
				return true;
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

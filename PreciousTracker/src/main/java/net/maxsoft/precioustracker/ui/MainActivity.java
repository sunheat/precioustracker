package net.maxsoft.precioustracker.ui;

import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.PreciousTrackerModel;
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

public class MainActivity extends FragmentActivity implements TabListener {

	private PreciousTrackerModel model;

	private PreciousTrackerPagerAdapter pagerAdapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		model = PreciousTrackerModel.getInstance(this);

		pagerAdapter = new PreciousTrackerPagerAdapter(getSupportFragmentManager());
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

		// add tabs with listener
		actionBar.addTab(actionBar.newTab().setText(R.string.moves).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.items).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.category).setTabListener(this));
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
				Intent intentMove = new Intent(getBaseContext(), AddMoveActivity.class);
				startActivityForResult(intentMove, PreciousTrackerModel.REQ_CODE_ADD_MOVE);
				return true;
			case PreciousTrackerPagerAdapter.TAB_PRECIOUS_ITEMS:
				Intent intentItem = new Intent(getBaseContext(), CreateItemActivity.class);
				startActivityForResult(intentItem, PreciousTrackerModel.REQ_CODE_CREATE_ITEM);
				return true;
			case PreciousTrackerPagerAdapter.TAB_PRECIOUS_CATEGORY:
				Intent intentCategory = new Intent(getBaseContext(), CreateCategoryActivity.class);
				startActivityForResult(intentCategory, PreciousTrackerModel.REQ_CODE_CREATE_CATEGORY);
				return true;
			}
		case R.id.refresh:
			switch (viewPager.getCurrentItem()) {
			case PreciousTrackerPagerAdapter.TAB_PRECIOUS_MOVES:
				model.broadcast(PreciousTrackerModel.INTENT_MSG_REFRESH_MOVE_LIST);
				return true;
			case PreciousTrackerPagerAdapter.TAB_PRECIOUS_ITEMS:
				return true;
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PreciousTrackerModel.REQ_CODE_ADD_MOVE:
				model.broadcast(PreciousTrackerModel.INTENT_MSG_REFRESH_MOVE_LIST);
			case PreciousTrackerModel.REQ_CODE_CREATE_ITEM:
				model.broadcast(PreciousTrackerModel.INTENT_MSG_REFRESH_ITEM_LIST);
			case PreciousTrackerModel.REQ_CODE_CREATE_CATEGORY:
				model.broadcast(PreciousTrackerModel.INTENT_MSG_REFRESH_CATEGORY_LIST);
			}
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// no logic here
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// no logic here
	}

}

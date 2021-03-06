package net.maxsoft.precioustracker.ui;

import android.content.Intent;
import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.PreciousTrackerModel;
import net.maxsoft.precioustracker.model.dao.PreciousCategory;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * The CreateCategoryActivity used for creating category records.
 * 
 * @author Max
 * 
 */
public class CreateCategoryActivity extends Activity {

	private PreciousTrackerModel model;
	private PreciousCategory newCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_category);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new CreateCategoryFragment()).commit();
		}

		// enable up button in action bar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (model == null) {
			model = PreciousTrackerModel.getInstance(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_category, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Handles cancel button click. */
	public void onCancel(View v) {
		finish();
	}

	/** Handles save button click. */
	public void onSave(View v) {
		// make sure newCategory isn't null
		getPreciousCategory();
		String categroyName = ((TextView) findViewById(R.id.txtCategoryName)).getText().toString();
		newCategory.setName(categroyName);
		model.insertNewCategory(newCategory);

        // return the result
        Intent returnIntent = new Intent();
        returnIntent.putExtra(PreciousTrackerModel.EXTRA_KEY_NEW_CATEGORY_ID, newCategory.getId());
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	/**
	 * Used to initialize the new PreciousCategory object.
	 * 
	 * @return the PreciousCategory object representing the new database record
	 */
	private PreciousCategory getPreciousCategory() {
		if (newCategory == null) {
			newCategory = new PreciousCategory();
		}
		return newCategory;
	}

	public static class CreateCategoryFragment extends Fragment {

		public CreateCategoryFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_create_category, container, false);
			return rootView;
		}
	}

}

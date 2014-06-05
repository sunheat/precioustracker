package com.maxsoft.precioustracker;

import java.io.File;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.maxsoft.precioustracker.model.PreciousItem;
import com.maxsoft.precioustracker.model.PreciousTrackerModel;

/**
 * The CreateItemActivity used for creating item records.
 * 
 * @author Max
 * 
 */
public class CreateItemActivity extends Activity implements OnItemSelectedListener {

	private PreciousTrackerModel model;
	private PreciousItem newItem;
	private List<PreciousCategory> categoryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_item);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new CreateItemFragment()).commit();
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

		populateCategoryList();
	}

	/**
	 * Populates the category spinner.
	 */
	private void populateCategoryList() {
		// get the category list from the database
		categoryList = model.getCategoryList();
		// add an item for triggering the new category creation activity
		categoryList.add(getNewCategory());

		// using ArrayAdapter to display spinner items
		ArrayAdapter<PreciousCategory> adapter = new ArrayAdapter<PreciousCategory>(this, android.R.layout.simple_spinner_item, categoryList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner lstCategory = (Spinner) findViewById(R.id.lstCategory);
		lstCategory.setAdapter(adapter);
		lstCategory.setOnItemSelectedListener(this);
	}

	/**
	 * Returns a category item for the spinner that can be used to trigger new
	 * category creation.
	 * 
	 * @return
	 */
	private PreciousCategory getNewCategory() {
		PreciousCategory newCategory = new PreciousCategory();
		newCategory.setName(getResources().getString(R.string.createNewCategory));
		// sets a special ID to represent an yet to be created new category
		newCategory.set_id(PreciousTrackerModel.CREATE_NEW_CATEGORY_ID);
		return newCategory;
	}

	/**
	 * Initializes and returns the PreciousItem object that represents the
	 * precious item database record to be created.
	 * 
	 * @return the PreciousItem object representing the precious item database
	 *         record to be created
	 */
	private PreciousItem getPreciousItem() {
		if (newItem == null) {
			newItem = new PreciousItem();
		}
		return newItem;
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

	/** handles cancel button click */
	public void onCancel(View v) {
		finish();
	}

	/** handles save button click */
	public void onSave(View v) {
		String itemName = ((TextView) findViewById(R.id.txtItemName)).getText().toString();
		String itemLoc = ((TextView) findViewById(R.id.txtLocation)).getText().toString();

		// make sure newItem isn't null
		getPreciousItem();
		newItem.setName(itemName);
		newItem.setLocation(itemLoc);
		model.insertNewItem(newItem);
		setResult(RESULT_OK);

		finish();
	}

	public static class CreateItemFragment extends Fragment {

		public CreateItemFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_create_item, container, false);
			return rootView;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		PreciousCategory category = categoryList.get(position);
		if (category.get_id() == PreciousTrackerModel.CREATE_NEW_CATEGORY_ID) {
			// special item selected. trigger the CreateCategoryActivity
			Intent intent = new Intent(this, CreateCategoryActivity.class);
			startActivityForResult(intent, PreciousTrackerModel.REQ_CODE_CREATE_CATEGORY);
		} else {
			// sets the selected category on the new PreciousItem object
			getPreciousItem();
			newItem.setCategoryId(category.get_id());
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// no logic here
	}
	
	/**
     * Handles portrait taking action.
     * 
     * @param v
     */
    public void onTakePortrait(View v) {
        String portraitFilePath = model.getOutputMediaFilePath();
        getPreciousItem().setPhotoFilePath(portraitFilePath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(portraitFilePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, PreciousTrackerModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        // respond to image capture results
        case PreciousTrackerModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
            if (resultCode == RESULT_OK) {
                // make sure the item object isn't null
                getPreciousItem();

                // prepare the Uri object to use with ImageView
                String photoFilePath = newItem.getPhotoFilePath();
                File file = new File(photoFilePath);
                Uri imageUri = Uri.fromFile(file);

                ImageView imgPortrait = (ImageView) findViewById(R.id.imgPortrait);
                imgPortrait.setImageURI(imageUri);

                newItem.setPhotoFilePath(photoFilePath);
            }
            break;
        // respond to new category creation results
        case PreciousTrackerModel.REQ_CODE_CREATE_CATEGORY:
            if (resultCode == RESULT_OK) {
                // make sure the item object isn't null
                getPreciousItem();
                // get the newly created item ID from intent's extras
                Bundle extras = data.getExtras();
                long categoryId = extras.getLong(PreciousTrackerModel.EXTRA_KEY_NEW_CATEGORY_ID);
                newItem.setCategoryId(categoryId);

                // refreshes the item list
                populateCategoryList();
            }
            break;
        }
    }

}

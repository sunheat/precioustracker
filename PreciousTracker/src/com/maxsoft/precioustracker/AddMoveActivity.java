package com.maxsoft.precioustracker;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.maxsoft.precioustracker.model.PreciousItem;
import com.maxsoft.precioustracker.model.PreciousMove;
import com.maxsoft.precioustracker.model.PreciousTrackerModel;

/**
 * The AddMoveActivity used for creating move records for a precious item.
 * 
 * @author Max
 * 
 */
public class AddMoveActivity extends Activity implements OnItemSelectedListener {

	private PreciousTrackerModel model;
	private PreciousMove newMove;
	private List<PreciousItem> itemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_move);

		// enable up button in action bar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// disable editing for the data and time fields. using date and time
		// picker to set date/time.
		EditText txtDateMoved = (EditText) findViewById(R.id.txtDateMoved);
		txtDateMoved.setKeyListener(null);
		EditText txtTimeMoved = (EditText) findViewById(R.id.txtTimeMoved);
		txtTimeMoved.setKeyListener(null);
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (model == null) {
			model = PreciousTrackerModel.getInstance(this);
		}

		populateItemList();

		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		DateFormat formatter = SimpleDateFormat.getDateInstance();
		String formattedDate = formatter.format(c.getTime());
		EditText txtDateMoved = (EditText) findViewById(R.id.txtDateMoved);
		txtDateMoved.setText(formattedDate);
		formatter = SimpleDateFormat.getTimeInstance();
		String formattedTime = formatter.format(c.getTime());
		EditText txtTimeMoved = (EditText) findViewById(R.id.txtTimeMoved);
		txtTimeMoved.setText(formattedTime);
	}

	/**
	 * Populates the item spinner
	 */
	private void populateItemList() {
		// get the item list from the database
		itemList = model.getItemList();
		// add an item for triggering new new item creation activity
		itemList.add(getNewItem());
		// using ArrayAdapter to display items
		ArrayAdapter<PreciousItem> adapter = new ArrayAdapter<PreciousItem>(this, android.R.layout.simple_spinner_item, itemList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner lstItem = (Spinner) findViewById(R.id.lstItem);
		lstItem.setAdapter(adapter);
		// sets item selection listener
		lstItem.setOnItemSelectedListener(this);
	}

	/**
	 * returns an item for the drop down list that can be used to create a new
	 * PreciousItem when clicked
	 */
	private PreciousItem getNewItem() {
		PreciousItem newItem = new PreciousItem();
		newItem.setName(getResources().getString(R.string.createNewItem));
		// sets a special ID to represent an uncreated new item
		newItem.set_id(PreciousTrackerModel.CREATE_NEW_ITEM_ID);
		return newItem;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// respond to image capture results
		case PreciousTrackerModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				// make sure the item object isn't null
				getPreciousMove();

				// prepare the Uri object to use with ImageView
				String snapshotPath = newMove.getSnapshot();
				File file = new File(snapshotPath);
				Uri imageUri = Uri.fromFile(file);

				ImageView imgSnapshot = (ImageView) findViewById(R.id.imgSnapshot);
				imgSnapshot.setImageURI(imageUri);

				newMove.setSnapshot(snapshotPath);
			}
			break;
		case PreciousTrackerModel.REQ_CODE_CREATE_ITEM:
			if (resultCode == RESULT_OK) {
				// make sure the item object isn't null
				getPreciousMove();
				// get the newly created item ID from intent's extras
				Bundle extras = data.getExtras();
				long itemId = extras.getLong(PreciousTrackerModel.EXTRA_KEY_NEW_ITEM_ID);
				newMove.setItemId(itemId);

				// refreshes the item list
				populateItemList();
			}
			break;
		}
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	/**
	 * Handles taking snapshot action.
	 * 
	 * @param v
	 */
	public void onSnapshot(View v) {
		getPreciousMove().setSnapshot(getOutputMediaFilePath().toString());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(getOutputMediaFilePath());
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent, PreciousTrackerModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	/**
	 * Handles save button click.
	 * 
	 * @param v
	 */
	public void onSave(View v) {
		String fromWhere = ((TextView) findViewById(R.id.txtFromWhere)).getText().toString();
		String toWhere = ((TextView) findViewById(R.id.txtToWhere)).getText().toString();
		String dateString = ((TextView) findViewById(R.id.txtDateMoved)).getText().toString();
		String timeString = ((TextView) findViewById(R.id.txtTimeMoved)).getText().toString();
		Date dateMoved = null;
		try {
			dateMoved = SimpleDateFormat.getDateInstance().parse(dateString);
			Calendar dateCalendar = Calendar.getInstance();
			dateCalendar.setTime(dateMoved);
			Date timeMoved = SimpleDateFormat.getTimeInstance().parse(timeString);
			Calendar timeCalendar = Calendar.getInstance();
			timeCalendar.setTime(timeMoved);
			dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
			dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
			dateMoved = dateCalendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		getPreciousMove();
		newMove.setFromWhere(fromWhere);
		newMove.setToWhere(toWhere);
		newMove.setDateMoved(dateMoved);

		model.insertNewMove(newMove);
		setResult(RESULT_OK);
		finish();
	}

	/**
	 * Handles cancel button click.
	 * 
	 * @param v
	 */
	public void onCancel(View v) {
		setResult(RESULT_CANCELED);
		finish();
	}

	@SuppressLint("SimpleDateFormat")
	/**
	 * Gets the snapshot picture file path.
	 * @return the file path of the snapshot taken.
	 */
	private String getOutputMediaFilePath() {
		// checks whether external storage is mounted
		String sdState = Environment.getExternalStorageState();
		if (sdState.equals(Environment.MEDIA_MOUNTED)) {
			// if mounted, use external storage for storing snapshots
			File mediaDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PreciousTrackerModel.PHOTO_DIR);
			// checks to see if the directory exists for this app
			if (!mediaDir.exists()) {
				boolean s = mediaDir.mkdirs();
				if (!s) {
					Log.e(PreciousTrackerModel.PHOTO_DIR, "failed to create dir");
					return null;
				}
			}
			DateFormat formatter = new SimpleDateFormat(PreciousTrackerModel.IMG_FORMAT_STRING);
			String timeStamp = formatter.format(new Date());
			File mediaFile = new File(mediaDir.getPath() + File.separator + timeStamp + ".jpg");
			return mediaFile.getPath();
		} else {
			// else, use internal storage
			// TODO location of snapshot using internal storage
			return null;
		}
	}

	/**
	 * Initializes and returns the PreciousMove object that represents the
	 * precious move database record to be created.
	 * 
	 * @return the PreciousMove objects representing the precious move database
	 *         record to be created
	 */
	private PreciousMove getPreciousMove() {
		if (newMove == null) {
			newMove = new PreciousMove();
		}
		return newMove;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		PreciousItem item = itemList.get(position);
		if (item.get_id() == PreciousTrackerModel.CREATE_NEW_ITEM_ID) {
			// launch the CreateItemActivity to create a new item
			Intent intent = new Intent(this, CreateItemActivity.class);
			startActivityForResult(intent, PreciousTrackerModel.REQ_CODE_CREATE_ITEM);
		} else {
			// set the selected item to the new move record
			getPreciousMove();
			newMove.setItemId(item.get_id());
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// no logic here
	}

}

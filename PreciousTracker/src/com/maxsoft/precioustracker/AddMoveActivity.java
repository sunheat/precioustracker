package com.maxsoft.precioustracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class AddMoveActivity extends Activity implements OnItemSelectedListener {

	private PreciousTrackerModel model;
	private PreciousMove newMove;
	private List<PreciousItem> itemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_move);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

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

		itemList = model.getItemList();
		ArrayAdapter<PreciousItem> adapter = new ArrayAdapter<PreciousItem>(this, android.R.layout.simple_spinner_item, itemList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner lstItem = (Spinner) findViewById(R.id.lstItem);
		lstItem.setAdapter(adapter);
		lstItem.setOnItemSelectedListener(this);

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PreciousTrackerModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				getPreciousMove();
				String snapshotPath = newMove.getSnapshot();
				File file = new File(snapshotPath);
				Uri imageUri = Uri.fromFile(file);

				ImageView imgSnapshot = (ImageView) findViewById(R.id.imgSnapshot);
				imgSnapshot.setImageURI(imageUri);

				newMove.setSnapshot(snapshotPath);
			} else {
				getPreciousMove();
				newMove.setSnapshot(null);
			}
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

	public void onSnapshot(View v) {
		getPreciousMove().setSnapshot(getOutputMediaFilePath().toString());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(getOutputMediaFilePath());
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent, PreciousTrackerModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	public void onSave(View v) {
		String fromWhere = ((TextView) findViewById(R.id.txtFromWhere)).getText().toString();
		String toWhere = ((TextView) findViewById(R.id.txtToWhere)).getText().toString();
		String dateString = ((TextView) findViewById(R.id.txtDateMoved)).getText().toString();
		Date dateMoved = null;
		try {
			dateMoved = SimpleDateFormat.getDateInstance().parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		getPreciousMove();
		newMove.setFromWhere(fromWhere);
		newMove.setToWhere(toWhere);
		newMove.setDateMoved(dateMoved);

		model.insertNewMove(newMove);
		finishActivity(PreciousTrackerModel.REQ_CODE_ADD_MOVE);
	}

	public void onCancel(View v) {
		finish();
	}

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
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String timeStamp = formatter.format(new Date());
			File mediaFile = new File(mediaDir.getPath() + File.separator + timeStamp + ".jpg");
			return mediaFile.getPath();
		} else {
			// else, use internal storage
			// TODO location of snapshot using internal storage
			return null;
		}
	}

	private PreciousMove getPreciousMove() {
		if (newMove == null) {
			newMove = new PreciousMove();
		}
		return newMove;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		PreciousItem item = itemList.get(position);
		getPreciousMove();
		newMove.setItemId(item.get_id());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}

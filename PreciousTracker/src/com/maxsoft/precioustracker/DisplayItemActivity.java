package com.maxsoft.precioustracker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxsoft.precioustracker.model.PreciousItem;

public class DisplayItemActivity extends Activity {

	// the item to display
	private PreciousItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_item);

		// set up back button on action bar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	protected void onStart() {
		super.onStart();

		// find views to display information
		TextView txtItemName = (TextView) findViewById(R.id.txtItemName);
		TextView txtDateCreated = (TextView) findViewById(R.id.txtDateCreated);
		TextView txtCategory = (TextView) findViewById(R.id.txtCategory);
		TextView txtLocation = (TextView) findViewById(R.id.txtLocation);
		//TODO ImageView imgSnapshot = (ImageView) findViewById(R.id.imgSnapshot);
		ImageView imgItemPhoto = (ImageView) findViewById(R.id.imgItemPhoto);

		// get the item object from intent extras
		Intent intent = getIntent();
		item = (PreciousItem) intent.getSerializableExtra(PreciousItem.INTENT_MESSAGE);

		txtItemName.setText(item.getName());
		txtCategory.setText(item.getCategoryName());
		txtLocation.setText(item.getLocation());

		// format the date
		Date dateCreated = item.getDateCreated();
		String formatedDate = SimpleDateFormat.getDateTimeInstance().format(dateCreated);
		txtDateCreated.setText(formatedDate);

		// display the photo if it's not null
		String photoFilePath = item.getPhotoFilePath();
		if (photoFilePath != null) {
			File file = new File(photoFilePath);
			Uri snapshotUri = Uri.fromFile(file);
			imgItemPhoto.setImageURI(snapshotUri);
		}
		// String snapshotFilePath = item.getSnapshot();
		// if (snapshotFilePath != null) {
		// File file = new File(snapshotFilePath);
		// Uri snapshotUri = Uri.fromFile(file);
		// imgSnapshot.setImageURI(snapshotUri);
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.display_move, menu);
		return true;
	}

}

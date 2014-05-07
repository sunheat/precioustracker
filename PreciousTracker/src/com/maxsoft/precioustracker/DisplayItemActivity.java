package com.maxsoft.precioustracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxsoft.precioustracker.model.PreciousItem;

public class DisplayItemActivity extends Activity {

	private PreciousItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_item);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	protected void onStart() {
		super.onStart();

		TextView txtItemName = (TextView) findViewById(R.id.txtItemName);
		TextView txtDateMoved = (TextView) findViewById(R.id.txtDateCreated);
		TextView txtCategory = (TextView) findViewById(R.id.txtCategory);
		TextView txtLocation = (TextView) findViewById(R.id.txtLocation);
		ImageView imgSnapshot = (ImageView) findViewById(R.id.imgSnapshot);
		ImageView imgItemPhoto = (ImageView) findViewById(R.id.imgItemPhoto);

		Intent intent = getIntent();
		item = (PreciousItem) intent.getSerializableExtra(PreciousItem.INTENT_MESSAGE);
		txtItemName.setText(item.getName());
		// format the date
		//Date dateMoved = move.getDateMoved();
		//String formatedDate = SimpleDateFormat.getDateTimeInstance().format(dateMoved);
		//txtDateMoved.setText(formatedDate);
		txtCategory.setText(item.getCategoryName());
		txtLocation.setText(item.getLocation());
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

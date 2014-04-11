package com.maxsoft.precioustracker;

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

import com.maxsoft.precioustracker.model.PreciousMove;

public class DisplayMoveActivity extends Activity {

	private PreciousMove move;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_move);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	protected void onStart() {
		super.onStart();
		Intent intent = getIntent();
		TextView txtItemName = (TextView) findViewById(R.id.txtItemName);
		TextView txtDateMoved = (TextView) findViewById(R.id.txtDateMoved);
		TextView txtFromWhere = (TextView) findViewById(R.id.txtFromWhere);
		TextView txtToWhere = (TextView) findViewById(R.id.txtToWhere);
		ImageView imgSnapshot = (ImageView) findViewById(R.id.imgSnapshot);
		move = (PreciousMove) intent.getSerializableExtra(PreciousMove.INTENT_MESSAGE);
		txtItemName.setText(move.getItemName());
		// format the date
		Date dateMoved = move.getDateMoved();
		String formatedDate = SimpleDateFormat.getDateTimeInstance().format(dateMoved);
		txtDateMoved.setText(formatedDate);
		txtFromWhere.setText(move.getFromWhere());
		txtToWhere.setText(move.getToWhere());
		String snapshotFilePath = move.getSnapshot();
		Uri snapshotUri = Uri.parse(snapshotFilePath);
		imgSnapshot.setImageURI(snapshotUri);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.display_move, menu);
		return true;
	}

}

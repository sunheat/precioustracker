package net.maxsoft.precioustracker.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.PreciousMove;
import net.maxsoft.precioustracker.model.PreciousTrackerModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * DisplayMoveActivity for displaying details of an item move record.
 * 
 * @author Max
 */
public class DisplayMoveActivity extends Activity {

    // the PreciousMove to be displayed
    private PreciousMove move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_move);

        // setting up the back function in the action bar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void onStart() {
        super.onStart();

        TextView txtItemName = (TextView) findViewById(R.id.txtItemName);
        TextView txtDateMoved = (TextView) findViewById(R.id.txtDateMoved);
        TextView txtFromWhere = (TextView) findViewById(R.id.txtFromWhere);
        TextView txtToWhere = (TextView) findViewById(R.id.txtToWhere);
        ImageView imgSnapshot = (ImageView) findViewById(R.id.imgSnapshot);

        // retrieve the PreciousMove object to be displayed from the intent
        Intent intent = getIntent();
        move = (PreciousMove) intent.getSerializableExtra(PreciousMove.INTENT_MESSAGE);

        txtItemName.setText(move.getItemName());

        // format the date
        Date dateMoved = move.getDateMoved();
        String formatedDate = SimpleDateFormat.getDateTimeInstance().format(dateMoved);

        txtDateMoved.setText(formatedDate);
        txtFromWhere.setText(move.getFromWhere());
        txtToWhere.setText(move.getToWhere());
        // get the snapshot file path
        String snapshotFilePath = move.getSnapshot();
        if (snapshotFilePath != null) {
            File file = new File(snapshotFilePath);
            Uri snapshotUri = Uri.fromFile(file);
            imgSnapshot.setImageURI(snapshotUri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.display_move, menu);
        return true;
    }

    /**
     * Called when the image view showing the snapshot is clicked. Displays the
     * snapshot photo using full screen.
     * 
     * @param v
     */
    public void onSnapshotClicked(View v) {
        if (move.getSnapshot() != null) {
            Intent intent = new Intent(getBaseContext(), ShowPhotoActivity.class);
            intent.putExtra(PreciousTrackerModel.EXTRA_PHOTO_FILE_PATH, move.getSnapshot());
            startActivity(intent);
        }
    }

}

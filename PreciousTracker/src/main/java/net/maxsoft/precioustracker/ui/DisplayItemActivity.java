package net.maxsoft.precioustracker.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.PreciousTrackerModel;
import net.maxsoft.precioustracker.model.dao.PreciousCategory;
import net.maxsoft.precioustracker.model.dao.PreciousItem;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayItemActivity extends Activity {

    // the item to display
    private PreciousItem item;
    private PreciousTrackerModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);
        
        model = PreciousTrackerModel.getInstance(getParent());

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
        ImageView imgItemPhoto = (ImageView) findViewById(R.id.imgItemPhoto);

        // get the item object from intent extras
        Intent intent = getIntent();
        item = (PreciousItem) intent.getSerializableExtra(PreciousItem.INTENT_MESSAGE);
        Long catId = item.getCategory();
        PreciousCategory category = model.getCategory(catId);

        txtItemName.setText(item.getName());
        txtCategory.setText(category.getName());
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.display_move, menu);
        return true;
    }

    /**
     * Called when the image view showing the item portrait is clicked. Displays
     * the photo full screen.
     * 
     * @param v
     */
    public void onPhotoClicked(View v) {
        String photoFilePath = item.getPhotoFilePath();
        if (photoFilePath != null) {
            Intent intent = new Intent(getBaseContext(), ShowPhotoActivity.class);
            intent.putExtra(PreciousTrackerModel.EXTRA_PHOTO_FILE_PATH, photoFilePath);
            startActivity(intent);
        }
    }

}

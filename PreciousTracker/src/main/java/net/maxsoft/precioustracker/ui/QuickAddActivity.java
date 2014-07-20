package net.maxsoft.precioustracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import net.maxsoft.precioustracker.model.PreciousTrackerModel;
import net.maxsoft.precioustracker.model.dao.PreciousQuickAdd;

import java.io.File;
import java.util.Date;

import static net.maxsoft.precioustracker.model.PreciousTrackerModel.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE;
import static net.maxsoft.precioustracker.model.PreciousTrackerModel.INTENT_MSG_QUICK_ADD_ID;

/**
 * Quickly add a snapshot (of a new item or a move) for later process.
 * <p/>
 * Created by Max Yang on 14-7-19.
 */
public class QuickAddActivity extends Activity {

    private PreciousTrackerModel model;
    private String photoFilePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        if (model == null) {
            model = PreciousTrackerModel.getInstance(this);
        }

        photoFilePath = model.getOutputMediaFilePath();
        File file = new File(photoFilePath);

        Intent snapshotIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        snapshotIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(snapshotIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            PreciousQuickAdd quickAdd = new PreciousQuickAdd();
            quickAdd.setDateTaken(new Date());
            quickAdd.setPhotoFilePath(photoFilePath);
            long id = model.insertNewQuickAdd(quickAdd);
            Intent editIntent = new Intent(this, EditQuickAddActivity.class);
            editIntent.putExtra(INTENT_MSG_QUICK_ADD_ID, id);
            startActivity(editIntent);
        }
    }

}
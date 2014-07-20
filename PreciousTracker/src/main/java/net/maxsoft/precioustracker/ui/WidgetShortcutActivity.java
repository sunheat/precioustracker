package net.maxsoft.precioustracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import net.maxsoft.precioustracker.R;

/**
 * Used for creating a shortcut widget to {@link QuickAddActivity}
 * <p/>
 * Created by Max Yang on 14-7-19.
 */
public class WidgetShortcutActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // intent to launch target activity
        Intent quickAddIntent = new Intent(this, QuickAddActivity.class);

        // intent to create the shortcut
        Intent shortcutIntent = new Intent();
        // target intent of the shortcut
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, quickAddIntent);
        // label of the shortcut
        String name = getResources().getString(R.string.quick_snapshot);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // icon
        Intent.ShortcutIconResource iconResource = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

        // trigger shortcut creation
        setResult(RESULT_OK, shortcutIntent);

        finish();
        super.onCreate(savedInstanceState);
    }
}
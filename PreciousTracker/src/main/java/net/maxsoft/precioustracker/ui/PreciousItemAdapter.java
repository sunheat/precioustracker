package net.maxsoft.precioustracker.ui;

import java.io.File;
import java.util.List;

import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.dao.PreciousItem;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PreciousItemAdapter extends BaseAdapter {

    private List<PreciousItem> data;

    public PreciousItemAdapter(List<PreciousItem> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflator.inflate(R.layout.list_view_item_item, null, false);
        TextView txtItemName = (TextView) convertView.findViewById(R.id.itemListTxtName);
        TextView txtItemLoc = (TextView) convertView.findViewById(R.id.itemListTxtLoc);

        PreciousItem item = data.get(position);
        txtItemName.setText(item.getName());
        txtItemLoc.setText(item.getLocation());
        String photoFilePath = item.getPhotoFilePath();
        if (photoFilePath != null) {
            ImageView imgPortrait = (ImageView) convertView.findViewById(R.id.itemListImg);
            imgPortrait.setImageURI(Uri.fromFile(new File(photoFilePath)));
        }
        return convertView;
    }

}

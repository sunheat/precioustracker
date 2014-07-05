package net.maxsoft.precioustracker.ui;

import java.io.File;
import java.util.List;

import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.dao.PreciousMove;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PreciousMoveAdapter extends BaseAdapter {

    private List<PreciousMove> data;

    public PreciousMoveAdapter(List<PreciousMove> data) {
        super();
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
        convertView = inflator.inflate(R.layout.list_view_item_moves, parent, false);

        // look up views
        TextView txtItemName = (TextView) convertView.findViewById(R.id.movesListTxtItem);
        TextView txtItemLoc = (TextView) convertView.findViewById(R.id.movesListTxtLoc);
        ImageView imgSnapshot = (ImageView) convertView.findViewById(R.id.movesListImg);

        PreciousMove item = data.get(position);
        // TODO txtItemName.setText(item.getItemName());
        txtItemLoc.setText(item.getTo_where());
        // set the snapshot image if not null
        String snapshotFilePath = item.getSnapshot();
        if (snapshotFilePath != null) {
            File file = new File(snapshotFilePath);
            Uri uri = Uri.fromFile(file);
            imgSnapshot.setImageURI(uri);
        }

        return convertView;
    }

}

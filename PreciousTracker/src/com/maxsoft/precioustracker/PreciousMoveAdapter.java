package com.maxsoft.precioustracker;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxsoft.precioustracker.model.PreciousMove;

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
		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(R.layout.list_view_item_moves, parent, false);
		}
		TextView txtItemName = (TextView) convertView.findViewById(R.id.movesListTxtItem);
		TextView txtItemLoc = (TextView) convertView.findViewById(R.id.movesListTxtLoc);
		PreciousMove item = data.get(position);
		txtItemName.setText(item.getItemName());
		txtItemLoc.setText(item.getToWhere());
		return convertView;
	}

}

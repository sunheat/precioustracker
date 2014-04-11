package com.maxsoft.precioustracker;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.maxsoft.precioustracker.model.PreciousItem;
import com.maxsoft.precioustracker.model.PreciousTrackerModel;

public class PreciousItemsFragment extends Fragment {

	private List<PreciousItem> itemList;
	private ViewGroup parent;
	private View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.parent = container;

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.item_list, container, false);
		}

		return rootView;
	}

	public void onStart() {
		super.onStart();

		if (itemList == null) {
			PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
			itemList = model.getItemList();
		}

		PreciousItemAdapter adapter = new PreciousItemAdapter(parent, itemList);
		ListView listView = (ListView) parent.findViewById(R.id.itemListView);
		listView.setAdapter(adapter);
	}

}

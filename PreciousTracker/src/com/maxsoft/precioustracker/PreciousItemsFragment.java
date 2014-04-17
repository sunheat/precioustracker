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

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.parent = container;
		return inflater.inflate(R.layout.item_list, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		refreshList();
	}

	private void refreshList() {
		PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
		itemList = model.getItemList();
		PreciousItemAdapter adapter = new PreciousItemAdapter(itemList);
		ListView listView = (ListView) parent.findViewById(R.id.itemListView);
		listView.setAdapter(adapter);
	}

}

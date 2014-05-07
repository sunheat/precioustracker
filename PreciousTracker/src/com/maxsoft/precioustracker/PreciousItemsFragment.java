package com.maxsoft.precioustracker;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.maxsoft.precioustracker.model.PreciousItem;
import com.maxsoft.precioustracker.model.PreciousTrackerModel;

public class PreciousItemsFragment extends Fragment {

	private List<PreciousItem> itemList;
	private ViewGroup parent;
	private PreciousItemListBroadcastReceiver receiver;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.parent = container;

		if (receiver == null) {
			receiver = new PreciousItemListBroadcastReceiver();
			PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
			model.registerBroadcastReceiver(receiver, PreciousTrackerModel.INTENT_MSG_REFRESH_ITEM_LIST);
		}

		return inflater.inflate(R.layout.item_list, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		refreshList();

		// add the item click listener
		ListView itemListView = (ListView) parent.findViewById(R.id.itemListView);
		itemListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long rowId) {
				PreciousItemsFragment.this.onItemClick(position);
			}
		});
	}

	private void onItemClick(int position) {
		Intent intent = new Intent(parent.getContext(), DisplayItemActivity.class);
		PreciousItem item = itemList.get(position);
		intent.putExtra(PreciousItem.INTENT_MESSAGE, item);
		startActivity(intent);
	}

	private void refreshList() {
		PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
		itemList = model.getItemList();
		PreciousItemAdapter adapter = new PreciousItemAdapter(itemList);
		ListView listView = (ListView) parent.findViewById(R.id.itemListView);
		listView.setAdapter(adapter);
	}

	public class PreciousItemListBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			refreshList();
		}
	}

}

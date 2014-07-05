package net.maxsoft.precioustracker.ui;

import java.util.List;

import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.PreciousTrackerModel;
import net.maxsoft.precioustracker.model.dao.PreciousItem;
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

public class PreciousItemsFragment extends Fragment {

	private List<PreciousItem> itemList;
	private PreciousItemListBroadcastReceiver receiver;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		ListView itemListView = (ListView) getActivity().findViewById(R.id.itemListView);
		itemListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long rowId) {
				PreciousItemsFragment.this.onItemClick(position);
			}
		});
	}

	private void onItemClick(int position) {
	    PreciousItem item = itemList.get(position);
		Intent intent = new Intent(getActivity().getBaseContext(), DisplayItemActivity.class);
		intent.putExtra(PreciousItem.INTENT_MESSAGE, item);
		startActivity(intent);
	}

	private void refreshList() {
		PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
		itemList = model.getItemList();
		PreciousItemAdapter adapter = new PreciousItemAdapter(itemList);
		ListView listView = (ListView) getActivity().findViewById(R.id.itemListView);
		listView.setAdapter(adapter);
	}

	public class PreciousItemListBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			refreshList();
		}
	}

}

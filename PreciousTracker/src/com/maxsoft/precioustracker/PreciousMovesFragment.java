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

import com.maxsoft.precioustracker.model.PreciousMove;
import com.maxsoft.precioustracker.model.PreciousTrackerModel;

public final class PreciousMovesFragment extends Fragment {

	private List<PreciousMove> recentMoveList;
	private PreciousMoveAdapter movesListAdapter;
	private ViewGroup parent;
	private View rootView;
	private Context context;
	private RefreshMoveListBroadcastReceiver receiver;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		this.parent = container;
		this.context = parent.getContext();

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.recent_moves, container, false);
		}

		if (receiver == null) {
			receiver = new RefreshMoveListBroadcastReceiver();
			PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
			model.registerBroadcastReceiver(receiver, PreciousTrackerModel.INTENT_MSG_REFRESH_MOVE_LIST);
		}

		return rootView;
	}

	private void refreshList() {
		PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
		recentMoveList = model.getRecentMoves();
		movesListAdapter = new PreciousMoveAdapter(parent, recentMoveList);
		ListView movesListView = (ListView) parent.findViewById(R.id.movesListView);
		movesListView.setAdapter(movesListAdapter);
	}

	private void onMoveItemClick(int position) {
		Intent intent = new Intent(context, DisplayMoveActivity.class);
		PreciousMove move = recentMoveList.get(position);
		intent.putExtra(PreciousMove.INTENT_MESSAGE, move);
		startActivity(intent);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		refreshList();

		ListView movesListView = (ListView) parent.findViewById(R.id.movesListView);
		movesListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long rowId) {
				PreciousMovesFragment.this.onMoveItemClick(position);
			}
		});
	}

	public class RefreshMoveListBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			refreshList();
		}

	}

}
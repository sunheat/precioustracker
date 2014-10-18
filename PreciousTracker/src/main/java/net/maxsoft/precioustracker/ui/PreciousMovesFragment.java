package net.maxsoft.precioustracker.ui;

import java.util.List;

import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.PreciousTrackerModel;
import net.maxsoft.precioustracker.model.dao.PreciousMove;
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

public final class PreciousMovesFragment extends Fragment {

	private List<PreciousMove> recentMoveList;
	private PreciousMoveAdapter movesListAdapter;
	private View rootView;
	private Context context;
	private RefreshMoveListBroadcastReceiver receiver;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		this.context = container.getContext();

		rootView = inflater.inflate(R.layout.recent_moves, container, false);

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
		movesListAdapter = new PreciousMoveAdapter(recentMoveList);
		ListView movesListView = (ListView) getActivity().findViewById(R.id.movesListView);
		movesListView.setAdapter(movesListAdapter);
	}

	private void onMoveItemClick(int position) {
		Intent intent = new Intent(context, DisplayMoveActivity.class);
		PreciousMove move = recentMoveList.get(position);
		intent.putExtra(PreciousMove.INTENT_MESSAGE, move);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		refreshList();

		ListView movesListView = (ListView) getActivity().findViewById(R.id.movesListView);
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
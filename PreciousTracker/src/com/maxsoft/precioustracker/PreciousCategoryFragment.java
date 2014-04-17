package com.maxsoft.precioustracker;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.maxsoft.precioustracker.model.PreciousTrackerModel;

public class PreciousCategoryFragment extends Fragment {

	private List<PreciousCategory> categoryList;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.category_list, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		refreshList();
	}

	private void refreshList() {
		PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
		categoryList = model.getCategoryList();
		ArrayAdapter<PreciousCategory> adapter = new ArrayAdapter<PreciousCategory>(getActivity(), android.R.layout.simple_list_item_1, categoryList);
		ListView listView = (ListView) getActivity().findViewById(R.id.lstCategory);
		listView.setAdapter(adapter);
	}
}

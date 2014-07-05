package net.maxsoft.precioustracker.ui;

import java.util.List;

import net.maxsoft.precioustracker.R;
import net.maxsoft.precioustracker.model.PreciousTrackerModel;
import net.maxsoft.precioustracker.model.dao.PreciousCategory;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PreciousCategoryFragment extends Fragment {

    private List<PreciousCategory> categoryList;
    private PreciousCategoryBroadcastReceiver receiver;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (receiver == null) {
            receiver = new PreciousCategoryBroadcastReceiver();
            PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
            model.registerBroadcastReceiver(receiver, PreciousTrackerModel.INTENT_MSG_REFRESH_CATEGORY_LIST);
        }

        return inflater.inflate(R.layout.category_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshList();

        // add the item click listener
        ListView categoryListView = (ListView) getActivity().findViewById(R.id.lstCategory);
        categoryListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long rowId) {
                PreciousCategoryFragment.this.onItemClick(position);
            }
        });
    }

    private void refreshList() {
        PreciousTrackerModel model = PreciousTrackerModel.getInstance(getActivity());
        categoryList = model.getCategoryList();
        ArrayAdapter<PreciousCategory> adapter = new ArrayAdapter<PreciousCategory>(getActivity(),
                android.R.layout.simple_list_item_1, categoryList);
        ListView listView = (ListView) getActivity().findViewById(R.id.lstCategory);
        listView.setAdapter(adapter);
    }

    private void onItemClick(int position) {
        Intent intent = new Intent(getActivity().getBaseContext(), DisplayCategoryActivity.class);
        PreciousCategory category = categoryList.get(position);
        intent.putExtra(PreciousCategory.INTENT_MESSAGE, category);
        startActivity(intent);
    }

    public class PreciousCategoryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            refreshList();
        }

    }
}

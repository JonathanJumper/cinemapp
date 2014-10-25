package unal.jomartinezch.cinemapp.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.adapter.TheatersListAdapter;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.Theater;
import unal.jomartinezch.cinemapp.util.SwipeMenu;
import unal.jomartinezch.cinemapp.util.SwipeMenuCreator;
import unal.jomartinezch.cinemapp.util.SwipeMenuItem;
import unal.jomartinezch.cinemapp.util.SwipeMenuListView;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentTheater extends Fragment {

    private List<Theater> theatersList = DataContainer.getInstance().theaters;
    private SwipeMenuListView listView;
    private TheatersListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_theaters, container, false);
        listView = (SwipeMenuListView) rootView.findViewById(R.id.lv_theaters);
        adapter = new TheatersListAdapter(getActivity(), theatersList);
        listView.setAdapter(adapter);


        final int itemWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, getResources().getDisplayMetrics());

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "locate" item
                SwipeMenuItem locateItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                locateItem.setBackground(R.drawable.ovaled_button_bo_locate);
                // set item width
                locateItem.setWidth(itemWidth);
                // set a icon
                locateItem.setIcon(R.drawable.ic_locate);
                // add to menu
                menu.addMenuItem(locateItem);

                // create "detail" item
                SwipeMenuItem detailsItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                detailsItem.setBackground(R.drawable.ovaled_button_bo_details);
                // set item width
                detailsItem.setWidth(itemWidth);
                // set a icon
                detailsItem.setIcon(R.drawable.ic_details);
                // add to menu
                menu.addMenuItem(detailsItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                listView.smoothOpenMenu(arg2);
            }
        });
        return rootView;
    }
}

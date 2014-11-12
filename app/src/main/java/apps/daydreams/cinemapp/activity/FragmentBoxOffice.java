package apps.daydreams.cinemapp.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.adapter.MoviesListAdapter;
import apps.daydreams.cinemapp.model.DataContainer;
import apps.daydreams.cinemapp.model.MovieLite;
import apps.daydreams.cinemapp.util.SwipeMenu;
import apps.daydreams.cinemapp.util.SwipeMenuCreator;
import apps.daydreams.cinemapp.util.SwipeMenuItem;
import apps.daydreams.cinemapp.util.SwipeMenuListView;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentBoxOffice extends Fragment{

    private List<MovieLite> movieList = DataContainer.getInstance().movies;
    private SwipeMenuListView listView;
    private MoviesListAdapter adapter;
    private MovieLite selected;
    private SearchView sv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_boxoffice, container, false);

        listView = (SwipeMenuListView) rootView.findViewById(R.id.lvy_movies);
        adapter = new MoviesListAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);

        final int itemWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, getResources().getDisplayMetrics());

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "locate" item
                SwipeMenuItem locateItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                locateItem.setBackground(R.drawable.button_list_showtime);
                // set item width
                locateItem.setWidth(itemWidth);
                // set a icon
                locateItem.setIcon(R.drawable.ic_showtime);
                // add to menu
                menu.addMenuItem(locateItem);

                // create "detail" item
                SwipeMenuItem detailsItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                detailsItem.setBackground(R.drawable.button_list_details);
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
                        Intent intent = new Intent();
                        selected = (MovieLite) listView.getItemAtPosition(position);
                        intent.putExtra("mid", selected.mid);
                        intent.setClass(getActivity(), ActivityShowtime.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent();
                        selected = (MovieLite) listView.getItemAtPosition(position);
                        intent2.putExtra("mid", selected.mid);
                        intent2.setClass(getActivity(), ActivityMovieDetail.class);
                        startActivity(intent2);
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

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item = menu.add("Search");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        sv = new SearchView(getActivity());

        // modifying the text inside edittext component
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.search_movie) + "</small>"));
        textView.setHintTextColor(getResources().getColor(R.color.duration));
        textView.setTextColor(getResources().getColor(R.color.primary_text));

        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = (ImageView) sv.findViewById(searchImgId);
        v.setImageResource(R.drawable.ic_search);

        int closeButtonId = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButtonImage = (ImageView) sv.findViewById(closeButtonId);
        closeButtonImage.setImageResource(R.drawable.ic_cancel_action);

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });

        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.getFilter().filter("");
                sv.onActionViewCollapsed();
                return false;
            }
        });

        item.setActionView(sv);
    }
}
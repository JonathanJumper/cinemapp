package apps.daydreams.cinemapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.adapter.ShowtimesListAdapter;
import apps.daydreams.cinemapp.model.DataContainer;
import apps.daydreams.cinemapp.model.Showtime;
import apps.daydreams.cinemapp.util.SwipeMenu;
import apps.daydreams.cinemapp.util.SwipeMenuCreator;
import apps.daydreams.cinemapp.util.SwipeMenuItem;
import apps.daydreams.cinemapp.util.SwipeMenuListView;

public class ActivityShowtime extends ActionBarActivity {

    private List<Showtime> showtimes = DataContainer.getInstance().showtimes;
    private SwipeMenuListView listView;
    private ShowtimesListAdapter adapter;
    private String mid, tid;
    private List<Showtime> showtimesFetched = new ArrayList<Showtime>();

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);

        Bundle b = this.getIntent().getExtras();
        mid = b.getString("mid" , null);
        tid = b.getString("tid", null);

        if(tid == null) {
            for(Showtime s: showtimes)
                if(s.mid.equals(mid)) showtimesFetched.add(s);
        }

        if(mid == null) {
            for(Showtime s: showtimes)
                if(s.tid.equals(tid)) showtimesFetched.add(s);
        }

        listView = (SwipeMenuListView) findViewById(R.id.lv_showtimes);
        adapter = new ShowtimesListAdapter(this, showtimesFetched);
        listView.setAdapter(adapter);

        final int itemWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, getResources().getDisplayMetrics());

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "locate" item
                SwipeMenuItem locateItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                locateItem.setBackground(R.drawable.button_list_theater);
                // set item width
                locateItem.setWidth(itemWidth);
                // set a icon
                locateItem.setIcon(R.drawable.ic_theater);
                // add to menu
                menu.addMenuItem(locateItem);

                // create "detail" item
                SwipeMenuItem detailsItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                detailsItem.setBackground(R.drawable.list_th_button_movies);
                // set item width
                detailsItem.setWidth(itemWidth);
                // set a icon
                detailsItem.setIcon(R.drawable.ic_boxoffice);
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
                        intent.putExtra("tid", showtimesFetched.get(position).tid);
                        intent.setClass(getApplicationContext(), ActivityTheaterDetail.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent();
                        intent2.putExtra("mid", showtimesFetched.get(position).mid);
                        intent2.setClass(getApplicationContext(), ActivityMovieDetail.class);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        onBackPressed();
        return true;
    }
}

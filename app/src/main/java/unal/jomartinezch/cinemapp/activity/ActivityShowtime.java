package unal.jomartinezch.cinemapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.adapter.ShowtimesListAdapter;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.Showtime;
import unal.jomartinezch.cinemapp.util.SwipeMenu;
import unal.jomartinezch.cinemapp.util.SwipeMenuCreator;
import unal.jomartinezch.cinemapp.util.SwipeMenuItem;
import unal.jomartinezch.cinemapp.util.SwipeMenuListView;

public class ActivityShowtime extends Activity {

    private List<Showtime> showtimes = DataContainer.getInstance().showtimes;
    private SwipeMenuListView listView;
    private ShowtimesListAdapter adapter;
    private int moviePos, theaterPos;
    private List<Showtime> showtimesFetched = new ArrayList<Showtime>();

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);

        Bundle b = this.getIntent().getExtras();
        moviePos = b.getInt("movie_position");
        theaterPos = b.getInt("theater_position");

        if(theaterPos == -1) {
            String mid = DataContainer.getInstance().movies.get(moviePos).mid;
            for(Showtime s: showtimes)
                if(s.mid.equals(mid)) showtimesFetched.add(s);
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
                locateItem.setBackground(R.drawable.list_bo_button_locate);
                // set item width
                locateItem.setWidth(itemWidth);
                // set a icon
                locateItem.setIcon(R.drawable.ic_locate);
                // add to menu
                menu.addMenuItem(locateItem);

                // create "detail" item
                SwipeMenuItem detailsItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                detailsItem.setBackground(R.drawable.list_bo_button_details);
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
                        Intent intent = new Intent();
                        intent.putExtra("position", position);
                        intent.setClass(getApplicationContext(), ActivityMovieDetail.class);
                        startActivity(intent);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.showtime, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

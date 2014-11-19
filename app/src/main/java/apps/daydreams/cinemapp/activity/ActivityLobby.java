package apps.daydreams.cinemapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.adapter.NavDrawerItem;
import apps.daydreams.cinemapp.adapter.NavDrawerListAdapter;
import apps.daydreams.cinemapp.model.DataContainer;


public class ActivityLobby extends ActionBarActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList, mDrawerListB;
    private ActionBarDrawerToggle mDrawerToggle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;

    //fragments
    FragmentBoxOffice fragmentBoxOffice;
    FragmentGenre fragmentGenre;
    FragmentTheater fragmentTheater;
    //FragmentMapMe fragmentMapMe;
    FragmentMailbox fragmentMailbox;
    FragmentAbout fragmentAbout;

    public DataContainer data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.nav_open, // nav drawer open - description for accessibility
                R.string.nav_open // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
              //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xfffff70d));
            }
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.nav_open);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mTitle = getResources().getString(R.string.nav_open);

        fragmentBoxOffice = new FragmentBoxOffice();
        fragmentGenre = new FragmentGenre();
        fragmentTheater = new FragmentTheater();
        //fragmentMapMe = new FragmentMapMe();
        fragmentMailbox = new FragmentMailbox();
        fragmentAbout = new FragmentAbout();

        //load data
        data = DataContainer.getInstance();

        // nav drawer icons from resources
        final TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerListB = (ListView) findViewById(R.id.list_slidermenu_bottom);

        final ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
        final ArrayList<NavDrawerItem> navDrawerItemsB = new ArrayList<NavDrawerItem>();


        // agregar un nuevo item al menu deslizante

        // Movies
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), true, data.movies.size()+""));
        // Genres
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Theaters
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, data.theaters.size()+""));
        // MapMe
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

        //Mailbox
        navDrawerItemsB.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        //Settings
        navDrawerItemsB.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        //About
        navDrawerItemsB.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        mDrawerListB.setOnItemClickListener(new SlideMenuClickListenerB());

        // setting the nav drawer list adapter
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.setAdapter(adapter);
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItemsB);
        mDrawerListB.setAdapter(adapter);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }

        //open drawer layout
        getSupportActionBar().setTitle(R.string.nav_open);
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private class SlideMenuClickListenerB implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position+4);
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = fragmentBoxOffice;
                break;
            case 1:
                fragment = fragmentGenre;
                break;
            case 2:
                fragment = fragmentTheater;
                break;
            case 3:
                fragment = new FragmentMapMe();
                break;
            case 4:
                fragment = fragmentMailbox;
                break;
            case 5:
                onBackPressed();
                break;
            case 6:
                fragment = fragmentAbout;
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            if(position < 4) {
                mDrawerListB.setItemChecked(0, false);
                mDrawerListB.setItemChecked(1, false);
                mDrawerListB.setItemChecked(2, false);
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
            }
            else {
                mDrawerList.clearChoices();
                mDrawerListB.setItemChecked(position-4, true);
                mDrawerListB.setSelection(position-4);
            }
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        else Log.d("Error", "Error creating fragment");
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        Log.e("was called ---->","onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.e("was called ---->","onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.e("was called ---->","onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e("was called ---->","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("was called ---->","save instance");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        Log.e("was called ---->","restore instance");
        super.onRestoreInstanceState(savedState);
    }
}
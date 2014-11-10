package apps.daydreams.cinemapp.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
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


public class ActivityLobby extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList, mDrawerListB;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

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
        mTitle = mDrawerTitle = getTitle();

        fragmentBoxOffice = new FragmentBoxOffice();
        fragmentGenre = new FragmentGenre();
        fragmentTheater = new FragmentTheater();
        //fragmentMapMe = new FragmentMapMe();
        fragmentMailbox = new FragmentMailbox();
        fragmentAbout = new FragmentAbout();

        //load data
        data = DataContainer.getInstance();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        final TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        //About
        navDrawerItemsB.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        mDrawerListB.setOnItemClickListener(new SlideMenuClickListenerB());

        // setting the nav drawer list adapter
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.setAdapter(adapter);
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItemsB);
        mDrawerListB.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(getResources().getString(R.string.nav_open));
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
             }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }

        //open drawer layout
        mDrawerLayout.openDrawer(Gravity.LEFT);
        getActionBar().setTitle(getResources().getString(R.string.nav_open));
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    private class SlideMenuClickListenerB implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
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
        getActionBar().setTitle(mTitle);
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
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
package unal.jomartinezch.cinemapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.util.Utils;


public class FragmentMapMe extends Fragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener{

    private final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    private static final float ZOOM_LVL = 15;
    private static final boolean MYLOCATIONENABLE = false;
    private GoogleMap mMap;
    private MapView mapView;
    private View myFragmentView;
    private Location location = null;
    private LocationClient mLocationClient = null;
    private LocationRequest mLocationRequest = null;
    private Marker mOrigin;

    private boolean isCalculating = false;


    private String PrefSitpDist;
    private String PrefTransmiDist;
    private double searchRadiusTransmi = 1000;
    private double searchRadiusSITP = 500;
    private double searchRadius = 0;
    private int MINCHECK_IN_DISTANCE = 40;
    private double rpSearchRadius = 750;
    private double AdsSearchRadius = 2000;
    private int maxCalculateTroncal = 4;
    private int maxCalculateSITP = 4;

    private boolean isDialogWarningAvailable = false;

    private SharedPreferences sharedPref;
    private ArrayList<Marker> BusStops;
    private ArrayList<Marker> rechargePoints = new ArrayList<Marker>();
    private ArrayList<Marker> AdsPoints = new ArrayList<Marker>();

    private int UserStopScore;
    private boolean holy;

    private Polyline line;
    private Polyline lineOrigin;
    private Polyline lineDestination;
    private List<Polyline> lineRoute;

    private boolean isChechedRechargePoints = false;
    private int resolutionDeviceFromDensity;

    private LinearLayout Layouttextroutes;
    private LinearLayout LayoutlistFinalRoutes;
    private TextView textroutes;
    private ListView listFinalRoutes;
    private ImageView startjourney;
    private LinearLayout startjourneyLayout;
    private TextView startjourneyText;
    private RelativeLayout relativeLayoutRoutesInfo;
    private TextView calculating;
    private ImageView closeRoutes;
    private ImageView clearRoutes;
    private ImageView findMe;

    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";


    private String colorBusZone;
    private int PUNISHMENT_TRANSFER = 100;
    private int height = 0;
    private boolean routesClosed = false;

    private LatLng myPosition;

    private String response;

    static  int times = 0;

    /**
     * Creates a new instance of MainFragment
     *
     * @return MainFragment
     */
    public static FragmentMapMe newInstance(int index) {
        Bundle args = new Bundle();
        if(times==0) {
            FragmentMapMe fragment = new FragmentMapMe();

            args.putInt("index", index);
            fragment.setArguments(args);
            times++;
            return fragment;
        }
        return null;
    }

    public FragmentMapMe() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getActivity().invalidateOptionsMenu();
        setRetainInstance(true);
        myFragmentView = inflater.inflate(R.layout.fragment_mapme, container, false);
        // getActivity().invalidateOptionsMenu();


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        height = size.y;

        initResources(myFragmentView);

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {

        }

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                mapView = (MapView) myFragmentView.findViewById(R.id.map);
                mapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (mapView != null) {
                    setUpMapIfNeeded();
                    mLocationClient = new LocationClient(this.getActivity(), this, this);
                    if (servicesConnected()) {
                        mLocationRequest = LocationRequest.create();
                        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        mLocationRequest.setInterval(UPDATE_INTERVAL);
                        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
                    } else {
                        try{

                        }catch (Exception e){

                        }

                    }

                }
                break;
            case ConnectionResult.SERVICE_MISSING:
                try{
                    Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }

                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                try{
                    Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }


                break;
            default:
                try{
                    Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }

        }

        return myFragmentView;
    }



    private void initResources(View myFragmentView) {


        findMe = (ImageView) myFragmentView.findViewById(R.id.findMy);


        findMe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProgressDialog dialog;
                dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Algo");
                dialog.setMessage("Mas");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                try {
                    dialog.show();
                } catch (Exception e) {
                    // TODO: handle exception
                }

                try{
                    mLocationClient.requestLocationUpdates(mLocationRequest, new LocationListener() {

                        @Override
                        public void onLocationChanged(Location arg0) {
                            //TODO mirar ads


                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }



                location = Utils.getLocation(getActivity(), mLocationClient);

                takeToLocation(Utils.convertLocationtoLatLang(location, getActivity()));
                if (mOrigin != null) {
                    mOrigin.setPosition(Utils.convertLocationtoLatLang(location, getActivity()));
                }

                try {
                    mOrigin.showInfoWindow();
                } catch (Exception e) {
                    // TODO: handle exception
                    // ////log.i("GaleonApps", "Origin point null");
                }


                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        times=0;
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        // mapView.onDestroy();
        super.onDestroy();



    }

    @Override
    public void onStart() {
        super.onStart();

        //Checks to see if LocationClient is not set and then sets it
        setUpLocationClientIfNull();
        mLocationClient.connect();
    }

    @Override
    public void onStop() {
        if (mLocationClient.isConnected()) {
            mLocationClient.removeLocationUpdates(this);
            mLocationClient.disconnect();
        }

        super.onStop();
    }


    //And now the method to set up mLocationClient if its null
    private void setUpLocationClientIfNull() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getActivity(), this, this);
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed  (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = mapView.getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        if (mMap == null) {
            //Map is not ready

        } else {
            //Map is ready
            mMap.setMyLocationEnabled(MYLOCATIONENABLE);
            mMap.getUiSettings().setZoomControlsEnabled(MYLOCATIONENABLE);
            mMap.setOnMapLongClickListener(this);
            mMap.setOnMarkerDragListener(this);
            mMap.setOnMarkerClickListener(this);
            Location myLocation = Utils.getLocation(getActivity(), mLocationClient);

            double mylatitude = myLocation.getLatitude();
            double mylongitude = myLocation.getLongitude();

            myPosition = new LatLng(mylatitude, mylongitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));

            mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_LVL));
        }
    }

    /**
     * Move the camera to a location
     *
     * @param toBeLocationLatLang
     */
    public void takeToLocation(LatLng toBeLocationLatLang) {
        if (toBeLocationLatLang != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(toBeLocationLatLang, ZOOM_LVL);
            mMap.animateCamera(update);
        } else {


        }
    }

    /**
     * @return
     */
    private boolean servicesConnected() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == resultCode) {

            return true;
        } else {
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            if (errorDialog != null) {
                errorDialog.show();
            }
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (mOrigin == null) {
            boolean mUpdatesRequestedPreferences = getActivity().getSharedPreferences("LOCATION_UPDATE", getActivity().MODE_PRIVATE).getBoolean("KEY_UPDATES_ON", false);
            Context contextApp = getActivity();
            if (mUpdatesRequestedPreferences) {

            } else {
                location = Utils.getLocation(contextApp, mLocationClient);



            }
            if (location != null) {
                if (!isCalculating) {
                    takeToLocation(Utils.convertLocationtoLatLang(location, getActivity()));
                    mOrigin = mMap.addMarker(new MarkerOptions().position(Utils.convertLocationtoLatLang(location, getActivity())).title("Usted esta aca").draggable(true));
                    // mOrigin = mMap.addMarker(new MarkerOptions().position(Utils.convertLocationtoLatLang(location, getActivity())).title(getString(R.string.you_are_here)).draggable(true)
                    //        .icon(BitmapDescriptorFactory.fromBitmap(profileIcon)));
                    mOrigin.showInfoWindow();
                    //loadAnunciosFromFile();

                }
            } else {
                try{
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                }
            }

        } else {
            try {
                takeToLocation(mOrigin.getPosition());
            } catch (NullPointerException e) {
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    /**
     * Load bus stops around origin
     */


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (BusStops != null) {
            for (int i = 0; i < BusStops.size(); i++) {
                if (marker.equals(BusStops.get(i))) {
                    Marker tempmarker = BusStops.get(i);
                    //TODO CREATE DIALOG
                    break;
                }
            }
        }
        marker.showInfoWindow();

        /*if(marker.equals(anunciomarker))
        {
        //TODO ANUNCIO
            anuncioDialog(anunciomarker);
        }*/
        return true;
    }

    private void cancelDialog(AlertDialog dialog) {
        dialog.cancel();
    }

    public void onMarkerDragStart(Marker marker) {
        cleanLineElements();

        for (int i = 0; i < BusStops.size(); i++) {
            BusStops.get(i).remove();
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        marker.showInfoWindow();

    }

    public void cleanLineElements() {
        if (line != null)
            line.remove();
        if (lineOrigin != null)
            lineOrigin.remove();
        if (lineDestination != null)
            lineDestination.remove();
        if (lineRoute != null) {
            for (int i = 0; i < lineRoute.size(); i++)
                lineRoute.get(i).remove();
        }

    }


    @Override
    public void onMapLongClick(LatLng point) {
        try {
            //TODO crear dialogo
        } catch (Exception e) {

        }

    }



}

package apps.daydreams.cinemapp.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.model.DataContainer;
import apps.daydreams.cinemapp.model.Theater;
import apps.daydreams.cinemapp.util.Utils;

public class FragmentMapMe extends Fragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener {

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
    private LocationClient mLocationClient;
    private LocationRequest mLocationRequest = null;

    private ImageView findMe, details, showtime;
    private LatLng myPosition;
    private int markerIndex = -1;

    private String city = DataContainer.getInstance().city;
    private Double cityLat = DataContainer.getInstance().lat;
    private Double cityLon = DataContainer.getInstance().lon;
    private ArrayList<Theater> theaters = DataContainer.getInstance().theaters;

    public FragmentMapMe() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        myFragmentView = inflater.inflate(R.layout.fragment_mapme, container, false);
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {}

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                mapView = (MapView) myFragmentView.findViewById(R.id.map);
                mapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if (mapView != null) {
                    setUpMapIfNeeded();
                    mLocationClient = new LocationClient(this.getActivity(), this, this);
                    if (servicesConnected()) {
                        mLocationRequest = LocationRequest.create();
                        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        mLocationRequest.setInterval(UPDATE_INTERVAL);
                        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
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
                }catch (Exception e) {

                }
                break;
            default:
                try{
                    Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }
        }

        findMe = (ImageView) myFragmentView.findViewById(R.id.findMe);
        findMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeToLocation(myPosition);
            }
        });

        details = (ImageView) myFragmentView.findViewById(R.id.detailsMap);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tid", theaters.get(markerIndex).tid);
                intent.setClass(getActivity(), ActivityTheaterDetail.class);
                startActivity(intent);
            }
        });

        showtime = (ImageView) myFragmentView.findViewById(R.id.moviesMap);
        showtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tid", theaters.get(markerIndex).tid);
                intent.setClass(getActivity(), ActivityShowtime.class);
                startActivity(intent);
            }
        });

        return myFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = mapView.getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        if (mMap != null) {
            //Map is ready
            mMap.setMyLocationEnabled(MYLOCATIONENABLE);
            mMap.getUiSettings().setZoomControlsEnabled(MYLOCATIONENABLE);
            mMap.setOnMarkerClickListener(this);

            for(Theater t: theaters)
                mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(t.lat), Double.parseDouble(t.lon)))
                                .draggable(false)
                                .title(t.name)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                );
            Location myLocation = Utils.getLocation(getActivity(), mLocationClient);
            myPosition = Utils.convertLocationtoLatLang(myLocation, getActivity().getBaseContext());
            Log.e("position ",myPosition.toString());
            mMap.addMarker(new MarkerOptions()
                            .position(myPosition)
                            .draggable(false)
                            .title(getResources().getString(R.string.youre_here))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cityLat,cityLon)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    marker.hideInfoWindow();
                }
            });
        }
    }

    public void takeToLocation(LatLng toBeLocationLatLang) {
        if (toBeLocationLatLang != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(toBeLocationLatLang, ZOOM_LVL);
            mMap.animateCamera(update);
        }
    }

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
        Log.e("clicked","onLocationChanged");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        markerIndex = Integer.parseInt(marker.getId().replace("m", ""));
        //if marker index equals size, marker is current location
        if(markerIndex == theaters.size()){
            details.setVisibility(View.INVISIBLE);
            showtime.setVisibility(View.INVISIBLE);
        }
        else {
            details.setVisibility(View.VISIBLE);
            showtime.setVisibility(View.VISIBLE);
        }
        return true;
    }

}
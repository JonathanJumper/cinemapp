package apps.daydreams.cinemapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LocationUtils implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener{

    private final static String TAG = "LocationUtils";
    private final static boolean mDebugLog = false;

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    private static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;

    // Define an object that holds accuracy and frequency parameters
    private LocationRequest mLocationRequest;
    private LocationClient mLocationClient;
    private LocationClient getmLocationClient() {
        return mLocationClient;
    }

    private boolean mUpdatesRequested;
    private Context context ;
    private SharedPreferences mPrefs;
    private Editor mEditor;

    public LocationUtils(Context contextFromActivity,  LocationClient mLocationClientActivity) {
        try {
            //Create the context
            setContext(contextFromActivity);
            setmLocationClient(mLocationClientActivity);
            // Create the LocationRequest object
            mLocationRequest = LocationRequest.create();
            // Use high accuracy
            mLocationRequest.setPriority(
                    LocationRequest.PRIORITY_HIGH_ACCURACY);
            // Set the update interval to 5 seconds
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            // Set the fastest update interval to 1 second
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

            // Open the shared preferences
            mPrefs = getContext().getSharedPreferences("SharedPreferences",
                    Context.MODE_PRIVATE);
            // Get a SharedPreferences editor
            mEditor = mPrefs.edit();
	        /*
	         * Create a new location client, using the enclosing class to
	         * handle callbacks.
	         */
	       /*
	        mLocationClient = new LocationClient(getContext(), this, this);
	        try {
	        	mLocationClient.connect();
			} catch (IllegalStateException e) {
				Log.i("GaleonApps", "Error en ubicacion\n"+e);
				// TODO: handle exception
			}
	        // Start with updates turned off
	         *
	         */
            mUpdatesRequested = false;
        } catch (Exception e) {

        }


    }

    public Location returnLocation(){
        try {
            getmLocationClient().requestLocationUpdates(mLocationRequest, this);
            return getmLocationClient().getLastLocation();
        } catch (IllegalStateException e) {
            return null;
        }



    }

    public void setmLocationClient(LocationClient mLocationClient) {
        this.mLocationClient = mLocationClient;
    }



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public void onLocationChanged(Location arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onDisconnected() {
        // TODO Auto-generated method stub

    }

}

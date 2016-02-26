package apps.daydreams.cinemapp.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.util.TypedValue;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    private final static String TAG = "Utils";
    private final static boolean mDebugLog = false;

    private static final int GET_ACTIVITIES = 0;
    private static Context context;
    private static boolean holiday = false;
    private static  int userMin=99;
    private static  int userHour=99;
    private static  int userDay=99;



    public static int getUserDay() {
        int day;
        if(userDay==99)
        {
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_WEEK);
        }
        else
        {
            day=userDay;
        }
        return day;
    }

    public static void setUserTIme(int userhour,int usermin) {
        userHour = userhour;
        userMin= usermin;
    }

    public static void setUserDay(int userday)
    {
        userDay=userday;
    }





    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371; // km
        lat1 = Math.toRadians(lat1);
        lng1 = Math.toRadians(lng1);
        lat2 = Math.toRadians(lat2);
        lng2 = Math.toRadians(lng2);

        double dlon = (lng2 - lng1);
        double dlat = (lat2 - lat1);

        double sinlat = Math.sin(dlat / 2);
        double sinlon = Math.sin(dlon / 2);

        double a = (sinlat * sinlat) + Math.cos(lat1) * Math.cos(lat2) * (sinlon * sinlon);
        double c = 2 * Math.asin(Math.min(1.0, Math.sqrt(a)));

        double distanceInMeters = earthRadius * c * 1000;

        return (int) distanceInMeters;
    }


    private static Location getLastKnownLocation(Context context) {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    /**
     * converts location to lat lang
     *
     * @param location
     * @return
     */
    public static LatLng convertLocationtoLatLang(Location location, Context context) {
        LatLng currentLatLang = null;
        if (location == null) {
            try {
                location = getLastKnownLocation(context);
            } catch (Exception e) {
            }

        }
        try{
            currentLatLang = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (NullPointerException e){
        }

        return currentLatLang;

    }

    public static Location getLocation(Context context, LocationClient mLocationClient) {
        Location locationResult = null;
        try{
            LocationUtils unLocationUtils = new LocationUtils(context,mLocationClient);
            return locationResult =unLocationUtils.returnLocation();
        }

        catch(NullPointerException e){
            Location burnedLocation = new Location("Test");
            // Get the location manager
            LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            Double lat, lon;

            try {
                if (location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    return location;
                }

                Location locationLKL = getLastKnownLocation(context);
                if (locationLKL == null) {
                    burnedLocation.setLatitude(4.598055599999999);
                    burnedLocation.setLongitude(-74.0758333);
                    burnedLocation.setTime(new Date().getTime()); // Set time as
                    // current Date
                    return burnedLocation;
                } else {
                    return locationLKL;
                }
            } catch (NullPointerException e1) {
                burnedLocation.setLatitude(4.598055599999999);
                burnedLocation.setLongitude(-74.0758333);
                burnedLocation.setTime(new Date().getTime()); // Set time as current
                // Date
                return burnedLocation;
            }

        }

    }

    public static int GPSorNetworkLocation(Context currentContext){
        int finalValue = 0;
        LocationManager service = (LocationManager) currentContext.getSystemService(currentContext.LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabled2 = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (enabled){
            finalValue = 1;
        }
        if (enabled2){
            finalValue =2;
        }
        if (enabled&&enabled2){
            finalValue=3;
        }
        return finalValue;
    }


    /**
     * gets the current location details
     *
     * @return
     */

    /**
     * Check if enabled and if not send user to the GSP settings Better solution
     * would be to display a dialog and suggesting to go to the settings
     */
    public static int checkforGPSAndPromtOpen(Context currentContext) {
        LocationManager service = (LocationManager) currentContext.getSystemService(currentContext.LOCATION_SERVICE);

        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        boolean enabled2 = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!enabled)
            return 1;

        boolean enablenet = true;

        ConnectivityManager conMan = (ConnectivityManager) currentContext.getSystemService(Context.CONNECTIVITY_SERVICE); // mobile

        try {

            boolean is3g = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
            boolean iswifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

            if (!is3g && !iswifi) {
                return 3;
            }

        } catch (Exception e) {
            boolean iswifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

            if (!iswifi)
                return 2;
        }
        return 0;

    }

    // Retorna si la conexión se realiza por WiFi
    public static boolean isWiFiConnection(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // mobile
        boolean iswifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return iswifi;
    }

    public static String getDensity(Context context){
        float densityDevice = context.getResources().getDisplayMetrics().density;
        String densityDeviceName ="";
        //0.75 - ldpi
        //1.0 - mdpi
        //1.5 - hdpi
        //2.0 - xhdpi
        //3.0 - xxhdpi
        //4.0 - xxxhdpi


        if (densityDevice==0.75){
            densityDeviceName="ldpi";

        }
        else if (densityDevice==1.0){
            densityDeviceName="mdpi";
        }
        else if (densityDevice==1.5){
            densityDeviceName="hdpi";
        }
        else if (densityDevice==2.0){
            densityDeviceName="xhdpi";
        }
        else if (densityDevice==3.0){
            densityDeviceName="xxhdpi";
        }
        else if (densityDevice==4.0){
            densityDeviceName="xxxhdpi";
        }
        else {
            densityDeviceName="mdpi";
        }

        return densityDeviceName;
    }


    public static int getResolutionIcons(Context context){
        float densityDevice = context.getResources().getDisplayMetrics().density;
        int resolution =0;
        //0.75 - ldpi
        //1.0 - mdpi
        //1.5 - hdpi
        //2.0 - xhdpi
        //3.0 - xxhdpi
        //4.0 - xxxhdpi

        if (densityDevice==0.75){
            resolution=36;

        }
        else if (densityDevice==1.0){
            resolution=48;
        }
        else if (densityDevice==1.5){
            resolution=72;
        }
        else if (densityDevice==2.0){
            resolution=96;
        }
        else if (densityDevice==3.0){
            resolution=144;
        }
        else if (densityDevice==4.0){
            resolution=192;
        }
        else {
            resolution=48;
        }

        return resolution;
    }

    public static int dpToPx(int dp, Context context) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return (int)px;
    }
}

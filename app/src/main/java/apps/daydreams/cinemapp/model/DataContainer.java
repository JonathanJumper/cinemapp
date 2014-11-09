package apps.daydreams.cinemapp.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Usuario on 11/10/2014.
 */
public class DataContainer {

    //singleton global variable class
    private static DataContainer instance;

    public String city;
    public double lat;
    public double lon;
    public ArrayList<MovieLite> movies;
    public ArrayList<Theater> theaters;
    public ArrayList<Showtime> showtimes;

    private DataContainer(){}

    public void setDataContainer(DataContainer data){
        instance = data;
    }

    public static synchronized DataContainer getInstance(){
        if(instance==null){
            Log.e("instance is null","");
            instance=new DataContainer();
        }
        return instance;
    }

}

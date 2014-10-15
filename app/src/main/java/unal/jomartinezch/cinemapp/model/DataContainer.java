package unal.jomartinezch.cinemapp.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Usuario on 11/10/2014.
 */
public class DataContainer {

    //singleton global variable class
    private static DataContainer instance;

    String city;
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

    @Override
    public String toString() {
        return "DataContainer{" +
                "city='" + city + '\'' +
                ", movies=" + movies +
                ", theaters=" + theaters +
                ", showtimes=" + showtimes +
                '}';
    }
}

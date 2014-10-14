package unal.jomartinezch.cinemapp.model;

import java.util.ArrayList;

/**
 * Created by Usuario on 11/10/2014.
 */
public class DataContainer {
    String city;
    public ArrayList<MovieLite> movies;
    public ArrayList<Theater> theaters;
    public ArrayList<Showtime> showtimes;

    public DataContainer(String city){
        this.city = city;
        this.theaters = new ArrayList<Theater>();
        this.showtimes = new ArrayList<Showtime>();
        this.movies = new ArrayList<MovieLite>();
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

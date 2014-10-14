package unal.jomartinezch.cinemapp.model;

import java.util.ArrayList;

/**
 * Created by Usuario on 11/10/2014.
 */
public class City {

	public String city;
    public ArrayList<String> movies;
    public ArrayList<String> theaters;

    public City(){}

    public City (String city){
        this.city = city;
        this.movies = new ArrayList<String>();
        this.theaters = new ArrayList<String>();
    }

    public void addMovie(String movieID){
        movies.add(movieID);
    }

    public void addTheater(String theaterID){
        theaters.add(theaterID);
    }

    @Override
    public String toString() {
        return "\n[city=" + city + ", movies=" + movies + ", theaters="
                + theaters + "]";
    }

}

package model;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class City {
	@Id	public String city;
	public double lat;
	public double lon;
	public ArrayList<String> movies;
	public ArrayList<String> theaters;
	public ArrayList<String> showtimes;
	
	public City(){}
	
	public City (String city, double lat, double lon){
		this.city = city;
		this.lat = lat;
		this.lon = lon;
		this.movies = new ArrayList<String>();
		this.theaters = new ArrayList<String>();
		this.showtimes = new ArrayList<String>();
	}
	
	public void addMovie(String movieID){
		movies.add(movieID);
	}
	
	public void addTheater(String theaterID){
		theaters.add(theaterID);
	}
	
	public void addShowtime(String showtimeID){
		showtimes.add(showtimeID);
	}

	@Override
	public String toString() {
		return "\n[city=" + city + ", lat=" + lat + ", lon=" + lon
				+ ", movies=" + movies + ", theaters=" + theaters + "]";
	}
	
}

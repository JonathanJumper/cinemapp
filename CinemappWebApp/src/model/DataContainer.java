package model;

import java.util.ArrayList;

public class DataContainer {
	
	public String city;
	public double lat;
	public double lon;
	public ArrayList<MovieParsed> movies;
	public ArrayList<Theater> theaters;
	public ArrayList<ShowtimeParsed> showtimes;
	
	public DataContainer(City city){
		this.city = city.city;
		lat = city.lat;
		lon = city.lon;
		this.theaters = new ArrayList<Theater>();
		this.showtimes = new ArrayList<ShowtimeParsed>();
		this.movies = new ArrayList<MovieParsed>();
	}
}

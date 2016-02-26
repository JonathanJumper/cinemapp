package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import model.City;
import model.Movie;
import model.Showtime;
import model.Theater;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class DataManager {
	
	String currentYear = "2014", key="cc4b67c52acb514bdf4931f7cedfd12b";
	public ArrayList<Movie> movies;
	public ArrayList<Theater> theaters;
	public ArrayList<Showtime> showtimes;
	public ArrayList<City> cities;
	
	Elements moviesE;
		
	public DataManager(){
        this.moviesE = new Elements();
        this.movies 	=	new ArrayList<Movie>();
        this.theaters 	= 	new ArrayList<Theater>();
        this.showtimes 	= 	new ArrayList<Showtime>();
        this.cities 	=	new	ArrayList<City>();
    }
	
	// ----------------------------------------------- Movies  -------------------------------------------
	
	private void getDataFromServer(String city){
		try{
			int page = 0;
			moviesE = new Elements();
	        Elements temp = new Elements();
	        do{	
	                Document doc = Jsoup.connect("http://google.com/movies?near="+city+"&sort=1&start="+page).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
	                	      .referrer("http://www.google.com").header("Accept-Language","en").maxBodySize(0).timeout(120*1000).get();
	                temp = doc.getElementsByClass("movie");
	                for (Element e : temp) moviesE.add(e);
	                page += 10;
	        } while (!temp.isEmpty());
	        
		}
		catch(Exception e){
			System.err.print("There was an error getting the data from the server\n");
			e.printStackTrace();
		}
	}
	
	private Movie fillMoviesNamID(int index){
		try{
			String temp, midTemp, mnTemp = null;
			temp = moviesE.get(index).select("[itemprop=name]").select("a[href]").toString();
			midTemp = temp.substring(temp.indexOf("mid=")+4, temp.indexOf(">")-1);
			mnTemp = temp.substring(temp.indexOf(">")+1, temp.indexOf("</a>"));
			
			int fix = mnTemp.indexOf(", The");
			if(fix != -1){
				mnTemp = mnTemp.substring(fix+2)+" "+mnTemp.substring(0, fix);
			}
			
			//clean
			mnTemp = Jsoup.parse(mnTemp).text();
			
			Movie mTemp;
			if(mnTemp.contains("3D")){
				mTemp = new Movie(mnTemp,midTemp, true);
			}
			else{
				mTemp = new Movie(mnTemp,midTemp, false);
			}
			return mTemp;
		}
		catch(Exception e){
			System.err.print("There was an error getting the names and IDs\n");
			e.printStackTrace();
		}
		return null;
			
	}
	
	private void fillMoviesDirCast(int index){
		try{
			String tempDirector = moviesE.get(index).select("[itemprop=director]").text();
			if(tempDirector != null && !tempDirector.isEmpty())
				movies.get(movies.size()-1).director = tempDirector;
			
			String castTemp = moviesE.get(index).select("[itemprop=actors]").html();
			if(castTemp != null && !castTemp.isEmpty()){
				String[] castArray = castTemp.split("\n");
				//clean
				for(int i= 0; i<castArray.length; i++){
					castArray[i] = Jsoup.parse(castArray[i]).text();
				}
				String castString = Arrays.toString(castArray);
				movies.get(movies.size()-1).cast= castString.substring(1,castString.length()-1);
			}
		}
		catch(Exception e){
			System.err.print("There was an error getting the Director and Cast\n");
			e.printStackTrace();
		}
	}
	
	private void fillMoviesDurGen(int index){
		try{
			String temp, dur, gen;
			temp = moviesE.get(index).getElementsByClass("info").text();
			String[] temp2 = temp.split(" - ");
			
			dur = temp2[0].substring(1, temp2[0].length()-2);
			movies.get(movies.size()-1).duration = dur;
			if(temp2.length > 1 ){
				//fix: in spanish won't work (Acontecimiento)
				gen = temp2[1].substring(0, temp2[1].length()-2);
				movies.get(movies.size()-1).genre = gen;
			}
			
		}
		catch(Exception e){
			System.err.print("There was an error getting the data of duration and genre with movie\n"+movies.get(index)+" caused by\n");
			System.err.print("There was an error getting the data of duration and genre with movie element\n"+moviesE.get(index)+" caused by\n");
			e.printStackTrace();
		}
	}
	
	/*
	 * returns the id from themoviedb.org API, to make a deeper search with it
	 * @param name the name of the film to search
	 */
	private int getApiId(String name){
		try {
			//clear the name to make the query
			name = name.replace("in 3D", "");
			name = name.replace("3D", "");
			name = name.replace("An IMAX Experience", "");
			name = name.replace("IMAX", "");
			name = name.replace(":", "");
			name = name.trim();
			
			//get the original title, if any, between the brackets, but check that string in brackets is not the year
			try{
				String temp = name.substring(name.indexOf("(")+1,name.indexOf(")"));
				boolean isYear = false;
				try{
					Integer.parseInt(temp);
					isYear = true;
				}
				catch(Exception e){}
				if(!isYear) name = temp;
				else name = name.replaceAll("\\(.*?\\)","");
			}
			catch(Exception e){}
			
			//fix ", The" error at the end
			
			name = name.trim();
			
			//make a query with the given (key, name, current_year) 
			JSONObject jObject = new JSONObject(Jsoup.connect
					("http://api.themoviedb.org/3/search/movie?api_key="+key+"&query="+name+"&primary_release_year="+currentYear)
					.timeout(120*1000).ignoreContentType(true).execute().body());
			JSONArray moviesArr = jObject.getJSONArray("results");
			
			//fix: needs to get the correct one if there's more than one
			if(moviesArr.length() > 0){
				JSONObject temp = (JSONObject) moviesArr.get(0);
				return temp.getInt("id");
			}
			
			//make a query with the given (key, name) because there weren't found movies in the current year
			jObject = new JSONObject(Jsoup.connect("http://api.themoviedb.org/3/search/movie?api_key="+key+"&query="+name).ignoreContentType(true).execute().body());
			moviesArr = jObject.getJSONArray("results");
			
			//fix: needs to get the correct one if there's more than one
			if(moviesArr.length() > 0){
				JSONObject temp = (JSONObject) moviesArr.get(0);
				return temp.getInt("id");
			}
			
			if(moviesArr.length() == 0) return -1;
			
		} 
		
		catch (Exception e) {
			System.err.print("There was an error getting the movie API ID\n");
			e.printStackTrace();
			return -1;
		}	
		return -1;
	}
	
	private void fillApiData(int pos){
		String lang = null;
		if(pos == 0) lang = "en";
		if(pos == 1) lang = "es";
		if(pos == 2) lang = "pt";
		if(pos == 3) lang = "fr";
		if(pos == 4) lang = "de";
		System.out.println("pos "+pos+"; lang "+lang);
		int id = -1;
		id = getApiId(movies.get(movies.size()-1).name[0]);
		if(id != -1){
			try {
				JSONObject jObject = new JSONObject(Jsoup.connect("http://api.themoviedb.org/3/movie/"+id+"?&api_key="+key+"&language="+lang).ignoreContentType(true).execute().body());
				movies.get(movies.size()-1).imagePath[pos] = jObject.getString("poster_path");
				String tempDesc = jObject.getString("overview");
				if(tempDesc != null && !tempDesc.isEmpty() && !tempDesc.contains("null"))
					movies.get(movies.size()-1).description[pos] = new Text(tempDesc);
				//when spanish change original name (to make it once), when not, change name
				if(pos == 1) movies.get(movies.size()-1).originalName = jObject.getString("original_title");
				else movies.get(movies.size()-1).name[pos] = jObject.getString("title");
				JSONObject jObject2 = new JSONObject(Jsoup.connect("http://api.themoviedb.org/3/movie/"+id+"/videos?&api_key="+key+"&language="+lang).ignoreContentType(true).execute().body());
				JSONArray ja = jObject2.getJSONArray("results");
				for(int i=0; i <ja.length(); i++)
					if( ((JSONObject)ja.get(i)).get("site").equals("YouTube") )
						movies.get(movies.size()-1).trailerPath[pos] = ((JSONObject)ja.get(i)).getString("key");
						
			} catch (Exception e) {
				System.err.println("There was an error filling the API Data in http://api.themoviedb.org/3/movie/"+id+"?&api_key="+key+"&language="+lang+" Caused by:\n");
				e.printStackTrace();
			}
		}
		id = -1;
	}
	
	private void getMoviesData(String city){
		getDataFromServer(city);
		for(int index = 0; index < moviesE.size(); index++){
			System.out.println("Loading movies from "+city+"; "+index+"/"+(moviesE.size()-1)+";");
			Movie mTemp = fillMoviesNamID(index);
			if(!movies.contains(mTemp) && mTemp != null){
				movies.add(mTemp);
				fillMoviesDirCast(index);
				fillMoviesDurGen(index);
				//set other languages
				for(int i=0; i < 5; i++) fillApiData(i);
			}
			for(City c: cities)	if(c.city.equals(city)) c.addMovie(mTemp.mid);
			movies.get(movies.indexOf(mTemp)).cities.add(city);
		}
	}
	
	public ArrayList<Movie> getMovies(){
		
		for(City city: cities) getMoviesData(city.city);
		return movies;
		
	}
	
	// ----------------------------------------------- Showtimes  -------------------------------------------
	
	private void fixEs(String mid, Element  doc){
		Elements e = doc.getElementsByClass("desc");
		String newName = e.get(0).getElementsByAttributeValueContaining("itemprop", "name").text();
		if(newName.contains("(") &&  newName.contains(")")){
			if(newName.indexOf("(") != 0){
				if(newName.indexOf(")") != newName.length()-1)
					newName = newName.substring(0, newName.indexOf("("))+newName.substring(newName.indexOf(")")+1);
				else
					newName = newName.substring(0, newName.indexOf("("));
			}
			else{
				if(newName.indexOf(")") != newName.length()-1)
					newName = newName.substring(newName.indexOf(")")+1);
				else{
					newName = newName.replace("(", "");
					newName = newName.replace(")", "");
				}
			}
		}
		
		if(newName.contains(", The")){
			newName= newName.substring(0, newName.indexOf(", The"));
			newName = "The "+newName;
		}
		
		if(newName.contains(", A")){
			newName= newName.substring(0, newName.indexOf(", A"));
			newName = "A "+newName;
		}
		newName = newName.replace("en 3D", "");
		newName = newName.replace("3D", "");
		newName = newName.trim();
		movies.get(movies.indexOf(new Movie("", mid, false))).name[1] = newName;
	
	}
	
	private Elements getShowtimesFromServer (String mid, String city, int date){
		try {
			Document doc = Jsoup.connect("http://google.com/movies?near="+city+"&date="+date+"&sort=1&mid="+mid).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com").header("Accept-Language","es").maxBodySize(0).get();
			fixEs(mid, doc);
			return doc.getElementsByClass("theater");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void fillShowtimes(String mid, String city, int date){
		String[] movieShowtimes = null;
		String tid = null;
		try{
			Elements temp = getShowtimesFromServer(mid, city, date);
			for(Element movieT: temp){
				Elements tidE = movieT.getElementsByClass("name");
				tid = tidE.get(0).getElementsByAttribute("href").get(0).attr("href");
				if(!tid.isEmpty()){
					tid = tid.substring(tid.indexOf("tid")+4, tid.length());
									
					String showtimesTemp = movieT.getElementsByClass("times").text();
					// clean showtimesTemp as:"?3:30?  ?5:30?  ?7:30?  ?9:30pm?"
					movieShowtimes = showtimesTemp.split(" ");
					movieShowtimes[0] = " "+movieShowtimes[0];
					for(int i = 0; i < movieShowtimes.length; i++){
						movieShowtimes[i] = movieShowtimes[i].substring(2, movieShowtimes[i].length()-1);
					}
					
					//find out if pm or am
					boolean isPm = movieShowtimes[movieShowtimes.length-1].contains("pm");
					for(int i = movieShowtimes.length-1; i >= 0; i-- ){
						boolean changed = false;
						if(isPm && movieShowtimes[i].contains("am")) {
							isPm = false;
							changed = true;
						}
						if(i != movieShowtimes.length-1 && !changed){
							
							if(isPm == true)	movieShowtimes[i] = movieShowtimes[i].concat("pm");
							else				movieShowtimes[i] = movieShowtimes[i].concat("am");
						}
					}
					showtimes.add(new Showtime(tid+"-"+mid, movieShowtimes));
					for(City c: cities)	if(c.city.equals(city)) c.addShowtime(tid+"-"+mid);
				}
			}
		}
		catch (Exception e){
			System.err.println("There was a problem getting the showtimes caused by:\n");
			System.err.println("mid ="+mid+", tid="+tid+" showtimes"+Arrays.toString(movieShowtimes)+"\n");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Showtime> getShowtimes(){
		int index=1;
		for(Movie movie: movies) {
			System.out.println("Loading showtimes"+index+"/"+(movies.size())+"...");
			for(String city: movie.cities)	fillShowtimes(movie.mid, city, 0);
			index++;
		}
		return showtimes;
	}

	// ----------------------------------------------- Teatros -------------------------------------------
	
	public void setTheatersCity(){
		for(Theater t: theaters) 
			for(City c: cities)
				if(t.city.equalsIgnoreCase(c.city))
					c.addTheater(t.tid);
	}
	
	public ArrayList<Theater> getTheaters(){
		theaters = new TheaterDataManager().getTheaters();
		setTheatersCity();
		return theaters;
	}
	
	// ----------------------------------------------- Cities -------------------------------------------
	
	public ArrayList<City> getCities(){
		
		cities.add(new City("bogota", 4.65692, -74.08673));
		cities.add(new City("medellin", 6.23390	, -75.57588));
		cities.add(new City("cali", 3.42244, -76.52051));
		cities.add(new City("villavicencio", 4.14461, -73.63436));
		cities.add(new City("tunja", 5.53214, -73.36126));
		
		return cities;
	}
}
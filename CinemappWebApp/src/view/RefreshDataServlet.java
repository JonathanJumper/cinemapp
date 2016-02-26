package view;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.City;
import model.Movie;
import model.Showtime;
import model.Theater;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import controller.DataManager;


@SuppressWarnings("serial")
public class RefreshDataServlet extends HttpServlet {
	
	
	public void doGet(HttpServletRequest req, final HttpServletResponse resp)
			throws IOException {
		
		List<Key<City>> keysC = ofy().load().type(City.class).keys().list();
		ofy().delete().keys(keysC).now();
		
		List<Key<Movie>> keysM = ofy().load().type(Movie.class).keys().list();
		ofy().delete().keys(keysM).now();
		
		List<Key<Theater>> keysT = ofy().load().type(Theater.class).keys().list();
		ofy().delete().keys(keysT).now();
		
		List<Key<Showtime>> keysS = ofy().load().type(Showtime.class).keys().list();
		ofy().delete().keys(keysS).now();
		
		DataManager dm = new DataManager();
	
		ObjectifyService.register(City.class);
		ObjectifyService.register(Movie.class);
		ObjectifyService.register(Theater.class);
		ObjectifyService.register(Showtime.class);
		
		dm.getCities();
		dm.getMovies();
		dm.getTheaters();
		dm.getShowtimes();
		
		ofy().save().entities(dm.cities).now();
		ofy().save().entities(dm.movies).now();
		ofy().save().entities(dm.theaters).now();
		ofy().save().entities(dm.showtimes).now();
		
		resp.getWriter().println("Success.");
		
		
	}
}

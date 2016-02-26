package view;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.City;
import model.DataContainer;
import model.Movie;
import model.MovieParsed;
import model.Showtime;
import model.ShowtimeParsed;
import model.Theater;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.ObjectifyService;


@SuppressWarnings("serial")
public class GetDataServlet extends HttpServlet {
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {		
		
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String city = req.getParameter("city");
		String lang = req.getParameter("lang");
		int pos = -1;
		if(lang.equalsIgnoreCase("en")) pos = 0;
		if(lang.equalsIgnoreCase("es")) pos = 1;
		if(lang.equalsIgnoreCase("pt")) pos = 2;
		if(lang.equalsIgnoreCase("fr")) pos = 3;
		if(lang.equalsIgnoreCase("de")) pos = 4;

		
		ObjectifyService.register(Movie.class);
		ObjectifyService.register(Theater.class);
		ObjectifyService.register(Showtime.class);
		ObjectifyService.register(City.class);
		
		City c = ofy().load().type(City.class).id(city).now();
		
		DataContainer data = new DataContainer(c);	
		if(c.movies != null) 
			for(String mid: c.movies) 
				data.movies.add( new MovieParsed(ofy().load().type(Movie.class).id(mid).now(), pos) );
			
		if(c.theaters != null)
			for(String tid: c.theaters) 
					data.theaters.add(ofy().load().type(Theater.class).id(tid).now());
					
		if(c.showtimes != null)
			for(String sid: c.showtimes)
				data.showtimes.add( new ShowtimeParsed(ofy().load().type(Showtime.class).id(sid).now()) );
		
		resp.setContentType("application/json");
		resp.getWriter().print(gson.toJson(data));
	}
}

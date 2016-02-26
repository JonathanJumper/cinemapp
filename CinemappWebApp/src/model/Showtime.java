package model;
import java.util.Arrays;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class Showtime {
	public @Id	String sid;
	public String[] showtimes;
	
	public Showtime(){}
	
	public Showtime(String sid, String[] showtimes) {
		super();
		//sid = tid + - + mid
		this.sid = sid;
		this.showtimes = showtimes;
	}

	@Override
	public String toString() {
		return "\n[sid=" + sid + ", showtimes="
				+ Arrays.toString(showtimes) + "]";
	}	
	
}

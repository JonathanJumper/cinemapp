package model;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Cache;

@Entity
@Cache
public class Movie {
		public String[] name;
		public String originalName;
		public @Id	String mid;
		public boolean threeD;
		public String duration;
		public String genre;
		public String director;
		public String cast;
		public Text[] description;
		public String[] imagePath;
		public String[] trailerPath;
		public ArrayList<String> cities;
		
		public Movie(){}
		
		public Movie(String name, String mid, boolean threeD){
			this.name = new String[5];
			this.name[0] = name;
			this.mid = mid;
			this.threeD = threeD;
			this.description= new Text[5];
			this.imagePath= new String[5];
			this.trailerPath= new String[5];
			this.cities = new ArrayList<String>();
		}

		@Override
		public String toString() {
			return "\n[name=" + Arrays.toString(name) + ", originalName="
					+ originalName + ", mid=" + mid + ", threeD=" + threeD
					+ ", duration=" + duration + ", genre=" + genre
					+ ", director=" + director
					+ ", cast="	+ cast + ", description="
					+ Arrays.toString(description) + ", imagePath="
					+ Arrays.toString(imagePath) + ", trailerPath="
					+ Arrays.toString(trailerPath) + ", cities=" + cities + "]";
		}

		@Override
		public boolean equals(Object obj) {
			boolean toReturn = false;
	        if (obj != null && obj instanceof Movie){
	            toReturn = this.mid.equals(((Movie) obj).mid);
	        }
	        return toReturn;
		}		
}
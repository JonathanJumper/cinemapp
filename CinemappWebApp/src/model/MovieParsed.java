package model;

public class MovieParsed {
	public String name;
	public String originalName;
	public String mid;
	public boolean threeD;
	public String duration;
	public String genre;
	public String director;
	public String cast;
	public String description;
	public String imagePath;
	public String trailerPath;
	
	public MovieParsed(){}
	
	public MovieParsed(Movie m, int pos){
		this.name = m.name[pos];
		this.originalName = m.originalName;
		this.mid = m.mid;
		this.threeD = m.threeD;
		this.duration = m.duration;
		this.genre = m.genre;
		this.director= m.director;
		this.cast = m.cast;
		try{
			this.description= m.description[pos].getValue();
		}catch(Exception e){}
		if(m.imagePath[pos] == null) this.imagePath = m.imagePath[0];
		else this.imagePath= m.imagePath[pos];
		if(m.trailerPath[pos] == null) 	this.trailerPath = m.trailerPath[0];
		else this.trailerPath = m.trailerPath[pos];	
	}
}
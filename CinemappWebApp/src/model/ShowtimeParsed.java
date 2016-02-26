package model;

public class ShowtimeParsed {
    public String mid;
    public	String tid;
    public String[] showtimes;

    public ShowtimeParsed(){}
    
    public ShowtimeParsed(Showtime showtime){
    	this.tid = showtime.sid.substring(0, showtime.sid.indexOf("-"));
    	this.mid = showtime.sid.substring(showtime.sid.indexOf("-")+1, showtime.sid.length());
    	this.showtimes = showtime.showtimes;
    }
    
}


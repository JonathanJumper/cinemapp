package unal.jomartinezch.cinemapp.model;

import java.util.Arrays;

/**
 * Created by Usuario on 11/10/2014.
 */
public class Showtime {
    public String mid;
    public	String tid;
    public String[] showtimes;

    public Showtime(){}

    @Override
    public String toString() {
        return "\n[mid=" + mid + ", tid=" + tid + ", showtimes="
                + Arrays.toString(showtimes) + "]";
    }

}

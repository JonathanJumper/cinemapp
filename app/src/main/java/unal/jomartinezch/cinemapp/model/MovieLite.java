package unal.jomartinezch.cinemapp.model;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieLite {
    public String name;
    public String originalName;
    public String mid;
    public boolean threeD;
    public String duration;
    public String genre;
    public String director;
    public String[] cast;
    public String description;
    public String imagePath;
    public ArrayList<String> cities;

    public MovieLite(){}

    @Override
    public String toString() {
        return "\n[name=" + name + ", originalName=" + originalName
                + ", mid=" + mid + ", threeD=" + threeD + ", duration="
                + duration + ", genre=" + genre + ", director=" + director
                + ", cast=" + Arrays.toString(cast) + ", description="
                + description + ", imagePath=" + imagePath + ", cities="
                + cities + "]";
    }
}

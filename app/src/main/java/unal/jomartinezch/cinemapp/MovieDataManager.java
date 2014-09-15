package unal.jomartinezch.cinemapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Usuario on 10/09/2014.
 */
class MovieDataManager {

    private final String city;
    private final String lang;
    private final ArrayList<Movie> movies;
    private final Elements moviesE;

    public MovieDataManager(String city, String lang){
        this.city = city;
        this.lang = lang;
        this.movies = new ArrayList<Movie>();
        this.moviesE = new Elements();
    }

    void getMovies(){
        try{
            int page = 0;
            Elements temp;
            do{
                Document doc = Jsoup.connect("http://google.com/movies?near="+city+"&sort=1&start="+page).header("Accept-Language",lang).get();
                temp = doc.getElementsByClass("movie");
                for (Element e : temp) moviesE.add(e);
                page += 10;
            } while (!temp.isEmpty());

        }
        catch(Exception e){
            System.err.print("There was an error getting the data from the server");
            e.printStackTrace();
        }
    }

    void getMoviesNamId(){
        try{
            String temp, midTemp, mnTemp;
            boolean en = lang.equals("en");

            for(Element e: moviesE){
                temp = e.select("[itemprop=name]").select("a[href]").toString();
                midTemp = temp.substring(temp.indexOf("mid=")+4, temp.indexOf(">")-1);
                mnTemp = temp.substring(temp.indexOf(">")+1, temp.indexOf("</a>"));

                if(en){
                    int fix = mnTemp.indexOf(", The");
                    if(fix != -1){
                        mnTemp = mnTemp.substring(fix+2)+" "+mnTemp.substring(0, fix);
                    }
                }
                if(mnTemp.contains("3D")){
                    movies.add(new Movie(mnTemp,midTemp, true));
                }
                else{
                    movies.add(new Movie(mnTemp,midTemp, false));
                }

            }
        }
        catch(Exception e){
            System.err.print("There was an error getting the names and IDs");
            e.printStackTrace();
        }

    }

    void getMoviesDirCast(){
        try{
            int index = 0;
            for(Element e: moviesE){
                movies.get(index).director = e.select("[itemprop=director]").text();

                String castTemp = e.select("[itemprop=actors]").html();
                movies.get(index).cast = castTemp.split("\n");
                index++;
            }
        }
        catch(Exception e){
            System.err.print("There was an error getting the Director and Cast");
            e.printStackTrace();
        }
    }

    void getMoviesDurGen(){
        try{
            int index = 0;
            String temp, dur, gen;
            for(Element e: moviesE){
                temp = e.getElementsByClass("info").text();
                dur = temp.substring(0, temp.indexOf(" - "));
                gen = temp.substring(temp.indexOf(" - ")+3);
                gen = gen.substring(0,gen.indexOf(" "));

                movies.get(index).duration = dur;
                movies.get(index).genre = gen;
                index++;
            }
        }
        catch(Exception e){
            System.err.print("There was an error getting the data of duration and genre");
            e.printStackTrace();
        }
    }

    public ArrayList<Movie> getMoviesData(){
        getMovies();
        getMoviesNamId();
        getMoviesDirCast();
        getMoviesDurGen();
        return movies;
    }
}
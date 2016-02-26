package apps.daydreams.cinemapp.adapter;

/**
 * Created by Usuario on 09/12/2014.
 */
public class GenreItem {
    private String title;
    private int icon;

    public GenreItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

}

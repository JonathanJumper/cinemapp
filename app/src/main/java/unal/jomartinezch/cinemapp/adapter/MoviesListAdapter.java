package unal.jomartinezch.cinemapp.adapter;

/**
 * Created by Usuario on 21/09/2014.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.model.MovieLite;
import unal.jomartinezch.cinemapp.util.AppController;


public class MoviesListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<MovieLite> movieLiteItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public MoviesListAdapter(Activity activity, List<MovieLite> movieLiteItems) {
        this.activity = activity;
        this.movieLiteItems = movieLiteItems;
    }

    @Override
    public int getCount() {
        return movieLiteItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieLiteItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView director = (TextView) convertView.findViewById(R.id.director);
        TextView duration = (TextView) convertView.findViewById(R.id.duration);
        TextView threeD = (TextView) convertView.findViewById(R.id.threeD);

        // getting movie data for the row
        MovieLite m = movieLiteItems.get(position);

        // thumbnail image
        try {
            String imagePath = m.imagePath.replace("w500", "w92");
            thumbNail.setImageUrl(imagePath, imageLoader);
        }
        catch(Exception e){
            // fix assing another picture --> thumbNail...(another);
            Log.e("In image path of "+m.name,e.toString());
        }
        //assign all other attributes
        try {
            String name = m.name.replace("3D","");
            if(name.contains("(")) title.setText(name.substring(0,name.indexOf("(") ));
            else title.setText(name);
        }catch (Exception e ){
            Log.e("Error in adapter", e.toString());
            title.setText(m.name);
        }
        if(m.genre.contains("/"))  genre.setText(m.genre.substring(0, m.genre.indexOf("/")));
        else genre.setText(m.genre);
        director.setText(m.director);
        duration.setText(m.duration);
        if(m.threeD) threeD.setText("3D");
        else threeD.setText("");

        return convertView;
    }

}

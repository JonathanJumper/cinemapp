package unal.jomartinezch.cinemapp;

/**
 * Created by Usuario on 21/09/2014.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import unal.jomartinezch.cinemapp.model.MovieLite;


public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<MovieLite> movieLiteItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<MovieLite> movieLiteItems) {
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
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        MovieLite m = movieLiteItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.imagePath, imageLoader);

        // title
        title.setText(m.name);

        // rating
        rating.setText("Rating: 7.5");

        // genre
        genre.setText(m.director);

        // release year
        year.setText("2014");

        return convertView;
    }

}

package unal.jomartinezch.cinemapp.adapter;

/**
 * Created by Usuario on 21/09/2014.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.MovieLite;
import unal.jomartinezch.cinemapp.util.AppController;


public class MoviesListAdapter extends BaseAdapter implements Filterable{
    private Activity activity;
    private LayoutInflater inflater;
    private List<MovieLite> movieLiteItems;
    private ValueFilter valueFilter;
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
            convertView = inflater.inflate(R.layout.list_bo_item, null);

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
        if(m.imagePath != null)
            if(!m.imagePath.contains("null")){
                try {
                    String imagePath = "http://image.tmdb.org/t/p/w92"+ m.imagePath;
                    thumbNail.setImageUrl(imagePath, imageLoader);

                } catch (Exception e) {
                    Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
                    thumbNail.setImageDrawable(transparentDrawable);
                    Log.e("In image path of " + m.name, e.toString());
                }
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
        if(m.genre != null)
            if(m.genre.contains("/"))
                genre.setText(m.genre.substring(0, m.genre.indexOf("/")));
        else genre.setText(m.genre);
        director.setText(m.director);
        duration.setText(m.duration);
        if(m.threeD) threeD.setText("3D");
        else threeD.setText("");

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null)
            valueFilter=new ValueFilter();
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String s = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length()>0){
                ArrayList<MovieLite> filterList=new ArrayList<MovieLite>();
                for(MovieLite m: DataContainer.getInstance().movies) {
                    boolean flag = true;
                    if (m.name != null) {
                        String name = Normalizer.normalize(m.name.toLowerCase(), Normalizer.Form.NFD);
                        name = name.replaceAll("[^\\p{ASCII}]", "");
                        if (name.contains(s) || m.genre.contains(s)) {
                            filterList.add(m);
                            flag = false;
                        }
                    }
                    if (flag && m.genre != null){
                        if (m.genre.toLowerCase().contains(s)) {
                            filterList.add(m);
                            flag = false;
                        }
                    }
                    if (flag && m.director != null){
                        if (m.director.toLowerCase().contains(s)) {
                            filterList.add(m);
                            flag = false;
                        }
                    }
                    if (flag && m.originalName != null){
                        if (m.originalName.toLowerCase().contains(s)) {
                            filterList.add(m);
                        }
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=DataContainer.getInstance().movies.size();
                results.values=DataContainer.getInstance().movies;
            }
            return results;
        }

        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movieLiteItems = (ArrayList<MovieLite>) results.values;
            notifyDataSetChanged();
        }
    }
}

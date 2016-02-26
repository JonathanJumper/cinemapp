package apps.daydreams.cinemapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apps.daydreams.cinemapp.R;

/**
 * Created by Usuario on 25/10/2014.
 */
public class GenresListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<GenreItem> genreItems;

    public GenresListAdapter(Activity activity, ArrayList<GenreItem> genreItems) {
        this.activity = activity;
        this.genreItems = genreItems;
    }

    @Override
    public int getCount() {
        return genreItems.size();
    }

    @Override
    public Object getItem(int location) {
        return genreItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_ge_item, parent, false);

        // getting theater data for the row
        GenreItem t = genreItems.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.genre_name);
        name.setText(t.getTitle());

        ImageView icon = (ImageView) convertView.findViewById(R.id.genre_image);
        icon.setImageResource(t.getIcon());

        return convertView;
    }
}
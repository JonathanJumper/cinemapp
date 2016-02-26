package apps.daydreams.cinemapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.model.DataContainer;
import apps.daydreams.cinemapp.model.MovieLite;
import apps.daydreams.cinemapp.model.Showtime;
import apps.daydreams.cinemapp.model.Theater;

/**
 * Created by Usuario on 28/10/2014.
 */
public class ShowtimesListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Showtime> showtimeItems;

    public ShowtimesListAdapter(Activity activity, List<Showtime> showtimeItems) {
        this.activity = activity;
        this.showtimeItems = showtimeItems;
    }

    @Override
    public int getCount() {
        return showtimeItems.size();
    }

    @Override
    public Object getItem(int location) {
        return showtimeItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_sh_item, null);

        // getting movie data for the row
        Showtime s = showtimeItems.get(position);

        TextView movie = (TextView) convertView.findViewById(R.id.sh_movie);
        movie.setText(midToString(s.mid));

        TextView theater = (TextView) convertView.findViewById(R.id.sh_theater);
        theater.setText(tidToString(s.tid));

        TextView showtimes = (TextView) convertView.findViewById(R.id.sh_showtimes);
        showtimes.setText(showtimesToString(s.showtimes));


        return convertView;
    }

    public String midToString(String mid){
        for(MovieLite m: DataContainer.getInstance().movies)
            if(m.mid.equals(mid))
                return m.name;
        return "";
    }
    public String tidToString(String tid){
        for(Theater t: DataContainer.getInstance().theaters)
            if(t.tid.equals(tid))
                return t.name;
        return "";
    }

    public String showtimesToString(String[] showtimes){
        String showtime="";
        for(String s: showtimes)
            showtime = showtime+s+", ";
        showtime =showtime.substring(0,showtime.length()-2);
        return showtime;
    }
}

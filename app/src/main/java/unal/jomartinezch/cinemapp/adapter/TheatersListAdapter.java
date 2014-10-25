package unal.jomartinezch.cinemapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.model.Theater;

/**
 * Created by Usuario on 25/10/2014.
 */
public class TheatersListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Theater> theaterItems;

    public TheatersListAdapter(Activity activity, List<Theater> theatersItems) {
        this.activity = activity;
        this.theaterItems = theatersItems;
    }

    @Override
    public int getCount() {
        return theaterItems.size();
    }

    @Override
    public Object getItem(int location) {
        return theaterItems.get(location);
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



        return convertView;
    }
}

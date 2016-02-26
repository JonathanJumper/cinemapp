package apps.daydreams.cinemapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.model.DataContainer;
import apps.daydreams.cinemapp.model.Theater;

/**
 * Created by Usuario on 25/10/2014.
 */
public class TheatersListAdapter extends BaseAdapter implements Filterable {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Theater> theaterItems;
    private ValueFilter valueFilter;

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
            convertView = inflater.inflate(R.layout.list_th_item, parent, false);

        // getting theater data for the row
        Theater t = theaterItems.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.theater_name);
        name.setText(t.name);

        TextView phone = (TextView) convertView.findViewById(R.id.theater_phone);
        phone.setText(t.tel);

        TextView address = (TextView) convertView.findViewById(R.id.theater_address);
        address.setText(t.desc);


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
                ArrayList<Theater> filterList=new ArrayList<Theater>();
                for(Theater t: DataContainer.getInstance().theaters) {
                    boolean flag = true;
                    try {
                        if (t.name != null) {
                            String name = Normalizer.normalize(t.name.toLowerCase(), Normalizer.Form.NFD);
                            name = name.replaceAll("[^\\p{ASCII}]", "");
                            if (name.contains(s)) {
                                filterList.add(t);
                                flag = false;
                            }
                        }
                    }
                    catch (Exception e){
                        Log.d("Exception on theater filter name-->" + t.name, e.toString());
                    }
                    try {
                        if (flag && t.desc != null) {
                            if (t.desc.toLowerCase().contains(s)) {
                                filterList.add(t);
                            }
                        }
                    }
                    catch(Exception e){
                        Log.d("Exception on theater filter desc-->"+t.desc, e.toString());
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=DataContainer.getInstance().theaters.size();
                results.values=DataContainer.getInstance().theaters;
            }
            return results;
        }

        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            theaterItems = (ArrayList<Theater>) results.values;
            notifyDataSetChanged();
        }
    }
}

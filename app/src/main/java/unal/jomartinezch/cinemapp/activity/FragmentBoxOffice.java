package unal.jomartinezch.cinemapp.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.adapter.MoviesListAdapter;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.MovieLite;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentBoxOffice extends Fragment {

    private List<MovieLite> movieList = new ArrayList<MovieLite>();
    private ListView listView;
    private MoviesListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_boxoffice, container, false);

        listView = (ListView) rootView.findViewById(R.id.lvy_movies);
        adapter = new MoviesListAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);

        DataContainer data = DataContainer.getInstance();
        for(MovieLite m: data.movies) {
            movieList.add(m);
            adapter.notifyDataSetChanged();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent();
                intent.putExtra("position", arg2);
                intent.setClass(getActivity(), ActivityMovieDetail.class);
                startActivity(intent);


            }
        });

        return rootView;

    }

}
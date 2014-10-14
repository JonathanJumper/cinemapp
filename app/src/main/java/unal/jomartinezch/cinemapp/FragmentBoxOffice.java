package unal.jomartinezch.cinemapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.MovieLite;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentBoxOffice extends Fragment {

    private List<MovieLite> movieList = new ArrayList<MovieLite>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_boxoffice, container, false);

        listView = (ListView) rootView.findViewById(R.id.lvy_movies);
        adapter = new CustomListAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);

        return rootView;

    }

    public void show(){
        DataContainer data = ((Lobby) getActivity()).getData();
        for(MovieLite m: data.movies) {
            movieList.add(m);
            adapter.notifyDataSetChanged();
        }
    }
}
package unal.jomartinezch.cinemapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by user on 26/08/2014.
 */
public class BoxOffice extends Fragment {
    ListView lv_movies;
    ArrayList<Movie> movies;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movies = ((Lobby) getActivity()).getMovies();
        View rootView = inflater.inflate(R.layout.fragment_boxoffice, container, false);
        lv_movies = (ListView)rootView.findViewById(R.id.lvy_movies);
        String[] moviesArray = new String[movies.size()];
        for (int i = 0; i < movies.size(); i++)moviesArray[i] = movies.get(i).name;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,  moviesArray );
        lv_movies.setAdapter(adapter);

        return rootView;
    }
}

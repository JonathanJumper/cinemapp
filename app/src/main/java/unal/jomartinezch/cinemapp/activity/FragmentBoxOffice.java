package unal.jomartinezch.cinemapp.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.adapter.MoviesListAdapter;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.MovieLite;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentBoxOffice extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeLayout;
    private List<MovieLite> movieList = new ArrayList<MovieLite>();
    private ListView listView;
    private MoviesListAdapter adapter;
    DataContainer data = DataContainer.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_boxoffice, container, false);

        //getActivity().getActionBar().set
        listView = (ListView) rootView.findViewById(R.id.lvy_movies);
        adapter = new MoviesListAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);

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

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_SHORT).show();
    }
}
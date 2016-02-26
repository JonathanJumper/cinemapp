package apps.daydreams.cinemapp.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.adapter.GenreItem;
import apps.daydreams.cinemapp.adapter.GenresListAdapter;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentGenre extends Fragment {

    ListView genres;
    ArrayList<GenreItem> genreItems;
    GenresListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_genre, container, false);
        genres = (ListView) rootView.findViewById(R.id.lv_genres);
        genreItems = new ArrayList<GenreItem>();

        genreItems.add(new GenreItem("Action", R.drawable.ic_action));
        genreItems.add(new GenreItem("Adventure", R.drawable.ic_adventure));
        genreItems.add(new GenreItem("Animation", R.drawable.ic_animation));
        genreItems.add(new GenreItem("Comedy", R.drawable.ic_comedy));
        genreItems.add(new GenreItem("Documentary", R.drawable.ic_documentary));
        genreItems.add(new GenreItem("Drama", R.drawable.ic_drama));
        genreItems.add(new GenreItem("Fantasy", R.drawable.ic_fantasy));
        genreItems.add(new GenreItem("Horror", R.drawable.ic_horror));
        genreItems.add(new GenreItem("Sci-fi", R.drawable.ic_scifi));
        genreItems.add(new GenreItem("Thriller", R.drawable.ic_thriller));
        genreItems.add(new GenreItem("Musical", R.drawable.ic_musical));


        adapter = new GenresListAdapter(getActivity(), genreItems);
        genres.setAdapter(adapter);

        genres.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ((ActivityLobby)getActivity()).displayView(0);
            }
        });
        return rootView;
    }
}

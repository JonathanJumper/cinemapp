package unal.jomartinezch.cinemapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentGenre extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_genre, container, false);
        Log.d("theaters ", ((Lobby) getActivity()).getData().theaters.toString());
        return rootView;
    }
}

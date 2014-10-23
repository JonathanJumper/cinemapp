package unal.jomartinezch.cinemapp.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unal.jomartinezch.cinemapp.R;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentCalendar extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);


        return rootView;
    }
}

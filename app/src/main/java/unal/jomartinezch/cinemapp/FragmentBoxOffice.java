package unal.jomartinezch.cinemapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.MovieLite;

/**
 * Created by user on 26/08/2014.
 */
public class FragmentBoxOffice extends Fragment {

    private ProgressDialog pDialog;
    private List<MovieLite> movieList = new ArrayList<MovieLite>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_boxoffice, container, false);

        listView = (ListView) rootView.findViewById(R.id.lvy_movies);
        adapter = new CustomListAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);

        // Showing progress dialog before making http request
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.loading));
        pDialog.show();

        String feedUrl = ((Lobby)getActivity()).getFeedUrl();
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        GsonRequest<DataContainer> getData =
                new GsonRequest<DataContainer>(feedUrl, DataContainer.class,
                        new Response.Listener<DataContainer>() {
                            @Override
                            public void onResponse(DataContainer response) {
                                hidePDialog();
                                ((Lobby) getActivity()).setData(response);
                                for(MovieLite m: response.movies) {
                                    movieList.add(m);
                                    adapter.notifyDataSetChanged();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                getActivity().finish();
                                hidePDialog();
                                Toast.makeText(getActivity(), R.string.connection_error ,Toast.LENGTH_LONG).show();
                            }
                        });
        rq.add(getData);
        return rootView;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
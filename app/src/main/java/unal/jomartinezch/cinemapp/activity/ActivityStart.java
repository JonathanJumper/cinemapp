package unal.jomartinezch.cinemapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Calendar;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.util.GsonRequest;

public class ActivityStart extends Activity {

    private final String[] langs = { "  Español", "  English", "  Português", "  Français", "  Deutsch" };
    private final String[] cities = { "  Bogota", "  Medellin", "  Cali", "  Villavicencio", "  Tunja" };
    private String city;
    private String lang;
    private Spinner sp_lang;
    private Spinner sp_cities;
    private ProgressBar pBar;
    private Button okBtn;
    public DataContainer data = DataContainer.getInstance();
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        sp_cities = (Spinner) findViewById(R.id.sp_cities);
        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(this, R.layout.spiner_st_item, cities);
        sp_cities.setAdapter(adapterC);

        sp_lang = (Spinner) findViewById(R.id.sp_lang);
        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(this, R.layout.spiner_st_item, langs);
        sp_lang.setAdapter(adapterL);

        try {
            String versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
            TextView tv = (TextView) findViewById(R.id.tv_version);
            tv.setText("Version beta "+versionName);
        }catch(Exception e){}

        okBtn = (Button) findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                okBtn.setEnabled(false);
                okOnClick();
            }
        });

        pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#e91e63"),
                android.graphics.PorterDuff.Mode.SRC_IN);
        preferences = getPreferences(MODE_PRIVATE);
    }

    public void okOnClick(){

        city = cities[sp_cities.getSelectedItemPosition()].trim().toLowerCase();
        switch (sp_lang.getSelectedItemPosition()) {
            case 0:
                lang = "es";
                break;
            case 1:
                lang = "en";
                break;
            case 2:
                lang = "pt";
                break;
            case 3:
                lang = "fr";
                break;
            case 4:
                lang = "de";
                break;
            default:
                lang = "es";
                break;
        }

        Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DAY_OF_YEAR)+c.get(Calendar.YEAR);

        //if date is not current, clear cache, and refresh it
        int gotDate = preferences.getInt("date", -1);
        if(gotDate == -1) {
            Log.e("into:", "null got date");
            preferences.edit().clear();
            refreshData();
            return;
        }
        if(gotDate != date) {
            Log.e("into:", "different date");
            preferences.edit().clear();
            refreshData();
            return;
        }

        //if city is not the same, refresh data
        String gotCity = preferences.getString("city", "");
        if(!gotCity.equals(city)){
            Log.e("into:", "different city");
            preferences.edit().clear();
            refreshData();
            return;
        }

        //if lang is not the same, refresh data
        String gotLang = preferences.getString("lang", "");
        if(!gotLang.equals(lang)){
            Log.e("into:", "different lang");
            preferences.edit().clear();
            refreshData();
            return;
        }

        //if there isn't data at all, refresh data, else load cache
        Gson gson = new Gson();
        String json = preferences.getString("data", null);
        if(json == null) {
            Log.e("into:", "data == null");
            refreshData();
        }
        else {
            Log.e("into:", "data from cache");
            data.setDataContainer(gson.fromJson(json, DataContainer.class));
            Intent intent = new Intent(getApplicationContext(), ActivityLobby.class);
            startActivity(intent);
            okBtn.setEnabled(true);
        }
    }

    public void refreshData(){

        // Showing progress bar before making http request
        pBar.setVisibility(View.VISIBLE);

        String feedUrl = "http://cinemappco.appspot.com/getdata?city="+city+"&lang="+lang;
        RequestQueue rq = Volley.newRequestQueue(this);
        GsonRequest<DataContainer> getData =
                new GsonRequest<DataContainer>(feedUrl, DataContainer.class,
                        new Response.Listener<DataContainer>() {
                            @Override
                            public void onResponse(DataContainer response) {
                                okBtn.setEnabled(true);
                                pBar.setVisibility(View.GONE);
                                data.setDataContainer(response);

                                Intent intent = new Intent(getApplicationContext(), ActivityLobby.class);
                                startActivity(intent);

                                //persist data
                                SharedPreferences.Editor prefsEditor = preferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(response);
                                prefsEditor.putString("data", json);
                                prefsEditor.putInt("date", Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + Calendar.getInstance().get(Calendar.YEAR));
                                prefsEditor.putString("city", city);
                                prefsEditor.putString("lang",lang);
                                prefsEditor.commit();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                finish();
                                pBar.setVisibility(View.INVISIBLE);
                                Log.e("Error on response", error.toString());
                                Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_LONG).show();
                            }
                        });
        int socketTimeout = 20*1000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        getData.setRetryPolicy(policy);
        rq.add(getData);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        pBar.setVisibility(View.GONE);
    }

}

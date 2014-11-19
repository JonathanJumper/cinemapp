package apps.daydreams.cinemapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.model.DataContainer;
import apps.daydreams.cinemapp.util.AppController;
import apps.daydreams.cinemapp.util.GsonRequest;

public class ActivityStart extends Activity {

    private final String[] langs = { "  Español", "  English", "  Português", "  Français", "  Deutsch" };
    private final String[] cities = { "  Bogota", "  Medellin", "  Cali", "  Villavicencio", "  Tunja"};
    private String city, gotCity;
    private String lang, gotLang;
    private Spinner sp_lang;
    private Spinner sp_cities;
    private ProgressBar pBar;
    private Button okBtn;
    private TextView tv_lang, tv_city;
    public DataContainer data = DataContainer.getInstance();
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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

        sp_cities = (Spinner) findViewById(R.id.sp_cities);
        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(this, R.layout.spiner_st_item, cities);
        sp_cities.setAdapter(adapterC);

        sp_lang = (Spinner) findViewById(R.id.sp_lang);
        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(this, R.layout.spiner_st_item, langs);
        sp_lang.setAdapter(adapterL);

        preferences = getPreferences(MODE_PRIVATE);
        gotCity = preferences.getString("city", "bogota");
        sp_cities.setSelection(cityToInt(gotCity));

        gotLang = preferences.getString("lang", "es");
        sp_lang.setSelection(langToInt(gotLang));
        refreshLang(gotLang);

        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_city.setText(R.string.start_pick_city);

        tv_lang = (TextView) findViewById(R.id.tv_lang);
        tv_lang.setText(R.string.start_pick_lang);

    }

    public void okOnClick(){

        city = cities[sp_cities.getSelectedItemPosition()].trim().toLowerCase();
        lang = intToLang(sp_lang.getSelectedItemPosition());

        Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DAY_OF_YEAR)+c.get(Calendar.YEAR);

        //if date is not current, clear prefs and cache, and refresh it
        int gotDate = preferences.getInt("date", -1);
        if(gotDate == -1) {
            Log.e("into:", "null date");
            deleteCache(AppController.getInstance());
            preferences.edit().clear();
            refreshData();
            return;
        }
        if(gotDate != date) {
            Log.e("into:", "different date");
            deleteCache(AppController.getInstance());
            preferences.edit().clear();
            refreshData();
            return;
        }

        //if lang is not the same, refresh data
        gotLang = preferences.getString("lang", "");
        if(!gotLang.equals(lang)){
            Log.e("into:", "different lang");
            preferences.edit().clear();
            refreshLang(lang);
            refreshData();
            return;
        }

        //if city is not the same, refresh data
        gotCity = preferences.getString("city", "");
        if(!gotCity.equals(city)) {
            Log.e("into:", "different city");
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
            toLobby();
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
                                pBar.setVisibility(View.GONE);
                                data.setDataContainer(response);

                                //persist data
                                SharedPreferences.Editor prefsEditor = preferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(response);
                                prefsEditor.putString("data", json);
                                prefsEditor.putInt("date", Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + Calendar.getInstance().get(Calendar.YEAR));
                                prefsEditor.putString("city", city);
                                prefsEditor.putString("lang", lang);
                                prefsEditor.commit();

                                toLobby();
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

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        try {
            dir.delete();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void toLobby(){
        okBtn.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(), ActivityLobby.class);
        startActivity(intent);
    }

    public String intToLang(int lang){
        switch (lang) {
            case 0:
                return "es";
            case 1:
                return "en";
            case 2:
                return "pt";
            case 3:
                return "fr";
            case 4:
                return "de";
            default:
                return "es";
        }
    }

    public int langToInt(String lang){
        if (lang.equals("es")) {
            return 0;
        } else if (lang.equals("en")) {
            return 1;
        } else if (lang.equals("pt")) {
            return 2;
        } else if (lang.equals("fr")) {
            return 3;
        } else if (lang.equals("de")) {
            return 4;
        } else {
            return 1;
        }
    }

    public int cityToInt(String city){
        for(int i = 0; i < cities.length; i++)
            if(cities[i].trim().toLowerCase().equals(city)) return i;
        return 0;
    }

    public void refreshLang(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_city.setText(R.string.start_pick_city);

        tv_lang = (TextView) findViewById(R.id.tv_lang);
        tv_lang.setText(R.string.start_pick_lang);
    }


    @Override
    public void onPause() {
        super.onPause();
    }
}

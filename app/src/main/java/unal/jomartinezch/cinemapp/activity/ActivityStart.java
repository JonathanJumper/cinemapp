package unal.jomartinezch.cinemapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.util.GsonRequest;


public class ActivityStart extends Activity {

    private final String[] langs = { "  Español", "  English", "  Português", "  Français", "  Deutsch" };
    private final String[] cities = { "  Bogotá", "  Medellin", "  Cali", "  Villavicencio", "  Tunja" };
    private String city;
    private String lang;
    private Spinner sp_lang;
    private Spinner sp_cities;
    private ProgressDialog pDialog;
    public DataContainer data = DataContainer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        sp_cities = (Spinner) findViewById(R.id.sp_cities);
        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(this, R.layout.spiner_item, cities);
        sp_cities.setAdapter(adapterC);

        sp_lang = (Spinner) findViewById(R.id.sp_lang);
        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(this, R.layout.spiner_item, langs);
        sp_lang.setAdapter(adapterL);

        try {
            String versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
            TextView tv = (TextView) findViewById(R.id.tv_version);
            tv.setTextColor(Color.parseColor("#5fffffff"));
            tv.setText("Version beta "+versionName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void okOnClick(View v){

        switch (sp_cities.getSelectedItemPosition()) {
            case 0:
                city = "bogota";
                break;
            case 1:
                city = "medellin";
                break;
            case 2:
                city = "cali";
                break;
            case 3:
                city = "villavicencio";
                break;
            case 4:
                city = "tunja";
                break;
            default:
                city = "bogota";
                break;
        }
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

        // Showing progress dialog before making http request
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.loading));
        pDialog.show();

        String feedUrl = "http://extreme-core.appspot.com/getdata?city="+city+"&lang="+lang;
        RequestQueue rq = Volley.newRequestQueue(this);
        GsonRequest<DataContainer> getData =
                new GsonRequest<DataContainer>(feedUrl, DataContainer.class,
                        new Response.Listener<DataContainer>() {
                            @Override
                            public void onResponse(DataContainer response) {
                                hidePDialog();
                                data.setDataContainer(response);
                                Intent intent = new Intent(getApplicationContext(), ActivityLobby.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                finish();
                                hidePDialog();
                                Log.e("Error on response", error.toString());
                                Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_LONG).show();
                            }
                        });
        rq.add(getData);
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

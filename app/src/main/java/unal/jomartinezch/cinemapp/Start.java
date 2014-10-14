package unal.jomartinezch.cinemapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class Start extends Activity {

    private final String[] langs = { "  Español", "  English", "  Português", "  Français", "  Deutsch" };
    private final String[] cities = { "  Bogotá", "  Medellin", "  Cali", "  Villavicencio", "  Tunja" };;
    private String city;
    private String lang;
    private Spinner sp_lang;
    private Spinner sp_cities;
    private ProgressDialog pDialog;

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

        String feedUrl = "http://extreme-core.appspot.com/getdata?city="+city+"&lang="+lang;

        Intent intent = new Intent();
        intent.putExtra("url", feedUrl);
        intent.setClass(Start.this, Lobby.class);
        startActivity(intent);


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

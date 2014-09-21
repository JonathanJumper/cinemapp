package unal.jomartinezch.cinemapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.text.Normalizer;
import java.util.ArrayList;


public class Start extends Activity {

    private final String[] langs = { "  Español", "  English", "  Français", "  Deutsch", "  Italiano" };
    private Spinner sp_lang;
    private EditText et;
    private String city;
    private String lang;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        sp_lang = (Spinner) findViewById(R.id.sp_lang);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spiner_item, langs);
        sp_lang.setAdapter(adapter);



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
        et = (EditText) findViewById(R.id.et_city);
        if(TextUtils.isEmpty(et.getText())){
            Toast.makeText(Start.this, "Ingresa tu ciudad", Toast.LENGTH_SHORT).show();
        }
        else {
            city = et.getText().toString().toLowerCase();
            city = Normalizer.normalize(city, Normalizer.Form.NFD);
            city = city.replaceAll("[^\\p{ASCII}]", "");
            switch (sp_lang.getSelectedItemPosition()) {
                case 0:
                    lang = "es";
                    break;
                case 1:
                    lang = "en";
                    break;
                default:
                    lang = "es";
                    break;
            }
            new getData().execute();
        }

    }
    private class getData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            movies = new MovieDataManager(city,lang).getMoviesData();
            return null;
        }

        ProgressDialog pDialog;
        @Override
        protected void onPreExecute(){
            pDialog = new ProgressDialog(Start.this);
            pDialog.setMessage("Monos entrenados trabajando...");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void result){
            if(movies.isEmpty())Toast.makeText(Start.this, "No se pudo obtener informacion, Verifica los datos.", Toast.LENGTH_SHORT).show();
            else{
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putParcelableArrayList("movies", movies);
                intent.putExtras(b);
                intent.setClass(Start.this, Lobby.class);
                startActivity(intent);
            }
            pDialog.dismiss();
        }
    }

}

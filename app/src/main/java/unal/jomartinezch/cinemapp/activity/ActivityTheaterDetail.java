package unal.jomartinezch.cinemapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.Theater;

public class ActivityTheaterDetail extends Activity {

    Theater t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_detail);

        Bundle b = this.getIntent().getExtras();
        String tid = b.getString("tid");
        for (Theater t: DataContainer.getInstance().theaters){
            if(t.tid.equals(tid)){
                this.t = t;
                break;
            }
        }

        ((TextView) findViewById(R.id.theater_name)).setText(t.name);
        ((TextView) findViewById(R.id.theater_adress)).setText(t.desc);
        ((TextView) findViewById(R.id.theater_phone)).setText(t.tel);
        ((TextView) findViewById(R.id.theater_url)).setText(t.url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.theater_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void urlOnClick(View view) {
        Uri webpage = Uri.parse(t.url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
}

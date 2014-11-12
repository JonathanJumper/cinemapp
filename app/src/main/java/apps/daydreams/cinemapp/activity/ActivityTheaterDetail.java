package apps.daydreams.cinemapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.model.DataContainer;
import apps.daydreams.cinemapp.model.Theater;

public class ActivityTheaterDetail extends ActionBarActivity {

    Theater t = null;

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
        if(t != null) {
            ((TextView) findViewById(R.id.theater_name)).setText(t.name);
            ((TextView) findViewById(R.id.theater_adress)).setText(t.desc);
            ((TextView) findViewById(R.id.theater_phone)).setText(t.tel);
            ((TextView) findViewById(R.id.theater_url)).setText(t.url);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        onBackPressed();
        return true;
    }

    public void urlOnClick(View view) {
        Uri webpage = Uri.parse(t.url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
}

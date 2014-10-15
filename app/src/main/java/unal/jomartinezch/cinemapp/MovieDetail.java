package unal.jomartinezch.cinemapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import unal.jomartinezch.cinemapp.model.DataContainer;


public class MovieDetail extends Activity {

    DataContainer data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle b = this.getIntent().getExtras();
        int pos = b.getInt("position");
        data = DataContainer.getInstance();

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffeb3b")));
        getActionBar().setTitle(data.movies.get(pos).name);

        TextView t = (TextView)findViewById(R.id.movieDetail);
        t.setText(data.movies.get(pos).toString());

        Toast.makeText(getApplicationContext(),"Position "+pos, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_detail, menu);
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
}

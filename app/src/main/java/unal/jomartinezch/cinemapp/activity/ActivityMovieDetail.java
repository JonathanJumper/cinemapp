package unal.jomartinezch.cinemapp.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import unal.jomartinezch.cinemapp.R;
import unal.jomartinezch.cinemapp.model.DataContainer;
import unal.jomartinezch.cinemapp.model.MovieLite;
import unal.jomartinezch.cinemapp.util.AppController;


public class ActivityMovieDetail extends Activity {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle b = this.getIntent().getExtras();
        final int pos = b.getInt("position");
        final MovieLite m = DataContainer.getInstance().movies.get(pos);

        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.thumbnail);

        try {
            String imagePath = m.imagePath;
            thumbNail.setImageUrl(imagePath, imageLoader);
        }
        catch(Exception e){
            // fix assing another picture --> thumbNail...(another);
            Log.e("In image path of " + m.name, e.toString());
        }

        ((TextView) findViewById(R.id.name)).setText(m.name);
        if(m.description.equals("null")) ((TextView) findViewById(R.id.description)).setText(R.string.movie_description_null);
        else ((TextView) findViewById(R.id.description)).setText(m.description);
        ((TextView) findViewById(R.id.original)).setText(m.originalName);
        ((TextView) findViewById(R.id.genre)).setText(m.genre.replace("/",", "));

        final Button button = (Button) findViewById(R.id.trailer);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTrailer f = FragmentTrailer.newInstance(m.trailerPath.
                        replace("https://www.youtube.com/watch?v=",""));
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container_video, f).commit();
            }
        });
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

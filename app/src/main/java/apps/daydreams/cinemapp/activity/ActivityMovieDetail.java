package apps.daydreams.cinemapp.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import apps.daydreams.cinemapp.R;
import apps.daydreams.cinemapp.model.DataContainer;
import apps.daydreams.cinemapp.model.MovieLite;
import apps.daydreams.cinemapp.util.AppController;


public class ActivityMovieDetail extends ActionBarActivity {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    MovieLite m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle b = this.getIntent().getExtras();
        String mid = b.getString("mid");
        for (MovieLite m : DataContainer.getInstance().movies){
            if (m.mid.equals(mid)) {
                this.m = m;
                break;
            }
        }

        if(m.imagePath == null) {
            findViewById(R.id.thumbnail).setVisibility(View.GONE);
        }
        else{
            if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.thumbnail);
            try {
                String imagePath = "http://image.tmdb.org/t/p/w500"+m.imagePath;
                thumbNail.setImageUrl(imagePath, imageLoader);
            } catch (Exception e) {
                findViewById(R.id.thumbnail).setVisibility(View.GONE);
            }
        }

        if(m.name == null){
            findViewById(R.id.header).setVisibility(View.GONE);
        }
        else {
            ((TextView) findViewById(R.id.name)).setText(m.name);
        }
        if(m.originalName == null){
            findViewById(R.id.original_title).setVisibility(View.GONE);
            findViewById(R.id.original).setVisibility(View.GONE);
        }
        else {
            ((TextView) findViewById(R.id.original)).setText(m.originalName);
        }
        if(m.genre == null){
            findViewById(R.id.genre_title).setVisibility(View.GONE);
            findViewById(R.id.genre).setVisibility(View.GONE);
        }
        else {
            ((TextView) findViewById(R.id.genre)).setText(m.genre.replace("/", ", "));
        }
        if(m.director == null){
            findViewById(R.id.director_title).setVisibility(View.GONE);
            findViewById(R.id.director).setVisibility(View.GONE);
        }
        else {
            ((TextView) findViewById(R.id.director)).setText(m.director);
        }
        if(m.cast == null){
            findViewById(R.id.cast_title).setVisibility(View.GONE);
            findViewById(R.id.cast).setVisibility(View.GONE);
        }
        else {
            ((TextView) findViewById(R.id.cast)).setText(m.cast);
        }
        if ( m.description == null ) {
            findViewById(R.id.description_title).setVisibility(View.GONE);
            findViewById(R.id.description).setVisibility(View.GONE);
        }
        else {
            ((TextView) findViewById(R.id.description)).setText(m.description);
        }

        if (m.trailerPath == null) {
            findViewById(R.id.trailer_title).setVisibility(View.GONE);
            findViewById(R.id.frame_container_video).setVisibility(View.GONE);
        }
        else{
            final FragmentTrailer f = FragmentTrailer.newInstance(m.trailerPath);
            final FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container_video, f).commit();
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
}

package apps.daydreams.cinemapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import apps.daydreams.cinemapp.R;

public class ActivityDaydreams extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daydreams);

        ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"),
                android.graphics.PorterDuff.Mode.SRC_IN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(ActivityDaydreams.this, ActivityStart.class);
                ActivityDaydreams.this.startActivity(mainIntent);
                ActivityDaydreams.this.finish();
            }
        }, 1500);
    }
}

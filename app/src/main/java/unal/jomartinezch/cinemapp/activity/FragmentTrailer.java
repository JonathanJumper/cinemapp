package unal.jomartinezch.cinemapp.activity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

/**
 * Created by Usuario on 20/10/2014.
 */
public class FragmentTrailer extends YouTubePlayerFragment {

    public FragmentTrailer() { }

    public static FragmentTrailer newInstance(String url) {
        FragmentTrailer f = new FragmentTrailer();

        Bundle b = new Bundle();
        b.putString("url", url);
        f.setArguments(b);
        f.init();
        return f;
    }

    private void init() {

        initialize("AIzaSyC8VGL64CyC-IG43IXsqpqDBdzGvXTelKE", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {}

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.cueVideo(getArguments().getString("url"));
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {
                            if(b) {
                                player.setFullscreen(true);
                            }
                            else{
                                player.setFullscreen(false);
                            }
                        }
                    });
                }
            }
        });
    }
}
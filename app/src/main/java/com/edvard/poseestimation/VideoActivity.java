package com.edvard.poseestimation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoActivity extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    private Button btn_video_again;
    private Button btn_video_start;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        btn_video_again = (Button)findViewById(R.id.btn_video_again);
        btn_video_start = (Button)findViewById(R.id.btn_video_start);
        youTubePlayerView = findViewById(R.id.youtube_player_video);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onApiChange(YouTubePlayer youTubePlayer) {
                super.onApiChange(youTubePlayer);
                youTubePlayer.loadVideo("hvtrkKrfP9w", 0f);
            }
        });

        btn_video_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(VideoActivity.this, CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
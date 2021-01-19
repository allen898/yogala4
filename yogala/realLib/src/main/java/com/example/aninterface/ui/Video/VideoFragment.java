package com.example.aninterface.ui.Video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.aninterface.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoFragment extends Fragment {

    private VideoViewModel myMenuViewModel;
    private VideoIdentificationFragment videoIdentificationFragment;
    private FragmentManager fragmentManager;
    private YouTubePlayerView youTubePlayerView;
    private Button btn_video_again;
    private Button btn_video_start;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myMenuViewModel =
                ViewModelProviders.of(this).get(VideoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_video, container, false);
        btn_video_again = (Button) root.findViewById(R.id.btn_video_again);
        btn_video_start = (Button) root.findViewById(R.id.btn_video_start);
        youTubePlayerView = root.findViewById(R.id.youtube_player_video);

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
                videoIdentificationFragment = new VideoIdentificationFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, videoIdentificationFragment).addToBackStack(null).commit();
            }
        });

        return root;
    }
}

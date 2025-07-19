package com.example.muzik;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

public class MusicPlayerActivity  extends AppCompatActivity {

    TextView tvTime, tvDuration, tvTitle, tvArtist;
    SeekBar seekBarTime, seekBarVolume;
    Button btnPlay;

    MediaPlayer musicPlayer;
    Handler handler = new Handler();
    Runnable updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        tvTime = findViewById(R.id.tvTime);
        tvDuration = findViewById(R.id.tvDuration);
        seekBarTime = findViewById(R.id.seekBarTime);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        btnPlay = findViewById(R.id.btnPlay);
        tvTitle = findViewById(R.id.tvTitle);


        String musicName = getIntent().getStringExtra("musicName");
        int resId = getIntent().getIntExtra("resId", -1);

        if (resId == -1) {
            Log.e("MusicPlayerDetailActivity", "Invalid resource ID. Exiting activity.");
            finish();
            return;
        }

        tvTitle.setText(musicName);


        musicPlayer = MediaPlayer.create(this, resId);

        if (musicPlayer == null) {
            Log.e("MusicPlayerListActivity", "MediaPlayer initialization failed.");
            finish();
            return;
        }

        musicPlayer.setLooping(true);
        tvDuration.setText(millisecondsToString(musicPlayer.getDuration()));
        seekBarTime.setMax(musicPlayer.getDuration());

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress / 100f;
                musicPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicPlayer.isPlaying()) {
                    musicPlayer.pause();
                    btnPlay.setText("Play");
                } else {
                    musicPlayer.start();
                    btnPlay.setText("Pause");
                }
            }
        });

        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (musicPlayer != null && musicPlayer.isPlaying()) {
                    seekBarTime.setProgress(musicPlayer.getCurrentPosition());
                    tvTime.setText(millisecondsToString(musicPlayer.getCurrentPosition()));
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(updateSeekBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
        handler.removeCallbacks(updateSeekBar);
    }

    private String millisecondsToString(int time) {
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}

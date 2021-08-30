package com.example.mediaplayer;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView songName;
    public ImageView diskimage;
    private Button play,stop,bcontinue,exit,list;
    private Button nextsong,lastsong;
    private SeekBar seekBar;
    private TextView alltime,nowtime;
    private ImageView imageView;
    private String N = "INFO";
    private ArrayList<Integer> playList = new ArrayList<>();
    private int onePlaying = 0;
    private MediaPlayer mediaPlayer;
    private ObjectAnimator animator;
    private boolean isPlaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        preparePlaylist();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isPlaying) {
                    updateTimer();
                }
            }
        };
        new Timer().scheduleAtFixedRate(timerTask, 0, 500);
    }
    void  init(){
        nowtime = findViewById(R.id.now_time);
        alltime = findViewById(R.id.all_time);
        play = findViewById(R.id.bu_play);
        stop = findViewById(R.id.bu_stop);
        bcontinue = findViewById(R.id.bu_jixu);
        exit = findViewById(R.id.bu_exit);
        seekBar = findViewById(R.id.seekBar);
        imageView = findViewById(R.id.imageView);
        nextsong = findViewById(R.id.next);
        lastsong = findViewById(R.id.last);
        list = findViewById(R.id.list_in);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });
        OnClick onClick = new OnClick();
        play.setOnClickListener(onClick);
        stop.setOnClickListener(onClick);
        bcontinue.setOnClickListener(onClick);
        exit.setOnClickListener(onClick);
        nextsong.setOnClickListener(onClick);
        lastsong.setOnClickListener(onClick);
        songName = findViewById(R.id.songtitle);

        animator = ObjectAnimator.ofFloat(imageView,"rotation",0,360F);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
    }
    private void prepareMedia() {
        if (isPlaying) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), playList.get(onePlaying));
        int Du = mediaPlayer.getDuration();
        seekBar.setMax(Du);
        String musicTime = ex(Du);
        alltime.setText(musicTime);
        mediaPlayer.start();
    }
    private void updateTimer() {
        runOnUiThread(() -> {
            int Ms = mediaPlayer.getCurrentPosition();
            String time = ex(Ms);
            seekBar.setProgress(Ms);
            nowtime.setText(time);
        });
    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.bu_play:
                    Log.e(N,"start");
                    animator.start();
                    prepareMedia();
                    isPlaying = true;
                    break;
                case  R.id.bu_stop:
                    Log.e(N,"stop");
                    animator.pause();
                    mediaPlayer.pause();
                    isPlaying = false;
                    break;
                case  R.id.bu_jixu:
                    Log.e(N,"continue");
                    animator.resume();
                    mediaPlayer.start();
                    isPlaying = true;
                    break;
                case  R.id.bu_exit:
                    Log.e(N,"exit");
                    finish();
                    break;
                case R.id.next:
                    Log.e(N,"next");
                    onePlaying = ++onePlaying % playList.size();
                    prepareMedia();
                    animator.start();
                    break;
                case R.id.last:
                    Log.e(N,"last");
                    animator.start();
                    if (!mediaPlayer.isPlaying()) {
                        onePlaying = --onePlaying % playList.size();
                    }
                    prepareMedia();
                    break;
                default:
                    Log.e(N,"BUG!!!!!!!!");
                    break;
            }
        }
    }
    private class OnSeekBarChangeControl implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mediaPlayer.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mediaPlayer.pause();
            animator.pause();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mediaPlayer.start();
            if (seekBar.getProgress() < 10) {
                animator.start();
            } else {
                animator.resume();
            }
        }
    }
    private void preparePlaylist() {
        Field[] fields = R.raw.class.getFields();
        for (int count = 0; count < fields.length; count++) {
            Log.i("Raw Asset", fields[count].getName());
            try {
                int resId = fields[count].getInt(fields[count]);
                playList.add(resId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public static String ex(int ms){
        Log.e("main",""+ms);
        int sec =ms/1000;
        int min = sec/60;
        sec -= min * 60;
        return String.format("%02d:%02d",min,sec);
    }
    protected void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();

    }
}
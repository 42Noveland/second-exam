package com.example.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayerService extends Service {
    private MediaPlayer mediaPlayer;
    private Timer timer;

    public MediaPlayerService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return new musciControl();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void addTimer(){
        if(timer == null){
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                  int dur = mediaPlayer.getDuration();
                  int curr = mediaPlayer.getCurrentPosition();
                    Message msg ;
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",dur);
                    bundle.putInt("currentPosition",curr);
                }
            };
            timer.schedule(task,5,500);
        }
    }
    public void prepare(){
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            //mediaPlayer.setDataSource("http://m7.music.126.net/20210830133927/f54c102254bd7898af183adf1e18d41c/ymusic/0fd6/4f65/43ed/a8772889f38dfcb91c04da915b301617.mp3");
            mediaPlayer.prepareAsync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    class musciControl extends Binder{
        public void play() throws IOException {
            mediaPlayer.reset();//"http://m7.music.126.net/20210830133927/f54c102254bd7898af183adf1e18d41c/ymusic/0fd6/4f65/43ed/a8772889f38dfcb91c04da915b301617.mp3"
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.pln);
            mediaPlayer.start();
            addTimer();
        }
        public int prepareCu(){
            int cu = mediaPlayer.getCurrentPosition();
            return cu;
        }
        public void pause(){
            mediaPlayer.pause();
        }
        public void resume(){
            mediaPlayer.start();
        }
        public void stop(){
            mediaPlayer.stop();
            mediaPlayer.release();
            try {
                timer.cancel();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        public void seekTo(int m){
            mediaPlayer.seekTo(m);
        }
    }
}
package com.veechand.backgroundserviceexample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

public class MusicService extends Service {
    public MusicService() {
    }
    MediaPlayer mp = null;
    IBinder binder = null;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new BinderService();
        mp = MediaPlayer.create(MusicService.this,R.raw.fumaza);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
        mp.release();
    }

    public void play(){
        mp.start();
    }

    public void pause(){
        mp.pause();
    }

    public String seek() {
        /*while (true){
            Toast.makeText(context,mp.getCurrentPosition()+"/"+mp.getDuration(), Toast.LENGTH_SHORT).show();
            SystemClock.sleep(1000);
        }*/
        return mp.getCurrentPosition() + "/" + mp.getDuration();
    }

    public class BinderService extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }

    }
}

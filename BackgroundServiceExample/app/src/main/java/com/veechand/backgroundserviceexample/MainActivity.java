package com.veechand.backgroundserviceexample;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    Button startButton;
    Button endButton;
    Button bindButton;
    Button playButton;
    Button seekButton;
    Button pauseButton;
    MusicService musicservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.buttonStart);
        endButton = (Button) findViewById(R.id.buttonEnd);
        bindButton = (Button) findViewById(R.id.buttonBind);
        playButton = (Button) findViewById(R.id.buttonPlay);
        seekButton = (Button) findViewById(R.id.buttonSeek);
        pauseButton = (Button) findViewById(R.id.buttonPause);



        startButton.setOnClickListener(this);
        endButton.setOnClickListener(this);
        bindButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        seekButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent musicIntent = new Intent(MainActivity.this,MusicService.class);
        switch (v.getId()){
            case R.id.buttonStart:
                startService(musicIntent);
                break;
            case R.id.buttonEnd:
                stopService(musicIntent);
                break;
            case R.id.buttonBind:
                android.content.ServiceConnection serviceConnection = new MyServiceConnection();
                boolean bindResult = bindService(musicIntent, serviceConnection, BIND_AUTO_CREATE);
                Log.d("MusicService","Bind is "+bindResult);
                break;
            case R.id.buttonPlay:
                musicservice.play();
                break;
            case R.id.buttonPause:
                musicservice.pause();
                break;
            case R.id.buttonSeek:
                String position = musicservice.seek();
                //Toast.makeText(MainActivity.this,position,Toast.LENGTH_LONG).show();
                new SeekUpdater().execute();
                break;
        }


    }

    class MyServiceConnection implements android.content.ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicservice = ((MusicService.BinderService)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicservice = null;

        }
    }

    class SeekUpdater extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {
            while(true){
                publishProgress();
                SystemClock.sleep(1000);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Toast.makeText(MainActivity.this,musicservice.seek(),Toast.LENGTH_LONG).show();
        }
    }
}

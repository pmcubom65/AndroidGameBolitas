package com.example.juegopedromanuelcubomedina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Personaje personaje;
    private Handler handler=new Handler();
    private static final long interval=30;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        personaje=new Personaje(this);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.battle);

        mediaPlayer.start();
        setContentView(personaje);


        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        personaje.invalidate();
                    }
                });
            }
        },0,interval);

    }


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}

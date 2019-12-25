package com.example.juegopedromanuelcubomedina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.app.ActionBar;
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


    private Personaje2 personaje2;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);


        // personaje=new Personaje(this);
        personaje2=new Personaje2(this);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.battle);

        mediaPlayer.start();
        setContentView(personaje2);


        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
            //            personaje.invalidate();
                        personaje2.invalidate();

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

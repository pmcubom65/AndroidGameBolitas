package com.example.juegopedromanuelcubomedina;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ServicioMusica extends Service {

    MediaPlayer mimediaplayer;



    @Override
    public void onCreate() {
        mimediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.battle);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mimediaplayer.start();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        mimediaplayer.stop();
        mimediaplayer.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

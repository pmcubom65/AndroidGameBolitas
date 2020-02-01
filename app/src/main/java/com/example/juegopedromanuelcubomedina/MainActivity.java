package com.example.juegopedromanuelcubomedina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Personaje personaje;
    private Handler handler=new Handler(); //nos permite procesar runnable objects asociados a un thread,
    // se comunica con el main thread de la aplicacion
    private static final long interval=30;

    Timer timer;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);


         personaje=new Personaje(this);

        setContentView(personaje);

        //se genera una tarea repetitiva new Timer().schedule(task, after); (tarea, tiempo ejecución inicial)
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {   //se va añadiendo a la cola de procesos
                    @Override
                    public void run() {
                      personaje.invalidate();   //invalidamos el canvas para que vuelva a llamarse
                    }
                });
            }
        },0,interval);
        intent=new Intent(this, ServicioMusica.class);
        startService(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        stopService(intent);
    }





}

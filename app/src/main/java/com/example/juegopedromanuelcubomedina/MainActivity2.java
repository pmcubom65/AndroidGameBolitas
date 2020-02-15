package com.example.juegopedromanuelcubomedina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity {


    //nos permite procesar runnable objects asociados a un thread, se comunica con el main thread de la aplicacion
    private Handler handler=new Handler();
    private static final long interval=30;

    Intent intent;


    private Personaje2 personaje2;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);


        personaje2=new Personaje2(this);

        setContentView(personaje2);

//se genera una tarea repetitiva new Timer().schedule(task, after); (tarea, tiempo ejecución inicial)
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() { //se va añadiendo a la cola de procesos
                    @Override
                    public void run() {

                        personaje2.invalidate(); //invalidamos el canvas para que vuelva a llamarse

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
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();

        stopService(intent);
    }
}

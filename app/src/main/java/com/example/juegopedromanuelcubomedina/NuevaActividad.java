package com.example.juegopedromanuelcubomedina;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class NuevaActividad extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_actividad);



        Thread mithread=new Thread(){
            @Override
            public void run() {
               try {
                sleep(3000);
               }catch (Exception e) {

               }finally {
                   Intent intent=new Intent(NuevaActividad.this, MainActivity.class);
                   startActivity(intent);
               }
            }
        };
        mithread.start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}

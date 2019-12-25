package com.example.juegopedromanuelcubomedina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class Final extends AppCompatActivity  {

    Button b;
    TextView mitexto;
    String pongo;
    Button estadisticas;
    String total;
    String resultado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final);



        mitexto=(TextView) findViewById(R.id.puntuación);
      estadisticas=(Button) findViewById(R.id.estadisticas);
        b=(Button) findViewById(R.id.empezar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Final.this, MainActivity.class);
                startActivity(intent);
            }
        });
        resultado=getIntent().getStringExtra("puntuacion");
        String segundos=getIntent().getStringExtra("segundos");
        String fecha=getIntent().getStringExtra("fecha");


        pongo=String.format("Resultado: %s",resultado);

        mitexto.setText(pongo);

        Resultados r=new Resultados(this);
        SQLiteDatabase midatabase=r.getWritableDatabase();

        ContentValues contentvalues=new ContentValues();

        //(fecha text, puntuacion text, tiempo text)

        contentvalues.put("fecha", fecha);
        contentvalues.put("puntuacion", resultado);
        contentvalues.put("tiempo", segundos);


       long row= midatabase.insert("resultado",null, contentvalues);


        SQLiteDatabase databaselectura=r.getReadableDatabase();
        String[] columnas={"fecha", "puntuacion", "tiempo"};
       Cursor cursor= databaselectura.query("resultado",columnas,null,null,null,null,"fecha" + " DESC");
        cursor.moveToFirst();

        total="\tFecha\t\t  Score\t\t  Tiempo\n";
        while (cursor.moveToNext()) {
            total=String.format("%s %s \t\t%s \t\t%s\n",total, cursor.getString(0),cursor.getString(1),cursor.getString(2));
        }





    }

    public void dameEstadisticas(View v) {
        Intent ni=new Intent(Final.this, Estadisticas.class);
        ni.putExtra("estadistica",total);
        ni.putExtra("resultado", resultado);

        startActivity(ni);
    }



}

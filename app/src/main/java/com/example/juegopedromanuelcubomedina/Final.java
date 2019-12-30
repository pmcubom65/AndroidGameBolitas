package com.example.juegopedromanuelcubomedina;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.Manifest;
import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;


public class Final extends AppCompatActivity  {

    Button b;
    TextView mitexto;
    String pongo;
    Button estadisticas;
    String total;
    String resultado;
    private final String canal="5555";
    private final int notificationid=001;
    private String numjuego="";




    public static ArrayList<DatoEstadistico> conjunto=new ArrayList<>();



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



                Intent intent=(MenuActividad.numerojuego==1)?new Intent(Final.this, MainActivity.class):new Intent(Final.this, MainActivity2.class);

                startActivity(intent);
            }
        });
        resultado=getIntent().getStringExtra("puntuacion");
        String segundos=getIntent().getStringExtra("segundos");
        String fecha=getIntent().getStringExtra("fecha");
        String numjuego=(MenuActividad.numerojuego==1)?"Juego 1":"Juego 2";


        pongo=String.format("Resultado: %s",resultado);

        mitexto.setText(pongo);

        Resultados r=new Resultados(this);
        SQLiteDatabase midatabase=r.getWritableDatabase();

        ContentValues contentvalues=new ContentValues();



        contentvalues.put("fecha", fecha);
        contentvalues.put("puntuacion", resultado);
        contentvalues.put("tiempo", segundos);
        contentvalues.put("juego", numjuego);

     long row= midatabase.insertWithOnConflict("resultado",null, contentvalues, SQLiteDatabase.CONFLICT_REPLACE);



        System.out.println("insertado "+row);
    midatabase.close();


     contentvalues.clear();



        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");

        SQLiteDatabase databaselectura=r.getReadableDatabase();
        String[] columnas={"fecha", "puntuacion", "tiempo", "juego"};
       Cursor cursor= databaselectura.query("resultado",columnas,null,null,null,null,null);
        cursor.moveToFirst();
        LocalDate ld;
        conjunto.clear();
        do {


            ld=LocalDate.parse(cursor.getString(0), dtf);
            String score=cursor.getString(1);
            String tiempo=cursor.getString(2);
             String numjuegodb=cursor.getString(3);

                conjunto.add(new DatoEstadistico(ld,score ,tiempo, numjuegodb));


        }while (cursor.moveToNext());

        Collections.sort(conjunto, new Comparator<DatoEstadistico>() {
            @Override
            public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                return o1.getFecha().compareTo(o2.getFecha());
            }


        });



    }

    public void dameEstadisticas(View v) {


      Intent ni=new Intent(Final.this, MostrarEstadisticas.class);

        ni.putExtra("resultado", resultado);
        notificationChannel();
        Intent seguirintent=(MenuActividad.numerojuego==1)?new Intent(this,MainActivity.class):new Intent(this,MainActivity2.class);
        seguirintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,seguirintent,PendingIntent.FLAG_ONE_SHOT);

        Intent siintent=(MenuActividad.numerojuego==1)?new Intent(this, MainActivity.class):new Intent(this, MainActivity2.class);
        siintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent sipendingintent=PendingIntent.getActivity(this,0,siintent,PendingIntent.FLAG_ONE_SHOT);


        Intent nointent=new Intent(this, MostrarEstadisticas.class);
        nointent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent nopendingintent=PendingIntent.getActivity(this,0,nointent,PendingIntent.FLAG_ONE_SHOT);





        NotificationCompat.Builder notification=new NotificationCompat.Builder(this, canal);
        notification.setSmallIcon(R.drawable.mono1);
        notification.setContentTitle("El último score de su partida fue "+resultado);
        notification.setContentText("¿Revancha?");
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notification.setAutoCancel(true);
        notification.setContentIntent(pendingIntent);

        notification.addAction(R.drawable.mono1, "Sí", sipendingintent);
        notification.addAction(R.drawable.mono1, "No", nopendingintent);




        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationid, notification.build());


        startActivity(ni);
    }

    public void notificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            String indicar="Última marca "+resultado;
            CharSequence personal=indicar;
            String descripcion=indicar;
            int importancia=NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel=new NotificationChannel(canal, personal, importancia);
            notificationChannel.setDescription(indicar);
            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);



        }
    }



}

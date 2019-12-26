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



public class Final extends AppCompatActivity  {

    Button b;
    TextView mitexto;
    String pongo;
    Button estadisticas;
    String total;
    String resultado;
    private final String canal="5555";
    private final int notificationid=001;



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
                Intent intent=new Intent(Final.this, MenuActividad.class);
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

       if (row>30) {
           midatabase.execSQL("delete from resultado where puntuacion<10");
       }


        SQLiteDatabase databaselectura=r.getReadableDatabase();
        String[] columnas={"fecha", "puntuacion", "tiempo"};
       Cursor cursor= databaselectura.query("resultado",columnas,null,null,null,null,"fecha" + " DESC");
        cursor.moveToFirst();

        total="   Fecha      Score     Tiempo\n";
        while (cursor.moveToNext()) {
            total=String.format("%s  %s    %s   %s\n",total, cursor.getString(0),cursor.getString(1),cursor.getString(2));
        }





    }

    public void dameEstadisticas(View v) {
        Intent ni=new Intent(Final.this, Estadisticas.class);
        ni.putExtra("estadistica",total);
        ni.putExtra("resultado", resultado);
        notificationChannel();
        Intent seguirintent=new Intent(this,Personaje.class);
        seguirintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,seguirintent,PendingIntent.FLAG_ONE_SHOT);

        Intent siintent=new Intent(this, Personaje.class);
        siintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent sipendingintent=PendingIntent.getActivity(this,0,siintent,PendingIntent.FLAG_ONE_SHOT);


        Intent nointent=new Intent(this, Estadisticas.class);
        nointent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent nopendingintent=PendingIntent.getActivity(this,0,nointent,PendingIntent.FLAG_ONE_SHOT);





        NotificationCompat.Builder notification=new NotificationCompat.Builder(this, canal);
        notification.setSmallIcon(R.drawable.mono1);
        notification.setContentTitle("Ultimo score");
        notification.setContentText("El último score es "+resultado);
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

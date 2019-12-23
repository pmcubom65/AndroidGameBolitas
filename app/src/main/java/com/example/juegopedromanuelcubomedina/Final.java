package com.example.juegopedromanuelcubomedina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Final extends AppCompatActivity {

    Button b;
    TextView mitexto;
    String pongo;
    TextView bbdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final);
        mitexto=(TextView) findViewById(R.id.puntuación);
        bbdd=(TextView) findViewById(R.id.bbdd);
        b=(Button) findViewById(R.id.empezar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Final.this, MainActivity.class);
                startActivity(intent);
            }
        });
        String resultado=getIntent().getStringExtra("puntuacion");
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
        System.out.println("los datos son"+fecha+resultado+segundos);

       long row= midatabase.insert("resultado",null, contentvalues);
    System.out.println("insertado "+row);

        SQLiteDatabase databaselectura=r.getReadableDatabase();
        String[] columnas={"fecha", "puntuacion", "tiempo"};
       Cursor cursor= databaselectura.query("resultado",columnas,null,null,null,null,null);
        cursor.moveToFirst();

        String total="\tFecha\t\t  Score\t\t  Tiempo\n";
        while (cursor.moveToNext()) {
            total=String.format("%s %s \t\t%s \t\t%s\n",total, cursor.getString(0),cursor.getString(1),cursor.getString(2));
        }


        bbdd.setText(total);






    }
}

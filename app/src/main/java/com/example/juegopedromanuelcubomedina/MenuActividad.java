package com.example.juegopedromanuelcubomedina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.ListFragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.Inflater;

public class MenuActividad extends AppCompatActivity implements TitularFragment.ItemSelected {

    ListView milista;
    String[] juegos=new String[2];
    ImageView laimagen;
    ArrayList<Integer> descriptions;
    private Toolbar toolbar;

    int indice=3;
    static int numerojuego=1;
    static ND nd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_actividad);

        toolbar=findViewById(R.id.latoolbar);

        setSupportActionBar(toolbar);

        milista=(ListView) findViewById(R.id.lalista);

        laimagen=(ImageView) findViewById(R.id.laimagen);

        descriptions=new ArrayList<>();
        descriptions.add(R.drawable.inicio);
        descriptions.add(R.drawable.inicio2);
        descriptions.add(R.drawable.iraestadisticas);


        laimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (indice==0) {
                    numerojuego=1;
                    Intent intent=new Intent(MenuActividad.this, MainActivity.class);
                    startActivity(intent);
                } else if(indice==1) {
                    numerojuego=2;
                    Intent intent=new Intent(MenuActividad.this, MainActivity2.class);
                    startActivity(intent);
                } else if (indice==2) {

                    Intent intent=new Intent(MenuActividad.this, MostrarEstadisticas.class);
                   rellenarTodo();


                    startActivity(intent);
                }
                else {
                    MiDialogo miDialogo=new MiDialogo();
                    miDialogo.show(getSupportFragmentManager(),"mi dialogo");

                }

            }
        });


        nd=new ND();
    }


    public void rellenarTodo() {
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Resultados r=new Resultados(this);

        SQLiteDatabase databaselectura=r.getReadableDatabase();
        String[] columnas={"fecha", "puntuacion", "tiempo", "juego"};
        Cursor cursor= databaselectura.query("resultado",columnas,null,null,null,null,null);

        LocalDate ld;
        Final.conjunto.clear();
        if ( cursor.moveToFirst()) {
            do {


                ld = LocalDate.parse(cursor.getString(0), dtf);
                String score = cursor.getString(1);
                String tiempo = cursor.getString(2);
                String numjuegodb = cursor.getString(3);

                Final.conjunto.add(new DatoEstadistico(ld, score, tiempo, numjuegodb));


            } while (cursor.moveToNext());

            Collections.sort(Final.conjunto, new Comparator<DatoEstadistico>() {
                @Override
                public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                    return o1.getFecha().compareTo(o2.getFecha());
                }


            });
        }
    }






    @Override
    public void onItemSelected(int index) {
        laimagen.setImageResource(descriptions.get(index));

        indice=index;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menutoolbar2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Snackbar snackbar;
        switch (item.getItemId()) {

            case R.id.ma1:
                nd.setNivel(1);
                snackbar=Snackbar.make(laimagen,"Esta en modo normal", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();

                break;


            case R.id.ma2:
                nd.setNivel(2);
                snackbar=Snackbar.make(laimagen,"Esta en modo muy dificil", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
                break;
        }
        return true;
    }
}

package com.example.juegopedromanuelcubomedina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Comparator;

public class MostrarEstadisticas extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<DatoEstadistico> todoslosdatos;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_estadisticas);
        toolbar=findViewById(R.id.latoolbar);

        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.mireciclador);

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        myAdapter=new MiAdapter(this, Final.conjunto);

        recyclerView.setAdapter(myAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menutoolbar, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.indicador2:
                Final.conjunto.sort(new Comparator<DatoEstadistico>() {
                    @Override
                    public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                        return o1.getTiempo().compareTo(o2.getTiempo());
                    }
                });break;

            case R.id.indicador3:
                Final.conjunto.sort(new Comparator<DatoEstadistico>() {
                    @Override
                    public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                        return o1.getPuntuacion().compareTo(o2.getPuntuacion());
                    }
                });break;
            case R.id.indicador4:
                Final.conjunto.sort(new Comparator<DatoEstadistico>() {
                    @Override
                    public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                        return o1.getFecha().compareTo(o2.getFecha());
                    }
                });break;

        }


        myAdapter=new MiAdapter(this, Final.conjunto);

        recyclerView.setAdapter(myAdapter);

        return super.onOptionsItemSelected(item);
    }
}

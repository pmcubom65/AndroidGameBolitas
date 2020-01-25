package com.example.juegopedromanuelcubomedina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MostrarEstadisticas extends AppCompatActivity implements DialogoBorrados.ValorTecleado {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<DatoEstadistico> todoslosdatos;
    private Toolbar toolbar;
    private DrawerLayout midrawer;
    private NavigationView minavegacion;
    String resultado;
    private boolean ahome=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_estadisticas);

        Intent recibo=getIntent();
        resultado= recibo.getStringExtra("resultado");


        toolbar=findViewById(R.id.latoolbar);

        setSupportActionBar(toolbar);

        final ActionBar miactionbar=getSupportActionBar();
        miactionbar.setDisplayHomeAsUpEnabled(true);
        miactionbar.setHomeAsUpIndicator(R.mipmap.menupop);




        recyclerView=findViewById(R.id.mireciclador);

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        myAdapter=new MiAdapter(this, Final.conjunto);

        recyclerView.setAdapter(myAdapter);


        midrawer=findViewById(R.id.midrawer);
        minavegacion=findViewById(R.id.minavegacion);

        minavegacion.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.mihome:
                        menuItem.setChecked(true);
                        cambioHome();
                        return true;
                    case R.id.miexit:
                        menuItem.setChecked(true);
                        System.exit(0);
                        return true;
                    case R.id.mimapa:
                        menuItem.setChecked(true);
                        cambioGps();
                        return true;

                    case R.id.misport:
                        menuItem.setChecked(true);
                        MiDialogo miDialogo=new MiDialogo();
                        miDialogo.show(getSupportFragmentManager(),"mi dialogo");
                        return true;
                }
                    return false;
            }
        });


    }
    public void cambioHome() {
        startActivity(new Intent(this, NuevaActividad.class));

    }


    public void cambioGps() {

        Intent algps=new Intent(this, MapasGoogle.class);
        algps.putExtra("score",resultado);
        startActivity(algps);

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
                       return -(Integer.parseInt(o1.getTiempo())-Integer.parseInt(o2.getTiempo()));

                      //  return o1.getTiempo().compareTo(o2.getTiempo());
                    }
                });break;

            case R.id.indicador3:
                Final.conjunto.sort(new Comparator<DatoEstadistico>() {
                    @Override
                    public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                        return -(Integer.parseInt(o1.getPuntuacion())-Integer.parseInt(o2.getPuntuacion()));


                        //     return o1.getPuntuacion().compareTo(o2.getPuntuacion());
                    }
                });break;
            case R.id.indicador4:
                Final.conjunto.sort(new Comparator<DatoEstadistico>() {
                    @Override
                    public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                        return o1.getFecha().compareTo(o2.getFecha());
                    }
                });break;

            case android.R.id.home:
                midrawer.openDrawer(GravityCompat.START);
            return true;


            case R.id.indicador5:
         /*          Resultados r=new Resultados(this);
             SQLiteDatabase midatabase=r.getWritableDatabase();
                midatabase.delete("resultado", "cast(puntuacion as unsigned)<1", null);
                List<DatoEstadistico> respaldo=new ArrayList<>();
               respaldo=Final.conjunto.stream().filter(i->(Integer.valueOf(i.getPuntuacion())<10)).collect(Collectors.toList());
                Final.conjunto.removeAll(respaldo); */

        DialogoBorrados dialogo=new DialogoBorrados();
        dialogo.show(getSupportFragmentManager(), "dialogoborrar");

                break;



        }


        myAdapter=new MiAdapter(this, Final.conjunto);

        recyclerView.setAdapter(myAdapter);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onvalorelegido(String s) {
        Resultados r=new Resultados(this);
                SQLiteDatabase midatabase=r.getWritableDatabase();

                int filas=midatabase.delete("resultado", "cast(puntuacion as unsigned)<"+s, null);
                List<DatoEstadistico> respaldo=new ArrayList<>();
               respaldo=Final.conjunto.stream().filter(i->(Integer.valueOf(i.getPuntuacion())<Integer.valueOf(s))).collect(Collectors.toList());
                Final.conjunto.removeAll(respaldo);

        Snackbar snackbar=Snackbar.make(recyclerView,"Se han borrado "+String.valueOf(filas)+ " registros", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();


    }
}

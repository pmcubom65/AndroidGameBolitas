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

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class MostrarEstadisticas extends AppCompatActivity implements DialogoBorrados.ValorTecleado, DialogoActualizados.Datoaactualizar {

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
                       float tiempo1=0,tiempo2=0;
                        boolean esminuto1= (o1.getTiempo().contains("m"))? true:false;
                        boolean esminuto2=(o2.getTiempo().contains("m"))? true:false;


                        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());


                try {
                    tiempo1 = (esminuto1) ? (nf.parse(o1.getTiempo().substring(0, o1.getTiempo().indexOf("("))).floatValue() )* (60) : nf.parse(o1.getTiempo().substring(0, o1.getTiempo().indexOf("("))).floatValue();
                    tiempo2 = (esminuto2) ? (nf.parse(o2.getTiempo().substring(0, o2.getTiempo().indexOf("("))).floatValue() )* (60) : nf.parse(o2.getTiempo().substring(0, o2.getTiempo().indexOf("("))).floatValue();
                }catch (ParseException pe) {
                    System.out.println("error parseo");
                }
             //   System.out.println(tiempo1);
               //         System.out.println(tiempo2);
                       return -(Float.compare((float)tiempo1,(float) tiempo2));

                    }
                });break;

            case R.id.indicador3:
                Final.conjunto.sort(new Comparator<DatoEstadistico>() {
                    @Override
                    public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                        return -(Integer.parseInt(o1.getPuntuacion())-Integer.parseInt(o2.getPuntuacion()));

                    }
                });break;
            case R.id.indicador4:
                Final.conjunto.sort(new Comparator<DatoEstadistico>() {
                    @Override
                    public int compare(DatoEstadistico o1, DatoEstadistico o2) {
                        return -(o1.getFecha().compareTo(o2.getFecha()));
                    }
                });break;

            case android.R.id.home:
                midrawer.openDrawer(GravityCompat.START);
            return true;


            case R.id.indicador5:


        DialogoBorrados dialogo=new DialogoBorrados();
        dialogo.show(getSupportFragmentManager(), "dialogoborrar");

                break;


            case R.id.indicador6:

            DialogoActualizados dialogoActualizados=new DialogoActualizados();
            dialogoActualizados.show(getSupportFragmentManager(), "dialogoactualizar");

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
                midatabase.close();
                myAdapter=new MiAdapter(this, Final.conjunto);

                recyclerView.setAdapter(myAdapter);

        Snackbar snackbar=Snackbar.make(recyclerView,"Se han borrado "+String.valueOf(filas)+ " registros", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();


    }

    @Override
    public void onScoreAActualizar(String s) {
        DatoEstadistico de=null;
        try {
            int scoreelegido = Integer.valueOf(s);
            Resultados r = new Resultados(this);
            SQLiteDatabase midatabase = r.getWritableDatabase();

            ContentValues values = new ContentValues();
            //     DatoEstadistico de=Final.conjunto.stream().filter(i->(Integer.valueOf(i.getPuntuacion())==scoreelegido)).findFirst().get();

            Optional<DatoEstadistico> result = Final.conjunto.stream().filter(i -> (Integer.valueOf(i.getPuntuacion()) == scoreelegido)).findFirst();
            if (result.isPresent()) {
                de = result.get();
                String tiempode=de.getTiempo();

                int tiemposegundos = Integer.valueOf(tiempode.substring(0, tiempode.indexOf("(")));

                String formatted = String.format("%.2f",(float) tiemposegundos / 60);


                String valorminutos = String.format("%s%s", formatted, "(min)");

                values.put("tiempo", valorminutos);
                int filas = midatabase.update("resultado", values, "cast(puntuacion as unsigned)==" + scoreelegido, null);
                Final.conjunto.stream().filter(i -> (Integer.valueOf(i.getPuntuacion()) == scoreelegido)).forEach(i -> i.setTiempo(valorminutos));


                myAdapter=new MiAdapter(this, Final.conjunto);

                recyclerView.setAdapter(myAdapter);


                Snackbar snackbar = Snackbar.make(recyclerView, "Se han actualizado " + filas + " registros", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();


                midatabase.close();


            } else {
                Snackbar snackbar = Snackbar.make(recyclerView, "No se ha encontrado dicho score", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }


        }catch (Exception e) {
            Snackbar snackbar=Snackbar.make(recyclerView,"Ya está en minutos", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }
}

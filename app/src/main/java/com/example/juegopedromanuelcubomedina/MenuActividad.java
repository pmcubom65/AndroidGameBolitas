package com.example.juegopedromanuelcubomedina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActividad extends AppCompatActivity implements TitularFragment.ItemSelected {

    ListView milista;
    String[] juegos=new String[2];
    ImageView laimagen;
    ArrayList<Integer> descriptions;

    int indice=2;
    static int numerojuego=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_actividad);

        milista=(ListView) findViewById(R.id.lalista);
        laimagen=(ImageView) findViewById(R.id.laimagen);

        descriptions=new ArrayList<>();
        descriptions.add(R.drawable.inicio);
        descriptions.add(R.drawable.inicio2);


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
                } else {
                    MiDialogo miDialogo=new MiDialogo();
                    miDialogo.show(getSupportFragmentManager(),"mi dialogo");

                }

            }
        });



    }


    @Override
    public void onItemSelected(int index) {
        laimagen.setImageResource(descriptions.get(index));

        indice=index;
    }
}

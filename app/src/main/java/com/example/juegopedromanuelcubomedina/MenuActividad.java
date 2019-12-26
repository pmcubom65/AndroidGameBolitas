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

    int indice=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_actividad);

        milista=(ListView) findViewById(R.id.lalista);
        laimagen=(ImageView) findViewById(R.id.laimagen);
     /*   juegos[0]="Juego 1";
        juegos[1]="Juego 2";

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, juegos);
        milista.setAdapter(arrayAdapter);*/

        descriptions=new ArrayList<>();
        descriptions.add(R.drawable.inicio);
        descriptions.add(R.drawable.inicio2);


        laimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (indice==0) {
                    Intent intent=new Intent(MenuActividad.this, MainActivity.class);
                    startActivity(intent);
                } else if(indice==1) {
                    Intent intent=new Intent(MenuActividad.this, MainActivity2.class);
                    startActivity(intent);
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

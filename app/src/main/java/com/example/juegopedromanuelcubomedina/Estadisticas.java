package com.example.juegopedromanuelcubomedina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

public class Estadisticas extends AppCompatActivity {

    TextView mitexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_estadisticas);
        Intent recibo=getIntent();
        mitexto=(TextView) findViewById(R.id.bbdd);
        String estad=recibo.getStringExtra("estadistica");
        String notif=recibo.getStringExtra("resultado");

        mitexto.setText(estad);

    }
}

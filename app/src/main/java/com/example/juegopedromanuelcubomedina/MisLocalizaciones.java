package com.example.juegopedromanuelcubomedina;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MisLocalizaciones extends SQLiteOpenHelper {

    public MisLocalizaciones(@Nullable Context context) {
        super(context, "localizaciones.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table lospuntos (puntuacion text PRIMARY KEY, latitud text, longitud text, unique(puntuacion, latitud, longitud))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists lospuntos");


        onCreate(db);
    }
}

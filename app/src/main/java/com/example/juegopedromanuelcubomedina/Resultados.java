package com.example.juegopedromanuelcubomedina;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Resultados extends SQLiteOpenHelper {

    public Resultados(@Nullable Context context) {
        super(context, "resultados.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table resultado (fecha text, puntuacion text, tiempo text, juego text, ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, unique(fecha,puntuacion,tiempo))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists resultado");


        onCreate(db);
    }
}

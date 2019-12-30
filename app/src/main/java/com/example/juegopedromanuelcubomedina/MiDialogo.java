package com.example.juegopedromanuelcubomedina;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MiDialogo extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder midialogo=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        midialogo.setView(inflater.inflate(R.layout.dialogo_layout,null));
        midialogo.setPositiveButton("Juego 2", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MenuActividad.numerojuego=2;
                Intent intent=new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);
            }
        });
        midialogo.setNegativeButton("Juego 1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MenuActividad.numerojuego=1;
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });




        return midialogo.create();


    }
}

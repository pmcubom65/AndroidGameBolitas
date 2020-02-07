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

import com.example.juegopedromanuelcubomedina.MainActivity;
import com.example.juegopedromanuelcubomedina.MainActivity2;
import com.example.juegopedromanuelcubomedina.MenuActividad;
import com.example.juegopedromanuelcubomedina.R;

public class MiDialogo2 extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder midialogo=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        midialogo.setView(inflater.inflate(R.layout.dialogo_marcador, null));
        midialogo.setPositiveButton("Grabar Marcador", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent algps=new Intent(getActivity(), MapasGoogle.class);
                algps.putExtra("score",MostrarEstadisticas.resultado);

                startActivity(algps);
            }
        });
        midialogo.setNegativeButton("Ver Marcadores", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent algps=new Intent(getActivity(), MapasGoogle.class);
            //    algps.putExtra("score",MostrarEstadisticas.resultado);

                startActivity(algps);
            }
        });




        return midialogo.create();


    }
}

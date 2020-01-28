package com.example.juegopedromanuelcubomedina;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class DialogoActualizados extends DialogFragment {





    public interface Datoaactualizar {

        public void onScoreAActualizar(String s);
    }

    Datoaactualizar miscoreactualizar;
    EditText miedit;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View layoutactualizar=inflater.inflate(R.layout.actualizados,null);

        miedit=(EditText) layoutactualizar.findViewById(R.id.scoreactualizar);
        builder.setView(layoutactualizar);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String revisar=miedit.getText().toString();

                try {
                    int test=Integer.parseInt(revisar);
                    miscoreactualizar.onScoreAActualizar(revisar);
                }catch (Exception e) {
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.poneraqui), "No se ha encontrado dicho score", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                }





            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity=(Activity) context;

        try {
            miscoreactualizar=(Datoaactualizar) activity;
        }catch (ClassCastException cce) {}

    }
}

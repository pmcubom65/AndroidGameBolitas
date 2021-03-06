package com.example.juegopedromanuelcubomedina;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class DialogoBorrados extends DialogFragment {


    public interface ValorTecleado {

        public void onvalorelegido(String s);
    }

    ValorTecleado mivalor;
    EditText miedit;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View miview=inflater.inflate(R.layout.borrados,null);
        miedit=(EditText) miview.findViewById(R.id.scoreborrar);
        builder.setView(miview);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            try {
                String revisar = miedit.getText().toString();
                int test = Integer.parseInt(revisar);
                mivalor.onvalorelegido(revisar);
            }catch ( Exception e) {
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


    //inicializar el listener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity=(Activity) context;
        try {
            mivalor=(ValorTecleado) activity;
        } catch (ClassCastException cce){}


    }
}

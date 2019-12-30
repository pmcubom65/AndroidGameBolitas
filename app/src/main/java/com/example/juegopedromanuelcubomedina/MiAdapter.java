package com.example.juegopedromanuelcubomedina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

public class MiAdapter extends RecyclerView.Adapter<MiAdapter.ViewHolder> {

    private ArrayList<DatoEstadistico> datos;

    public MiAdapter(Context context, ArrayList<DatoEstadistico> list) {
        datos=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1,tv2,tv3, tv4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.tv1);
            tv2=itemView.findViewById(R.id.tv2);
            tv3=itemView.findViewById(R.id.tv3);
            tv4=itemView.findViewById(R.id.tv4);
        }
    }

    @NonNull
    @Override
    public MiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(datos.get(position));
        holder.tv1.setText(datos.get(position).getFecha().toString());
        holder.tv2.setText(datos.get(position).getPuntuacion());
        holder.tv3.setText(datos.get(position).getTiempo());
        holder.tv4.setText(datos.get(position).getNumjuego());


    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
}

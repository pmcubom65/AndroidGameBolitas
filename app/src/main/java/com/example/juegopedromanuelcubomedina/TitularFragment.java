package com.example.juegopedromanuelcubomedina;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

public class TitularFragment extends ListFragment {

    ItemSelected activity;


    public interface ItemSelected {
        void onItemSelected(int index);
    }




    public TitularFragment() {}

 /*   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.titular_fragment_layout,container,false);
        return view;
    }*/


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity=(ItemSelected) context;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ArrayList<String> data=new ArrayList<>();
        data.add("Juego 1: Recoge las bolas, esquiva las rojas");
        data.add("Juego 2: Recoge todas las gotas");

        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data));



    }


    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
            activity.onItemSelected(position);
    }
}

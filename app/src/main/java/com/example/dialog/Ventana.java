package com.example.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Ventana extends DialogFragment {
    private MainActivity mainActivity;

    public Ventana(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Titulo de la ventana");
        //builder.setMessage("Esta seguro?");

        //incrusta xml en el dialog, en este casa ventana.xml
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ventana,null);
        builder.setView(view);

        //botones
        //con el view
        ClickDialogo clickListener = new ClickDialogo(view, this.mainActivity);
        //sin el view
        //ClickDialogo clickListener = new ClickDialogo();
        builder.setNegativeButton("CERRAR", clickListener);
        //builder.setNeutralButton("Nada", clickListener);
        builder.setPositiveButton("GUARDAR", clickListener);

        //setear lista, no puede tener setview ni message (linea 19)
        /*String[] lista = new String[]{"opcion 1", "opcion 2"};
        builder.setItems(lista,clickListener);*/


        return builder.create();
    }
}

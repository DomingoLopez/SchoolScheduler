package com.classescalendar.classescalendar.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.classescalendar.classescalendar.R;
import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

import java.util.ArrayList;
import java.util.List;

public class ConflictDialog extends DialogFragment {

    public interface NoticeDialogListener {
        public void onElegida(int elegida, String[] names);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    List<AsignaturaEntity> asignaturas;
    String[] asig_names;
    String[] asig_names_modalidad;
    int elegida = 9999;
    NoticeDialogListener listener;



    public ConflictDialog(List<AsignaturaEntity> asigs, NoticeDialogListener list){
        this.asignaturas = asigs;
        this.asig_names = new String[asignaturas.size()];
        this.asig_names_modalidad = new String[asignaturas.size()];
        this.listener = list;
        setAsigNames();


    }

    private void setAsigNames() {
        for(int i = 0; i< this.asignaturas.size(); i++){
           asig_names[i] = this.asignaturas.get(i).abr;
           asig_names_modalidad[i] = this.asignaturas.get(i).abr + "("+this.asignaturas.get(i).modalidad+")";
        }
    }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Selecciona una asignatura")
                    .setItems(this.asig_names_modalidad, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            elegida = which;
                            listener.onElegida(elegida,asig_names);
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }





}


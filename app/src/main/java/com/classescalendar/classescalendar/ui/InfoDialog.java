package com.classescalendar.classescalendar.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.classescalendar.classescalendar.R;
import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

import java.util.List;

public class InfoDialog extends DialogFragment {

    public interface InfoDialogListener {
        public void aEliminar(String abr);
    }


    AsignaturaEntity asignatura;
    InfoDialogListener listener;

    TextView title;
    TextView description;
    TextView curso;
    TextView semestre;
    TextView horario;
    TextView credits;

    String[]nombre_dias = {"","Lunes","Martes","Miércoles","Jueves","Viernes"};



    public InfoDialog(AsignaturaEntity asig, InfoDialogListener list){

        this.asignatura = asig;
        this.listener = list;
    }



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.info_dialog,null);
            builder.setView(view);

            this.title = view.findViewById(R.id.tvNombreAsig);
            this.description = view.findViewById(R.id.tvDescriptionAsig);
            this.semestre = view.findViewById(R.id.tvSemestre);
            this.credits = view.findViewById(R.id.tvCredits);
            this.curso = view.findViewById(R.id.tvCurso);
            this.horario = view.findViewById(R.id.tvHorario);

            this.title.setText(this.asignatura.nombreCompleto);
            this.description.setText(this.asignatura.descripcion);
            this.curso.setText(Integer.toString(this.asignatura.curso));
            this.semestre.setText(Integer.toString(this.asignatura.cuatrimestre));
            this.credits.setText(Integer.toString(this.asignatura.creditos));
            this.horario.setText(nombre_dias[this.asignatura.dia]+": "+this.asignatura.hora_ini +":00 - "+ this.asignatura.hora_fin + ":00");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            }).setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                   listener.aEliminar(asignatura.abr);
                }
            });
            return builder.create();
        }





}


package com.classescalendar.classescalendar.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.classescalendar.classescalendar.R;
import com.classescalendar.classescalendar.classes.Cell;

import java.util.ArrayList;

public class CellAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Cell> cells;


    public CellAdapter(Context context, ArrayList<Cell> cells) {
        this.context = context;
        this.cells = cells;
    }

    @Override
    public int getCount() {
        return this.cells.size();
    }

    @Override
    public Cell getItem(int position) {
        return this.cells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cell, viewGroup, false);
        }

        TextView texto = (TextView) view.findViewById(R.id.text_cell);
        Cell item = getItem(position);

        switch (item.getType()){
            case ASIGNATURAS:

                if(item.getAsignaturasEntity().size() > 1){
                    //Si hay más de una asignatura en la misma celda
                    view.setBackgroundColor(Color.parseColor("#c62828"));
                    texto.setText("INCOMP");
                }else if(item.getAsignaturasEntity().size() == 1){
                    texto.setText(item.getAsignaturasEntity().get(0).abr + " ("+item.getAsignaturasEntity().get(0).modalidad+")");
                    view.setBackgroundColor(Color.parseColor(item.getAsignaturasEntity().get(0).getColor()));
                }else{
                    texto.setText("");
                }

                break;

            default:
                texto.setText(item.getNombre());
                view.setBackgroundColor(Color.parseColor("#efefef"));
                break;

        }


        return view;
    }

}
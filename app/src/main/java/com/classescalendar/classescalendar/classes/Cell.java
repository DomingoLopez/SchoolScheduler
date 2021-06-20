package com.classescalendar.classescalendar.classes;

import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

import java.util.List;


public abstract class Cell {

    private CellType type;


    public Cell(CellType type){

        this.type = type;
    }



    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }


    //Métodos abstractos para la clase informativa
    public abstract  void setNombre(String nombre);
    public abstract String getNombre();


    //Métodos abstractos para la clase asignatura
    public abstract List<AsignaturaEntity> getAsignaturasEntity();

    public abstract  void setAsignaturasEntity(List<AsignaturaEntity> asignaturasEntity);

    public abstract  void setAsignatura(AsignaturaEntity asig);
}

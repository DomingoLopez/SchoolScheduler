package com.classescalendar.classescalendar.classes;

import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

import java.util.List;

public class CellAsignaturas extends  Cell{

    private List<AsignaturaEntity> asignaturasEntity;

    public CellAsignaturas(CellType type ,List<AsignaturaEntity> asignaturas) {
        super(type);
        this.asignaturasEntity = asignaturas;
    }


    public List<AsignaturaEntity> getAsignaturasEntity() {
        return this.asignaturasEntity;
    }

    public void setAsignaturasEntity(List<AsignaturaEntity> asignaturasEntity) {
        this.asignaturasEntity = asignaturasEntity;
    }

    public void setAsignatura(AsignaturaEntity asig){
        this.asignaturasEntity.add(asig);
    }



    @Override
    public void setNombre(String nombre) {

    }

    @Override
    public String getNombre() {
        return "";
    }

}

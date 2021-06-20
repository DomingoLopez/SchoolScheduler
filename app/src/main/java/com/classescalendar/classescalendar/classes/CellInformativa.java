package com.classescalendar.classescalendar.classes;

import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

import java.util.List;

public class CellInformativa extends Cell{

    String nombre;

    public CellInformativa(CellType type, String nombre) {
        super(type);
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public List<AsignaturaEntity> getAsignaturasEntity() {
        return null;
    }

    @Override
    public void setAsignaturasEntity(List<AsignaturaEntity> asignaturasEntity) {

    }

    @Override
    public void setAsignatura(AsignaturaEntity asig) {

    }
}

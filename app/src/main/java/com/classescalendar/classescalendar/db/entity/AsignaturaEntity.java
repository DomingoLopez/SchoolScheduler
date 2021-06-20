package com.classescalendar.classescalendar.db.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "ASIGNATURAS")
public class AsignaturaEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int curso;
    public int cuatrimestre;
    public String grupo;
    public String abr;
    public String nombreCompleto;
    public String descripcion;
    public int creditos;
    public int hora_ini;
    public int hora_fin;
    public int dia;
    public String modalidad;
    public String color;

    public AsignaturaEntity(int curso,
                            int cuatrimestre,
                            String grupo,
                            String abr,
                            String nombreCompleto,
                            String descripcion,
                            int creditos,
                            int hora_ini,
                            int hora_fin,
                            int dia,
                            String modalidad,
                            String color){
        this.curso = curso;
        this.cuatrimestre = cuatrimestre;
        this.grupo = grupo;
        this.abr = abr;
        this.nombreCompleto = nombreCompleto;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.hora_ini = hora_ini;
        this.hora_fin = hora_fin;
        this.dia = dia;
        this.modalidad = modalidad;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public String getAbr() {
        return abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getHora_ini() {
        return hora_ini;
    }

    public void setHora_ini(int hora_ini) {
        this.hora_ini = hora_ini;
    }

    public int getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(int hora_fin) {
        this.hora_fin = hora_fin;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

package com.classescalendar.classescalendar.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

import java.util.List;

@Dao
public interface AsignaturaDao {

    @Insert
    void insert(AsignaturaEntity placeEntity);

    @Update
    void update(AsignaturaEntity placeEntity);

    @Query("DELETE FROM ASIGNATURAS")
    void deleteAll();

    @Query("DELETE FROM ASIGNATURAS WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM ASIGNATURAS WHERE id = :id")
    AsignaturaEntity getAsignaturaById(int id);

    @Query("SELECT * FROM ASIGNATURAS")
    List<AsignaturaEntity> getAllAsignaturas();

    @Query("SELECT * FROM ASIGNATURAS WHERE curso = :curso")
    List<AsignaturaEntity> getAsignaturasByCurso(int curso);

    @Query("SELECT * FROM ASIGNATURAS WHERE curso IN (:cursos)")
    List<AsignaturaEntity> getAsignaturasByCursos(List<Integer> cursos);

    @Query("SELECT * FROM ASIGNATURAS WHERE curso = :curso AND cuatrimestre = :cuatrimestre")
    List<AsignaturaEntity> getAsignaturasByCursoYCuatrimestre(int curso, int cuatrimestre);

    @Query("SELECT * FROM ASIGNATURAS WHERE curso = :curso AND cuatrimestre = :cuatrimestre AND grupo = :grupo")
    List<AsignaturaEntity> getAsignaturasByCursoYCuatrimestreYGrupo(int curso, int cuatrimestre, String grupo);

    @Query("SELECT * FROM ASIGNATURAS WHERE abr IN (:asigs_abr)")
    List<AsignaturaEntity> getAsignaturasByABR(List<String> asigs_abr);

}
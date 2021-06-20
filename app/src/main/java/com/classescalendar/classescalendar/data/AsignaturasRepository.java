package com.classescalendar.classescalendar.data;

import android.app.Application;
import android.os.AsyncTask;

import com.classescalendar.classescalendar.db.ClassesCalendarDataBase;
import com.classescalendar.classescalendar.db.dao.AsignaturaDao;
import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

import java.util.List;

public class AsignaturasRepository{


    private AsignaturaDao asignaturaDao;



    public AsignaturasRepository(Application application) {

        ClassesCalendarDataBase db = ClassesCalendarDataBase.getDataBase(application);
        asignaturaDao = db.getAsignaturaDao();

    }


    public List<AsignaturaEntity> getAllAsignaturas(){
       return this.asignaturaDao.getAllAsignaturas();

    }

    public AsignaturaEntity getAsignaturaById(int id){
       return this.asignaturaDao.getAsignaturaById(id);

    }


    public List<AsignaturaEntity> getAsignaturasByCurso(int curso){
        return this.asignaturaDao.getAsignaturasByCurso(curso);
    }

    public List<AsignaturaEntity> getAsignaturasByCursos(List<Integer> cursos){
        return this.asignaturaDao.getAsignaturasByCursos(cursos);
    }

    public List<AsignaturaEntity> getAsignaturasByABR(List<String> asigs_abr){
        return this.asignaturaDao.getAsignaturasByABR(asigs_abr);
    }

    public List<AsignaturaEntity> getAsignaturasByCursoYCuatrimestre(int curso,int cuatrimestre){
        return this.asignaturaDao.getAsignaturasByCursoYCuatrimestre(curso,cuatrimestre);
    }


    public List<AsignaturaEntity> getAsignaturasByCursoYCuatrimestreYGrupo(int curso,int cuatrimestre,String grupo){
        return this.asignaturaDao.getAsignaturasByCursoYCuatrimestreYGrupo(curso,cuatrimestre, grupo);
    }

    public void insertAsignatura(AsignaturaEntity asignatura){
        new insertAsignaturaAsyncTask(asignaturaDao).execute(asignatura);

    }

    public void updatePlace(AsignaturaEntity asignatura){
        new updateAsignaturaAsyncTask(asignaturaDao).execute(asignatura);

    }



    private static class insertAsignaturaAsyncTask extends AsyncTask<AsignaturaEntity, Void, Void> {

        private AsignaturaDao asignaturaDaoAsyncTask;


        insertAsignaturaAsyncTask(AsignaturaDao asignaturaDao){
            asignaturaDaoAsyncTask = asignaturaDao;
        }

        @Override
        protected Void doInBackground(AsignaturaEntity... asignaturaEntities) {

            asignaturaDaoAsyncTask.insert(asignaturaEntities[0]);
            return null;
        }
    }

    private static class updateAsignaturaAsyncTask extends AsyncTask<AsignaturaEntity, Void, Void>{

        private AsignaturaDao asignaturaDaoAsyncTask;


        updateAsignaturaAsyncTask(AsignaturaDao asignaturaDao){
            asignaturaDaoAsyncTask = asignaturaDao;
        }

        @Override
        protected Void doInBackground(AsignaturaEntity... placeEntities) {

            asignaturaDaoAsyncTask.update(placeEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }




}
package com.classescalendar.classescalendar.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.classescalendar.classescalendar.db.dao.AsignaturaDao;
import com.classescalendar.classescalendar.db.entity.AsignaturaEntity;

@Database(entities = {AsignaturaEntity.class}, version = 1, exportSchema = false)
public abstract class ClassesCalendarDataBase extends RoomDatabase {

    public abstract AsignaturaDao getAsignaturaDao();

    private static volatile ClassesCalendarDataBase INSTANCE;


    public static ClassesCalendarDataBase getDataBase(final Context context){

        if(INSTANCE == null){
            synchronized (ClassesCalendarDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ClassesCalendarDataBase.class,"CLASSES")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;

    }

}
package com.apps.aggr.roomapp.db.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.apps.aggr.roomapp.constants.Constants;
import com.apps.aggr.roomapp.db.dao.ProfesorDAO;
import com.apps.aggr.roomapp.db.entity.Profesor;



@Database(entities = {Profesor.class}, version = 1)
public abstract class db extends RoomDatabase {
    private static db INSTANCE;
    public abstract ProfesorDAO profesorDAO();

    public static db getdb(Context c){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(c.getApplicationContext(), db.class, Constants.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance(){
        INSTANCE = null;
    }
}

package com.apps.aggr.roomapp.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.apps.aggr.roomapp.db.entity.Profesor;

import java.util.List;

@Dao
public interface ProfesorDAO {
    @Insert
    void insertProfesor(Profesor profesor);

    @Query("SELECT * FROM professor")
    List<Profesor> selectAllProfesors();

    @Query("SELECT * FROM professor WHERE id LIKE :id")
    Profesor selectProfesorById (int id);

    @Update
    void updateProfesorById(Profesor profesor);

    @Query("DELETE FROM professor")
    void deleteAllProfesors();

    @Delete
    void deleteProfesorByyId(Profesor profesor);
}

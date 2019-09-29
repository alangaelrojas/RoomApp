package com.apps.aggr.roomapp.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.apps.aggr.roomapp.constants.Constants;

@Entity(tableName = Constants.TABLE_NAME_PROFESSOR)
public class Profesor {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "isActive")
    private boolean isActive;
    @ColumnInfo(name = "typeProfesor")
    private String typeProfesor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTypeProfesor() {
        return typeProfesor;
    }

    public void setTypeProfesor(String typeProfesor) {
        this.typeProfesor = typeProfesor;
    }
}

package com.e.weatherappassignment.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    Long insertTask(Favourite note);


    @Query("SELECT * FROM Favourite ORDER BY created_at desc")
    List<Favourite> retrieveAllTasks();


    @Query("SELECT * FROM Favourite WHERE id =:taskId")
    LiveData<Favourite> getTask(int taskId);


    @Update
    void updateTask(Favourite note);


    @Delete
    void deleteTask(Favourite note);


    @Query("DELETE FROM Favourite WHERE id = :id")
    public void deleteById(String id);
}
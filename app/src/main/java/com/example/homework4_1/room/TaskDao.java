package com.example.homework4_1.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.homework4_1.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllive();

    @Delete
    void delete(Task task);

    @Insert
    void insert(Task task);
}

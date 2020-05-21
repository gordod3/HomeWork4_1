package com.example.homework4_1.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.homework4_1.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task ORDER BY title")
    List<Task> getSortedAll();

    @Query("SELECT * FROM Task ORDER BY id DESC")
    LiveData<List<Task>> getAllLive();

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Insert
    void insert(Task task);

}

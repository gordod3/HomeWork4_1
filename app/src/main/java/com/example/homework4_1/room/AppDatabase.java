package com.example.homework4_1.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.homework4_1.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}

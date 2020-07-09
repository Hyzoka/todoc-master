package com.clean.up.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.clean.up.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTasks();//  to get projects from table (repository) / READ

    @Insert
    void insertTask(Task task); //  for test and repository / CREATE

    @Delete
    void deleteTask(Task task);//  for test and repository / DELETE
}

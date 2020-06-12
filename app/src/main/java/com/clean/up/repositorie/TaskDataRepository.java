package com.clean.up.repositorie;


import android.arch.lifecycle.LiveData;

import com.clean.up.dao.TaskDao;
import com.clean.up.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    // --- GET ---

    public LiveData<List<Task>> getTasks() {
        return mTaskDao.getTasks();
    }

    // --- CREATE ---

    public void createTask(Task task) {
        mTaskDao.insertTask(task);
    }

    // --- DELETE ---

    public void deleteTask(Task task) {
        mTaskDao.deleteTask(task);
    }
}

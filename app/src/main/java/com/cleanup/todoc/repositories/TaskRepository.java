package com.cleanup.todoc.repositories;


import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.pojo.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao mTaskDao;



    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;

    }

    public LiveData<List<Task>> getListTasks() {
        return mTaskDao.getMyListTasks();
    }


   public void insert(final Task task) {
        mTaskDao.insert(task);
    }


   public void deleteTask(Task task){
        mTaskDao.deleteTask(task);

    }



}

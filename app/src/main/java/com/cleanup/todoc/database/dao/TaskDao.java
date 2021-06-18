package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.pojo.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert (Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM task ORDER BY id")
    LiveData<List<Task>> getMyListTasks();

}

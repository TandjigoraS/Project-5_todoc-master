package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.pojo.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert (Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getMyListTasks();

}

package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.pojo.Project;

@Dao
public interface ProjectDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void addProject(Project project);

    @Query("SELECT * FROM project")
    LiveData<Project[]> getAllProjects();


}

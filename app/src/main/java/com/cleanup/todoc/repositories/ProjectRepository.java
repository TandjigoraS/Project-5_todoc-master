package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.pojo.Project;


public class ProjectRepository {

    private final ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    public LiveData<Project[]> getAllProjects(){ return mProjectDao.getAllProjects();}
}

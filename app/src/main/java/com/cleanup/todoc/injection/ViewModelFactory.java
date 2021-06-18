package com.cleanup.todoc.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.MainApplication;
import com.cleanup.todoc.model.AddTaskViewModel;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.model.TaskViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;




    public ViewModelFactory(@NonNull TaskRepository taskRepository, ProjectRepository projectRepository) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(
                    mTaskRepository
            );
        }
        if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
                return (T) new AddTaskViewModel(
                        mTaskRepository, mProjectRepository, MainApplication.getApplication()
                );

        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}

package com.cleanup.todoc.model;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.R;
import com.cleanup.todoc.pojo.Project;
import com.cleanup.todoc.pojo.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.utils.singlelivedata.SingleLiveEvent;

import java.time.LocalDateTime;

public class AddTaskViewModel extends ViewModel {

    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Application mApplication;
    private final MutableLiveData<AddTaskViewState> mAddTaskViewStateMutableLiveData = new MutableLiveData<>();
    private final MediatorLiveData<Project[]> mListProjectsMediatorLiveData = new MediatorLiveData<>();
    private final SingleLiveEvent<Boolean> taskIsAdded = new SingleLiveEvent<>();


    public AddTaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Application application) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mApplication = application;

        LiveData<Project[]> allProjects = mProjectRepository.getAllProjects();

        mListProjectsMediatorLiveData.addSource(allProjects, mListProjectsMediatorLiveData::setValue);

    }

    public void addTask(Project taskProject, String taskName, LocalDateTime localDateTime) {
        String taskNameError;
        boolean hasTaskProject = false;

        if (taskProject != null) {
            hasTaskProject = true;
        }

        if (TextUtils.isEmpty(taskName)) {
            taskNameError = mApplication.getString(R.string.empty_task_name);
        } else {
            taskNameError = null;
        }

        if (hasTaskProject && taskNameError == null) {
            mTaskRepository.insert(new Task(taskProject.getId(), taskName, localDateTime));
            taskIsAdded.setValue(true);

        } else {
            mAddTaskViewStateMutableLiveData.setValue(new AddTaskViewState(taskNameError, hasTaskProject));
        }
    }

    public SingleLiveEvent<Boolean> getTaskIsAdded() {
        return taskIsAdded;
    }

    public LiveData<AddTaskViewState> getAddTaskViewStateMutableLiveData() {
        return mAddTaskViewStateMutableLiveData;
    }

    public LiveData<Project[]> getMyListProjects(){
        return mListProjectsMediatorLiveData;
    }

}

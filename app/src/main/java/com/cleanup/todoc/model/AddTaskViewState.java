package com.cleanup.todoc.model;

public class AddTaskViewState {

    private final String mTaskNameError;
    private final boolean hasTaskProject;

    public AddTaskViewState(String taskNameError, boolean hasTaskProject) {
        mTaskNameError = taskNameError;
        this.hasTaskProject = hasTaskProject;

    }

    public String getTaskNameError() {
        return mTaskNameError;
    }

    public boolean isHasTaskProject() {
        return hasTaskProject;
    }
}

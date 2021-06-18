package com.cleanup.todoc.model;

import com.cleanup.todoc.pojo.Task;

import java.util.List;

public class TaskViewState {

    private final int mStateRecyclerView;
    private final int mStateTextView;
    private final List<Task> mTaskList;

    public TaskViewState(int stateRecyclerView, int stateTextView, List<Task> taskList) {
        mStateRecyclerView = stateRecyclerView;
        mStateTextView = stateTextView;
        mTaskList = taskList;
    }

    public List<Task> getTaskList() {
        return mTaskList;
    }

    public int getStateRecyclerView() {
        return mStateRecyclerView;
    }

    public int getStateTextView() {
        return mStateTextView;
    }

}

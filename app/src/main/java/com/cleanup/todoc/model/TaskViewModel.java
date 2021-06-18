package com.cleanup.todoc.model;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.pojo.Task;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.sort.SortingType;

import java.util.Collections;
import java.util.List;

public class TaskViewModel extends ViewModel {
    private final TaskRepository mTaskRepository;
    private final LiveData<List<Task>> mListTasks;
    private final MediatorLiveData<TaskViewState> mViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<SortingType> mSortingTypeMutableLiveData = new MutableLiveData<>();



    public TaskViewModel(TaskRepository taskRepository) {
        mTaskRepository = taskRepository;
        mListTasks = mTaskRepository.getListTasks();


        mViewStateMediatorLiveData.addSource(mListTasks, tasks -> combine(tasks,
                mSortingTypeMutableLiveData.getValue()));

        mViewStateMediatorLiveData.addSource(mSortingTypeMutableLiveData, sortingType -> combine(mListTasks.getValue(),
                sortingType));



    }

    private void combine(List<Task> myListTasks,
                         @Nullable SortingType sortingType) {

        Collections.sort(myListTasks, (task1, task2) -> compareTasks(task1,
                task2,
                sortingType));

        int stateRecyclerView;
        int stateTextView;
        if (myListTasks.size() == 0) {
            stateRecyclerView = View.GONE;
            stateTextView = View.VISIBLE;
        } else {
            stateRecyclerView = View.VISIBLE;
            stateTextView = View.GONE;
        }

        mViewStateMediatorLiveData.setValue(new TaskViewState(stateRecyclerView, stateTextView, myListTasks));

    }


    private int compareTasks(
            Task task1,
            Task task2,
            @Nullable SortingType sortingType) {


       if (sortingType != null) {
             return sortingType.getTaskComparator().compare(task1, task2);

        }
        return (int) (task1.getId() - task2.getId());
    }


    public void sortNewestFirst() {

        SortingType recentFirst = SortingType.RECENT_FIRST;

        mSortingTypeMutableLiveData.setValue(recentFirst);
    }

    public void sortOldestFirst() {

        SortingType oldFirst = SortingType.OLD_FIRST;

        mSortingTypeMutableLiveData.setValue(oldFirst);
    }

    public void sortAlphabetical() {

        SortingType alphabetical = SortingType.ALPHABETICAL;

        mSortingTypeMutableLiveData.setValue(alphabetical);
    }

    public void sortAlphabeticalInverted() {

        SortingType alphabeticalInverted = SortingType.ALPHABETICAL_INVERTED;

        mSortingTypeMutableLiveData.setValue(alphabeticalInverted);
    }


    public LiveData<TaskViewState> getTasksViewStateLiveData() {
        return mViewStateMediatorLiveData;
    }


    public void deleteTask(Task task) {
        mTaskRepository.deleteTask(task);
    }

}

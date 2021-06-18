package com.cleanup.todoc;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.cleanup.todoc.model.AddTaskViewModel;
import com.cleanup.todoc.pojo.Project;
import com.cleanup.todoc.pojo.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class AddTaskUnitTest {
    @Rule

    public final InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    private final TaskRepository mTaskRepository = Mockito.mock(TaskRepository.class);
    private final ProjectRepository mProjectRepository = Mockito.mock(ProjectRepository.class);

    private AddTaskViewModel mAddTaskViewModel;



    @Before
    public void setUp() {


        mAddTaskViewModel = new AddTaskViewModel(mTaskRepository, mProjectRepository, MainApplication.getApplication());

    }

    @Test
    public void when_addTask() {

        //Given
        Task taskToAdd = new Task(
                1L
                , "Nettoyez les vitres"
                , LocalDateTime.of(2021, 2, 20, 11, 30)
        );


        // When
        mAddTaskViewModel.addTask(
                Project.getProjectById(1L)
                , "Nettoyez les vitres"
                , LocalDateTime.of(2021, 2, 20, 11, 30)

        );


        //Then


        Mockito.verify(mTaskRepository, Mockito.times(1)).insert(taskToAdd);

        Mockito.verifyNoMoreInteractions(mTaskRepository);

    }

}

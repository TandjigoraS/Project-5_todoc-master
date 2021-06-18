package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.TaskViewModel;
import com.cleanup.todoc.model.TaskViewState;
import com.cleanup.todoc.pojo.Task;
import com.cleanup.todoc.repositories.TaskRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */

@RunWith(MockitoJUnitRunner.class)
public class TaskUnitTest {


    @Rule

    public final InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    final TaskRepository mTaskRepository = Mockito.mock(TaskRepository.class);

    final MutableLiveData<List<Task>> mListMutableLiveData = new MutableLiveData<>();

    private TaskViewModel mTaskViewModel;


    @Before
    public void setUp() {


        Mockito.doReturn(mListMutableLiveData).when(mTaskRepository).getListTasks();

        mTaskViewModel = new TaskViewModel(mTaskRepository);

    }


    @Test
    public void test_projects() {
        final Task task1 = new Task(1, "task 1", LocalDateTime.of(2021, 2, 20, 11, 30));
        final Task task2 = new Task(2, "task 2", LocalDateTime.of(2021, 2, 20, 12, 30));
        final Task task3 = new Task(3, "task 3", LocalDateTime.of(2021, 2, 20, 13, 30));
        final Task task4 = new Task(4, "task 4", LocalDateTime.of(2021, 2, 20, 11, 30));

        assertEquals("Projet Tartampion", task1.getProject().getName());
        assertEquals("Projet Lucidia", task2.getProject().getName());
        assertEquals("Projet Circus", task3.getProject().getName());
        assertNull(task4.getProject());
    }


    @Test
    public void test_az_comparator_by_project() throws InterruptedException {
        //Given
        final Task task1 = new Task(1L, "aaa",LocalDateTime.of(2021, 2, 20, 11, 30));
        final Task task2 = new Task(2L, "zzz",LocalDateTime.of(2021, 2, 20, 12, 30));
        final Task task3 = new Task(3L, "hhh",LocalDateTime.of(2021, 2, 20, 13, 30));

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        mListMutableLiveData.setValue(tasks);

        //When
        mTaskViewModel.sortAlphabetical();

        TaskViewState result = UnitTestUtils.getOrAwaitValue(mTaskViewModel.getTasksViewStateLiveData());

        //Then
        assertSame(result.getTaskList().get(0).getProject().getName(), task3.getProject().getName());
        assertSame(result.getTaskList().get(1).getProject().getName(), task2.getProject().getName());
        assertSame(result.getTaskList().get(2).getProject().getName(), task1.getProject().getName());

        Mockito.verify(mTaskRepository, Mockito.times(1)).getListTasks();

        Mockito.verifyNoMoreInteractions(mTaskRepository);
    }

    @Test
    public void test_za_comparator() throws InterruptedException {
        //Given
        final Task task1 = new Task(1L, "aaa", LocalDateTime.of(2021, 2, 20, 11, 30));
        final Task task2 = new Task(2L, "zzz", LocalDateTime.of(2021, 2, 20, 12, 30));
        final Task task3 = new Task(3L, "hhh", LocalDateTime.of(2021, 2, 20, 13, 30));

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        mListMutableLiveData.setValue(tasks);

        //When
        mTaskViewModel.sortAlphabeticalInverted();

        TaskViewState result = UnitTestUtils.getOrAwaitValue(mTaskViewModel.getTasksViewStateLiveData());

        //Then
        assertSame(result.getTaskList().get(0).getProject().getName(), task1.getProject().getName());
        assertSame(result.getTaskList().get(1).getProject().getName(), task2.getProject().getName());
        assertSame(result.getTaskList().get(2).getProject().getName(), task3.getProject().getName());

        Mockito.verify(mTaskRepository, Mockito.times(1)).getListTasks();

        Mockito.verifyNoMoreInteractions(mTaskRepository);
    }

    @Test
    public void test_recent_comparator() throws InterruptedException {
        //Given
        final Task task1 = new Task(1L, "aaa",LocalDateTime.of(2021, 2, 20, 11, 30));
        final Task task2 = new Task(2L, "zzz",LocalDateTime.of(2021, 2, 20, 12, 30));
        final Task task3 = new Task(3L, "hhh",LocalDateTime.of(2021, 2, 20, 13, 30));

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        mListMutableLiveData.setValue(tasks);

        //When
        mTaskViewModel.sortNewestFirst();

        TaskViewState result = UnitTestUtils.getOrAwaitValue(mTaskViewModel.getTasksViewStateLiveData());

        //Then
        assertSame(result.getTaskList().get(0).getLocalDateTime(),task3.getLocalDateTime());
        assertSame(result.getTaskList().get(1).getLocalDateTime(),task2.getLocalDateTime());
        assertSame(result.getTaskList().get(2).getLocalDateTime(),task1.getLocalDateTime());

        Mockito.verify(mTaskRepository, Mockito.times(1)).getListTasks();

        Mockito.verifyNoMoreInteractions(mTaskRepository);
    }

    @Test
    public void test_old_comparator() throws InterruptedException {
        //Given
        final Task task1 = new Task(1L, "aaa", LocalDateTime.of(2021, 2, 20, 11, 30));
        final Task task2 = new Task(2L, "zzz", LocalDateTime.of(2021, 2, 20, 12, 30));
        final Task task3 = new Task(3L, "hhh", LocalDateTime.of(2021, 2, 20, 13, 30));

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        mListMutableLiveData.setValue(tasks);

        //When
        mTaskViewModel.sortOldestFirst();

        TaskViewState result = UnitTestUtils.getOrAwaitValue(mTaskViewModel.getTasksViewStateLiveData());

        //Then
        assertSame(result.getTaskList().get(0).getLocalDateTime(), task1.getLocalDateTime());
        assertSame(result.getTaskList().get(1).getLocalDateTime(), task2.getLocalDateTime());
        assertSame(result.getTaskList().get(2).getLocalDateTime(), task3.getLocalDateTime());

        Mockito.verify(mTaskRepository, Mockito.times(1)).getListTasks();

        Mockito.verifyNoMoreInteractions(mTaskRepository);
    }

    @Test
    public void when_deleteTask() {

        //Given
        Task taskToDelete = new Task(
                1L
                , "Nettoyez les vitres"
                , LocalDateTime.of(2021, 2, 20, 11, 30)
        );


        // When
        mTaskViewModel.deleteTask(taskToDelete);


        //Then

        Mockito.verify(mTaskRepository, Mockito.times(1)).getListTasks();

        Mockito.verify(mTaskRepository, Mockito.times(1)).deleteTask(taskToDelete);

        Mockito.verifyNoMoreInteractions(mTaskRepository);

    }

}
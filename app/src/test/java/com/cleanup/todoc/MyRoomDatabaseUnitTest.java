package com.cleanup.todoc;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;


import com.cleanup.todoc.database.MyRoomDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.pojo.Project;
import com.cleanup.todoc.pojo.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class MyRoomDatabaseUnitTest {


    // FOR DATA
    private MyRoomDatabase database;
    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;
    private final Task TASK_DEMO = new Task(1L, "Lavez le sol", LocalDateTime.of(2021, 2, 20, 11, 30));

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                MyRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        mTaskDao = this.database.getTaskDao();
        mProjectDao = this.database.getProjectDao();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void addAndGetProject() throws InterruptedException {
        for (Project project :
                Project.getAllProjects()) {
            mProjectDao.addProject(project);
        }

        Project[] myListProject = LiveDataTestUtil.getValue(this.database.getProjectDao().getAllProjects());

        assertEquals(3, myListProject.length);
    }


    @Test
    public void insertAndGetListTasks() throws InterruptedException {
        for (Project project :
                Project.getAllProjects()) {
            mProjectDao.addProject(project);
        }
        List<Task> myListTasks = LiveDataTestUtil.getValue(database.getTaskDao().getMyListTasks());

        assertEquals(0, myListTasks.size());

        mTaskDao.insert(TASK_DEMO);

        List<Task> myListTasksUpdate = LiveDataTestUtil.getValue(database.getTaskDao().getMyListTasks());

        assertEquals(1, myListTasksUpdate.size());
    }

    @Test
    public void deleteAndGetListTasks() throws InterruptedException {
        for (Project project :
                Project.getAllProjects()) {
            mProjectDao.addProject(project);
        }
        mTaskDao.insert(TASK_DEMO);

        List<Task> myListTasks = LiveDataTestUtil.getValue(database.getTaskDao().getMyListTasks());

        mTaskDao.deleteTask(myListTasks.get(0));

        List<Task> myListTasksUpdate = LiveDataTestUtil.getValue(database.getTaskDao().getMyListTasks());

        assertEquals(0, myListTasksUpdate.size());
        assertFalse(myListTasksUpdate.contains(TASK_DEMO));
    }


}


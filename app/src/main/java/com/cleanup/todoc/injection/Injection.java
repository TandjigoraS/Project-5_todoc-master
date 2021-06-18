package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.database.MyRoomDatabase;

public class Injection {



        public static TaskRepository provideTaskRepository(Context context) {
            MyRoomDatabase database = MyRoomDatabase.getDatabase(context);
            return new TaskRepository(database.getTaskDao());
        }

    public static ProjectRepository provideProjectRepository(Context context) {
        MyRoomDatabase database = MyRoomDatabase.getDatabase(context);
        return new ProjectRepository(database.getProjectDao());
    }



        public static ViewModelFactory provideViewModelFactory(Context context) {
            TaskRepository dataSourceTask = provideTaskRepository(context);
            ProjectRepository dataSourceProject = provideProjectRepository(context);
            return new ViewModelFactory(dataSourceTask, dataSourceProject);
        }


}

package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.utils.converter.LocalDateTimeAttributeConverter;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.pojo.Project;
import com.cleanup.todoc.pojo.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
@TypeConverters(LocalDateTimeAttributeConverter.class)
public abstract class MyRoomDatabase extends RoomDatabase {


    public abstract TaskDao getTaskDao();
    public abstract ProjectDao getProjectDao();


    private static volatile MyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 5;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "MyDatabase.db")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }

            }
        }
        return INSTANCE;
    }


    private static final Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            ContentValues valueProject = new ContentValues();
            valueProject.put("id", 1L);
            valueProject.put("name", "Projet Tartampion");
            valueProject.put("color", 0xFFEADAD1);


            ContentValues valueProject2 = new ContentValues();
            valueProject2.put("id", 2L);
            valueProject2.put("name", "Projet Lucidia");
            valueProject2.put("color", 0xFFB4CDBA);

            ContentValues valueProject3 = new ContentValues();
            valueProject3.put("id", 3L);
            valueProject3.put("name", "Projet Circus");
            valueProject3.put("color", 0xFFA3CED2);

            db.insert("project", OnConflictStrategy.REPLACE, valueProject);
            db.insert("project", OnConflictStrategy.REPLACE, valueProject2);
            db.insert("project", OnConflictStrategy.REPLACE, valueProject3);

        }


    };


}







package com.cleanup.todoc.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.injection.Injection;
import com.cleanup.todoc.injection.ViewModelFactory;
import com.cleanup.todoc.model.TaskViewModel;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The adapter which handles the list of tasks
     */
    private TasksAdapter adapter;

    /**
     * The RecyclerView which displays the list of tasks
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;

    /**
     * The TextView displaying the empty state
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    private TaskViewModel mTaskViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        mTaskViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TaskViewModel.class);
        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);
        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TasksAdapter(mTaskViewModel);
        listTasks.setAdapter(adapter);
        mTaskViewModel.getTasksViewStateLiveData().observe(this, taskViewState -> {
            adapter.updateTasks(taskViewState.getTaskList());
            lblNoTasks.setVisibility(taskViewState.getStateTextView());
            listTasks.setVisibility(taskViewState.getStateRecyclerView());

        });


        findViewById(R.id.fab_add_task).setOnClickListener(view -> {
            MyDialogFragment myDialogFragment = new MyDialogFragment();

            myDialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.filter_alphabetical) {
            mTaskViewModel.sortAlphabetical();

        } else if (id == R.id.filter_alphabetical_inverted) {
            mTaskViewModel.sortAlphabeticalInverted();

        } else if (id == R.id.filter_oldest_first) {
            mTaskViewModel.sortOldestFirst();

        } else if (id == R.id.filter_recent_first) {
            mTaskViewModel.sortNewestFirst();
        }


        return super.onOptionsItemSelected(item);
    }


}

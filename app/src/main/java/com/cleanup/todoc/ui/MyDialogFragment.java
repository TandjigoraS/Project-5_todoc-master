package com.cleanup.todoc.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.cleanup.todoc.R;
import com.cleanup.todoc.injection.Injection;
import com.cleanup.todoc.injection.ViewModelFactory;
import com.cleanup.todoc.model.AddTaskViewModel;
import com.cleanup.todoc.pojo.Project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDialogFragment extends DialogFragment {

    private EditText dialogEditText;
    private Spinner dialogSpinner;
    @Nullable
    public AlertDialog dialog = null;
    private final List<Project> allProjects = new ArrayList<>();
    private AddTaskViewModel mAddTaskViewModel;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_task, null);
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        dialogEditText = view.findViewById(R.id.txt_task_name);
        dialogSpinner = view.findViewById(R.id.project_spinner);
        ArrayAdapter<Project> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpinner.setAdapter(adapter);
        mAddTaskViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddTaskViewModel.class);
        mAddTaskViewModel.getMyListProjects().observe(this, projects -> {
            allProjects.addAll(Arrays.asList(projects));
            adapter.notifyDataSetChanged();

        });





        alertBuilder.setView(view)
                .setTitle(getString(R.string.add_task))
                .setPositiveButton(getString(R.string.add), null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogSpinner = null;
            dialogEditText = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                String taskName = dialogEditText.getText().toString();
                Project taskProject = (Project) dialogSpinner.getSelectedItem();
                mAddTaskViewModel.addTask(taskProject, taskName, LocalDateTime.now());
                mAddTaskViewModel.getAddTaskViewStateMutableLiveData().observe(getActivity(),
                        addTaskViewState -> dialogEditText.setError(addTaskViewState.getTaskNameError()));

                mAddTaskViewModel.getTaskIsAdded().observe(getActivity(), taskIsAdded -> {
                    if (taskIsAdded) {
                        dialog.dismiss();
                    }


                });
            });
        });

        return dialog;
    }


}
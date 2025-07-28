package com.rzjaffery.personaltaskmanagerapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rzjaffery.personaltaskmanagerapp.R;
import com.rzjaffery.personaltaskmanagerapp.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class CompletedTasksActivity extends AppCompatActivity {
    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_list);

        RecyclerView recyclerView = findViewById(R.id.completed_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(this, allTasks -> {
            List<com.rzjaffery.personaltaskmanagerapp.model.Task> completedTasks = new ArrayList<>();
            for (com.rzjaffery.personaltaskmanagerapp.model.Task task : allTasks) {
                if (task.isCompleted()) {
                    completedTasks.add(task);
                }
            }
            adapter.setTasks(completedTasks);
        });
    }
}

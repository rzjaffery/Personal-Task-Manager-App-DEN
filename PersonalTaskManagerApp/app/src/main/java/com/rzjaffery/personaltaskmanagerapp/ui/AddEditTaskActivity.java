// AddEditTaskActivity.java
package com.rzjaffery.personaltaskmanagerapp.ui;

import static com.rzjaffery.personaltaskmanagerapp.R.array.priorities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rzjaffery.personaltaskmanagerapp.R;
import com.rzjaffery.personaltaskmanagerapp.model.Task;
import com.rzjaffery.personaltaskmanagerapp.viewmodel.TaskViewModel;

import java.util.Date;
import java.util.Locale;


public class AddEditTaskActivity extends AppCompatActivity {

    private EditText editTitle, editDescription, editDate;
    private Spinner spinnerPriority;
    private CheckBox checkboxCompleted;
    private TaskViewModel taskViewModel;
    private int taskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTitle = findViewById(R.id.edit_title);
        editDescription = findViewById(R.id.edit_description);
        editDate = findViewById(R.id.edit_date);
        spinnerPriority = findViewById(R.id.spinner_priority);
        checkboxCompleted = findViewById(R.id.checkbox_completed);
        Button saveButton = findViewById(R.id.button_save);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                priorities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        if (getIntent().hasExtra("task_id")) {
            setTitle("Edit Task");
            taskId = getIntent().getIntExtra("task_id", -1);
            editTitle.setText(getIntent().getStringExtra("title"));
            editDescription.setText(getIntent().getStringExtra("description"));
            editDate.setText(getIntent().getStringExtra("date"));
            spinnerPriority.setSelection(adapter.getPosition(getIntent().getStringExtra("priority")));
            checkboxCompleted.setChecked(getIntent().getBooleanExtra("isCompleted", false));
        } else {
            setTitle("Add Task");
        }

        saveButton.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String description = editDescription.getText().toString();
            String dateString = editDate.getText().toString();
            String priority = spinnerPriority.getSelectedItem().toString();
            boolean isCompleted = checkboxCompleted.isChecked();

            if (title.isEmpty() || dateString.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Date date;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString);
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid date format. Use yyyy-MM-dd.", Toast.LENGTH_SHORT).show();
                return;
            }


            Task task = new Task(title, description, date, priority, isCompleted);

            if (taskId != -1) {
                task.setId(taskId);
                taskViewModel.update(task);
            } else {
                taskViewModel.insert(task);
            }

            finish();
        });

    }
}
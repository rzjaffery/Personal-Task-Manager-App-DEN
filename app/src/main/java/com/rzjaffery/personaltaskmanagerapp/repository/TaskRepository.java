// repository/TaskRepository.java
package com.rzjaffery.personaltaskmanagerapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.rzjaffery.personaltaskmanagerapp.db.TaskDao;
import com.rzjaffery.personaltaskmanagerapp.db.TaskDatabase;
import com.rzjaffery.personaltaskmanagerapp.model.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public TaskRepository(Application application) {
        TaskDatabase db = TaskDatabase.getInstance(application);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public void insert(Task task) { executor.execute(() -> taskDao.insert(task)); }
    public void update(Task task) { executor.execute(() -> taskDao.update(task)); }
    public void delete(Task task) { executor.execute(() -> taskDao.delete(task)); }
    public LiveData<List<Task>> getAllTasks() { return allTasks; }
}

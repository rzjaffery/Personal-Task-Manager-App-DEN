// TaskAdapter.java
package com.rzjaffery.personaltaskmanagerapp.ui;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.rzjaffery.personaltaskmanagerapp.R;
import com.rzjaffery.personaltaskmanagerapp.model.Task;
import com.rzjaffery.personaltaskmanagerapp.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<Task> tasks = new ArrayList<>();
    private OnItemClickListener listener;
    private TaskViewModel taskViewModel;

    public TaskAdapter(TaskViewModel taskViewModel) {
        this.taskViewModel = taskViewModel;
    }

    public TaskAdapter() {

    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_task, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task currentTask = tasks.get(position);
        holder.textViewTitle.setText(currentTask.getTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(currentTask.getDate());
        holder.textViewDate.setText(formattedDate);

        holder.textViewPriority.setText(currentTask.getPriority());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTaskAt(int position) {
        return tasks.get(position);
    }

    public ItemTouchHelper.SimpleCallback getSwipeCallback() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Task task = getTaskAt(position);

                if (direction == ItemTouchHelper.RIGHT) {
                    task.setCompleted(true);
                    taskViewModel.update(task);
                    tasks.remove(position);
                    notifyItemRemoved(position);

                    Toast.makeText(taskViewModel.getApplication().getApplicationContext(),
                            "Task Marked as Complete",
                            Toast.LENGTH_SHORT).show();


                } else if (direction == ItemTouchHelper.LEFT) {
                    taskViewModel.delete(task);
                    Toast.makeText(viewHolder.itemView.getContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dx,
                                    float dy, int actionState, boolean isCurrentlyActive) {
                Paint paint = new Paint();
                View view = viewHolder.itemView;

                if (dx > 0) { // Right swipe
                    paint.setColor(Color.parseColor("#4CAF50")); // Green
                    c.drawRect(view.getLeft(), view.getTop(), view.getLeft() + dx, view.getBottom(), paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(48);
                    c.drawText("Done", view.getLeft() + 50, view.getTop() + 100, paint);
                } else if (dx < 0) { // Left swipe
                    paint.setColor(Color.parseColor("#F44336")); // Red
                    c.drawRect(view.getRight() + dx, view.getTop(), view.getRight(), view.getBottom(), paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(48);
                    float textWidth = paint.measureText("Delete");
                    c.drawText("Delete", view.getRight() - textWidth - 40, view.getTop() + 100, paint);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
            }
        };
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDate;
        private final TextView textViewPriority;

        public TaskHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_title);
            textViewDate = itemView.findViewById(R.id.text_date);
            textViewPriority = itemView.findViewById(R.id.text_priority);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(tasks.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

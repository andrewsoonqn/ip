package Tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks = new ArrayList<>();

    public Task addTask(Task task) {
        tasks.add(task);
        return task;
    }

    @Override
    public String toString() {
        StringBuilder taskListBuilder = new StringBuilder();

        for (Task task : tasks) {
            taskListBuilder.append(TaskString.withIndex(task, tasks.indexOf(task) + 1));

            if (tasks.size() == 1) {
                break;
            }

            taskListBuilder.append("\n");
        }
        return taskListBuilder.toString();
    }

    public Task getTask(int idx) {
        return tasks.get(idx - 1);
    }

    public Task markTask(int which) {
        Task task = getTask(which);
        task.mark();
        return task;
    }

    public Task unmarkTask(int which) {
        Task task = getTask(which);
        task.unmark();
        return task;
    }

    public int getSize() {
        return tasks.size();
    }

    public Task removeTask(int which) {
        return tasks.remove(which - 1);
    }
}

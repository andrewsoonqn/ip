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
            taskListBuilder.append(TaskString.withId(task));

            if (tasks.size() == 1) {
                break;
            }

            taskListBuilder.append("\n");
        }
        return taskListBuilder.toString();
    }

    public Task getTask(int id) {
        return tasks.get(id - 1);
    }

    public void markTask(int id) {
        getTask(id).mark();
    }

    public void unmarkTask(int id) {
        getTask(id).unmark();
    }

    public int getSize() {
        return tasks.size();
    }
}

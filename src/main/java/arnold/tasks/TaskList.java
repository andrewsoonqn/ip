package arnold.tasks;

import arnold.datapersistence.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList {
    private final List<Task> tasks = new ArrayList<>();
    private final Storage storage;

    private TaskList(Storage storage) {
        this.storage = storage;
    }

    public static TaskList create(Storage storage) {
        TaskList taskList = new TaskList(storage);
        storage.load(taskList);
        return taskList;
    }

    public Task addTask(Task task) {
        tasks.add(task);
        storage.save(this);
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
        storage.save(this);
        return task;
    }

    public Task unmarkTask(int which) {
        Task task = getTask(which);
        task.unmark();
        storage.save(this);
        return task;
    }

    public int getSize() {
        return tasks.size();
    }

    public Task removeTask(int which) {
        Task removedTask = tasks.remove(which - 1);
        storage.save(this);
        return removedTask;
    }

    public String getTasksAsCommands() {
        return tasks.stream()
                .map(Task::asCommand).collect(Collectors.joining("\n"));
    }

}

package arnold.tasks;

import arnold.datapersistence.Storage;
import arnold.chatbotexceptions.ChatbotArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private final List<Task> tasks = new ArrayList<>();
    private final Storage storage;

    private TaskList(Storage storage) {
        this.storage = storage;
    }

    /**
     * Creates a TaskList from storage.
     *
     * @param storage The storage to load tasks from.
     * @return A TaskList populated with tasks from storage.
     */
    public static TaskList create(Storage storage) {
        TaskList taskList = new TaskList(storage);
        storage.load(taskList);
        return taskList;
    }

    /**
     * Adds a task to the list and saves the changes.
     *
     * @param task The task to be added.
     * @return The added task.
     */
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

    /**
     * Retrieves a task from the list by its index.
     *
     * @param idx The 1-based index of the task.
     * @return The task at the specified index.
     * @throws ChatbotArgumentException If the index is out of bounds.
     */
    public Task getTask(int idx) {
        if (idx < 1 || idx > tasks.size()) {
            throw new ChatbotArgumentException("Task index out of bounds.");
        }
        return tasks.get(idx - 1);
    }

    /**
     * Marks a task as done and saves the changes.
     *
     * @param which The 1-based index of the task to mark.
     * @return The marked task.
     */
    public Task markTask(int which) {
        Task task = getTask(which);
        task.mark();
        storage.save(this);
        return task;
    }

    /**
     * Unmarks a task as done and saves the changes.
     *
     * @param which The 1-based index of the task to unmark.
     * @return The unmarked task.
     */
    public Task unmarkTask(int which) {
        Task task = getTask(which);
        task.unmark();
        storage.save(this);
        return task;
    }

    /**
     * Returns the size of the task list.
     *
     * @return The number of tasks in the list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Removes a task from the list and saves the changes.
     *
     * @param which The 1-based index of the task to remove.
     * @return The removed task.
     */
    public Task removeTask(int which) {
        Task removedTask = tasks.remove(which - 1);
        storage.save(this);
        return removedTask;
    }

    /**
     * Returns all tasks formatted as commands.
     *
     * @return A string containing all tasks as commands.
     */
    public String getTasksAsCommands() {
        return tasks.stream()
                .map(Task::asCommand).collect(Collectors.joining("\n"));
    }

}

package arnold.tasks.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.datapersistence.Storage;
import arnold.tasks.Task;
import arnold.utils.ListSearcher;

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
        return TaskString.listWithIndex(tasks);
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
     * @throws ChatbotArgumentException If the index is out of bounds.
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
     * @throws ChatbotArgumentException If the index is out of bounds.
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
     * @throws ChatbotArgumentException If the index is out of bounds.
     */
    public Task removeTask(int which) {
        try {
            Task removedTask = tasks.remove(which - 1);
            storage.save(this);
            return removedTask;
        } catch (IndexOutOfBoundsException e) {
            throw new ChatbotArgumentException("Task index out of bounds.");
        }
    }

    /**
     * Retrieves an unmodifiable view of the task list.
     *
     * @return An unmodifiable list of tasks.
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Loads the given list of tasks into the task list, replacing any existing tasks.
     *
     * @param loadedTasks The list of tasks to load. Must not be null.
     */
    public void loadTasks(List<Task> loadedTasks) {
        tasks.clear();
        tasks.addAll(loadedTasks);
    }

    /**
     * Finds and retrieves a list of tasks that satisfy the given predicate condition.
     *
     * @param predicate The condition to apply to each task. Must not be null.
     * @return A list of tasks that match the predicate condition. Returns an empty list if no tasks
     *     satisfy the predicate or if the task list is empty.
     * @throws NullPointerException If the predicate is null.
     */
    public List<Task> findTasks(Predicate<Task> predicate) {
        ListSearcher<Task> searcher = new ListSearcher<>();
        return searcher.findItems(tasks, predicate);
    }
}

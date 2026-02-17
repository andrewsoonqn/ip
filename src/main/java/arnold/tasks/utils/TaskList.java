package arnold.tasks.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.datapersistence.Storage;
import arnold.inputhandling.Messages;
import arnold.tasks.Task;

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
     * Creates a TaskList from storage. If the data file is corrupted or unreadable,
     * the task list will be empty and any registered {@link arnold.datapersistence.StorageEventListener}
     * will be notified.
     *
     * @param storage The storage to load tasks from.
     * @return A TaskList populated with tasks from storage, or empty if loading failed.
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
        int initialSize = tasks.size();
        tasks.add(task);
        assert tasks.contains(task) : "Task should be in list after adding";
        assert tasks.size() == initialSize + 1 : "Task list size should increase by 1 after adding";
        storage.save(this);
        return task;
    }

    @Override
    public String toString() {
        return TaskString.listWithIndex(tasks);
    }

    /**
     * Validates that the given 1-based index is within valid bounds.
     *
     * @param idx The 1-based index to validate.
     * @throws ChatbotArgumentException If the index is out of bounds.
     */
    private void validateIndex(int idx) {
        if (idx < 1 || idx > tasks.size()) {
            throw new ChatbotArgumentException(Messages.taskIndexOutOfBounds(tasks.size()));
        }
    }

    /**
     * Retrieves a task from the list by its index.
     *
     * @param idx The 1-based index of the task.
     * @return The task at the specified index.
     * @throws ChatbotArgumentException If the index is out of bounds.
     */
    public Task getTask(int idx) {
        validateIndex(idx);
        return tasks.get(idx - 1);
    }

    /**
     * Marks a task as done and saves the changes.
     *
     * @param idx The 1-based index of the task to mark.
     * @return The marked task.
     * @throws ChatbotArgumentException If the index is out of bounds.
     */
    public Task markTask(int idx) {
        Task task = getTask(idx);
        task.mark();
        assert task.isDone() : "Task should be marked as done after marking";
        storage.save(this);
        return task;
    }

    /**
     * Unmarks a task as done and saves the changes.
     *
     * @param idx The 1-based index of the task to unmark.
     * @return The unmarked task.
     * @throws ChatbotArgumentException If the index is out of bounds.
     */
    public Task unmarkTask(int idx) {
        Task task = getTask(idx);
        task.unmark();
        assert !task.isDone() : "Task should not be done after unmarking";
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
     * @param idx The 1-based index of the task to remove.
     * @return The removed task.
     * @throws ChatbotArgumentException If the index is out of bounds.
     */
    public Task removeTask(int idx) {
        validateIndex(idx);
        int previousSize = tasks.size();
        Task removedTask = tasks.remove(idx - 1);
        assert tasks.size() == previousSize - 1 : "Task list size should decrease by one after removal";
        storage.save(this);
        return removedTask;
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
        return tasks.stream().filter(predicate).toList();
    }
}

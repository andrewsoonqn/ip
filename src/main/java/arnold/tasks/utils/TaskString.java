package arnold.tasks.utils;

import java.util.List;

import arnold.tasks.Task;

/**
 * Utility class for formatting tasks as strings.
 */
public class TaskString {
    /**
     * Returns the task formatted with its index.
     *
     * @param task The task to format.
     * @param index The index of the task.
     * @return The formatted string.
     */
    public static String withIndex(Task task, int index) {
        return String.format("%d.%s", index, task);
    }

    /**
     * Returns the task formatted without its index.
     *
     * @param task The task to format.
     * @return The formatted string.
     */
    public static String withoutIndex(Task task) {
        return String.format("  %s", task);
    }

    /**
     * Returns a string representing the list of tasks with indices.
     *
     * @param tasks The list of tasks.
     * @return The formatted string.
     */
    public static String listWithIndex(List<Task> tasks) {
        StringBuilder taskListBuilder = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            taskListBuilder.append(TaskString.withIndex(task, i + 1));

            if (i < tasks.size() - 1) {
                taskListBuilder.append("\n");
            }
        }
        return taskListBuilder.toString();
    }
}


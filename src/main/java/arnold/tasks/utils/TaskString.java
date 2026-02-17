package arnold.tasks.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import arnold.tasks.Task;

/**
 * Utility class for formatting tasks as strings.
 */
public class TaskString {
    private static final String INDENT = "  ";

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
        return INDENT + task;
    }

    /**
     * Returns a string representing the list of tasks with indices.
     *
     * @param tasks The list of tasks.
     * @return The formatted string.
     */
    public static String listWithIndex(List<Task> tasks) {
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> withIndex(tasks.get(i), i + 1))
                .collect(Collectors.joining("\n"));
    }
}


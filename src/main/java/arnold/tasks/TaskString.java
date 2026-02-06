package arnold.tasks;

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
}
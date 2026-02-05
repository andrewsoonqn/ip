package tasks;

public class TaskString {
    public static String withIndex(Task task, int index) {
        return String.format("%d.%s", index, task);
    }

    public static String withoutIndex(Task task) {
        return String.format("  %s", task);
    }
}
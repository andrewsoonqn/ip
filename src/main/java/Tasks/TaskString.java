package Tasks;

public class TaskString {
    public static String withId(Task task) {
        return String.format("%d. %s", task.getId(), task);
    }

    public static String withoutId(Task task) {
        return String.format("   %s", task);
    }
}
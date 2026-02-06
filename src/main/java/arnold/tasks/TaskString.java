package arnold.tasks;

import java.util.List;
public class TaskString {
    public static String withIndex(Task task, int index) {
        return String.format("%d.%s", index, task);
    }

    public static String withoutIndex(Task task) {
        return String.format("  %s", task);
    }

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
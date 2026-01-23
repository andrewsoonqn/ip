import java.util.ArrayList;
import java.util.List;

public class TaskList {
    // This TaskList implements a Singleton pattern
    private static final TaskList instance = new TaskList();
    private final List<String> tasks = new ArrayList<>();

    private TaskList() {}

    public static TaskList getInstance() {
        return instance;
    }

    public String addTask(String task) {
        tasks.add(task);
        return "added: " + task;
    }

    public String listTasks() {
        StringBuilder taskListBuilder = new StringBuilder();

        int counter = 1;
        for (String task : tasks) {
            // Format as "{counter}. {task}"
            // e.g. "1. my task"
            taskListBuilder.append(String.format("%d. %s", counter, task));

            if (tasks.size() == 1) {
                break;
            }
            taskListBuilder.append("\n");
            counter++;
        }
        return taskListBuilder.toString();
    }
}

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
        for (String task : tasks) {
            if (tasks.size() == 1) {
                taskListBuilder.append(task);
                break;
            }
            taskListBuilder.append(task).append("\n");
        }
        return taskListBuilder.toString();
    }
}

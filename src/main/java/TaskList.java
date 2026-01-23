import java.util.ArrayList;
import java.util.List;

public class TaskList {
    // This TaskList implements a Singleton pattern
    private static final TaskList instance = new TaskList();
    private final List<Task> tasks = new ArrayList<>();

    private TaskList() {}

    public static TaskList getInstance() {
        return instance;
    }

    public String addTask(Task task) {
        tasks.add(task);
        return "added: " + task.getDescription();
    }

    public String listTasks() {
        StringBuilder taskListBuilder = new StringBuilder();

        for (Task task : tasks) {
            taskListBuilder.append(task);

            if (tasks.size() == 1) {
                break;
            }

            taskListBuilder.append("\n");
        }
        return taskListBuilder.toString();
    }
}

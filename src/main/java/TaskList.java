import java.util.ArrayList;
import java.util.List;

public class TaskList {
    // This TaskList implements a Singleton pattern
    private static TaskList instance = new TaskList();
    private List<String> tasks = new ArrayList<>();

    private TaskList() {}

    public static TaskList getInstance() {
        return instance;
    }

    public String addTask(String task) {
        tasks.add(task);
        return "added: " + task;
    }


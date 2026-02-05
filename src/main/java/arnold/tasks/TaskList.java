package arnold.tasks;

import arnold.datapersistence.DataLoader;
import arnold.datapersistence.DataPaths;
import arnold.datapersistence.DataSaver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList {
    private final List<Task> tasks = new ArrayList<>();

    private TaskList() {
    }

    public static TaskList create() {
        TaskList taskList = new TaskList();
        DataLoader.loadData(DataPaths.TASKS_FILE_PATH, taskList);
        return taskList;
    }

    public Task addTask(Task task) {
        tasks.add(task);
        DataSaver.saveData(DataPaths.TASKS_FILE_PATH, this);
        return task;
    }

    @Override
    public String toString() {
        StringBuilder taskListBuilder = new StringBuilder();

        for (Task task : tasks) {
            taskListBuilder.append(TaskString.withIndex(task, tasks.indexOf(task) + 1));

            if (tasks.size() == 1) {
                break;
            }

            taskListBuilder.append("\n");
        }
        return taskListBuilder.toString();
    }

    public Task getTask(int idx) {
        return tasks.get(idx - 1);
    }

    public Task markTask(int which) {
        Task task = getTask(which);
        task.mark();
        DataSaver.saveData(DataPaths.TASKS_FILE_PATH, this);
        return task;
    }

    public Task unmarkTask(int which) {
        Task task = getTask(which);
        task.unmark();
        DataSaver.saveData(DataPaths.TASKS_FILE_PATH, this);
        return task;
    }

    public int getSize() {
        return tasks.size();
    }

    public Task removeTask(int which) {
        return tasks.remove(which - 1);
    }

    public String getTasksAsCommands() {
        return tasks.stream()
                .map(Task::asCommand).collect(Collectors.joining("\n"));
    }

}

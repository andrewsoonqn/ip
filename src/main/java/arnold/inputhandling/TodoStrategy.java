package arnold.inputhandling;

import arnold.tasks.Task;
import arnold.tasks.Todo;

public class TodoStrategy extends AddTaskStrategy {
    @Override
    protected Task getTask(String input) {
        return new Todo(input);
    }

    @Override
    public String getExampleUsage() {
        return "todo read book";
    }
}
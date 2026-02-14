package arnold.inputhandling;

import arnold.tasks.Task;
import arnold.tasks.Todo;

/**
 * Strategy for adding a todo task.
 */
public class TodoStrategy extends AddTaskStrategy {
    /**
     * Extracts a todo task from the user input.
     *
     * @param input The user input containing the task description.
     * @return The created todo task.
     */
    @Override
    protected Task getTask(String input) {
        return new Todo(input);
    }

    @Override
    public String getExampleUsage() {
        return "todo read book";
    }
}

package arnold.inputhandling.strategies.taskcrudstrategies.create;

import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Task;
import arnold.tasks.Todo;

/**
 * Strategy for adding a todo task.
 */
public class TodoStrategy extends CreateTaskStrategy {
    /**
     * Extracts a todo task from the parsed command.
     *
     * @param command The parsed command containing the task description.
     * @return The created todo task.
     */
    @Override
    protected Task getTask(ParsedCommand command) {
        return new Todo(command.getDescription());
    }

    @Override
    public String getExampleUsage() {
        return "todo read book";
    }
}

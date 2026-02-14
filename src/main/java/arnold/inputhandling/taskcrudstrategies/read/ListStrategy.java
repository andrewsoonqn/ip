package arnold.inputhandling.taskcrudstrategies.read;

import arnold.inputhandling.InputHandlingStrategy;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the list command.
 */
public class ListStrategy implements InputHandlingStrategy {
    /**
     * Handles the list command by displaying all tasks.
     *
     * @param command The parsed command (not used).
     * @param taskList The task list to display.
     * @return The formatted list of all tasks.
     */
    @Override
    public String handleInput(ParsedCommand command, TaskList taskList) {
        return "Here are the tasks in your list:\n" + taskList;
    }
}

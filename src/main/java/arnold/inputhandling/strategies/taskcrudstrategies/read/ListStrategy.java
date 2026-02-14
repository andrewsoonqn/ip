package arnold.inputhandling.strategies.taskcrudstrategies.read;

import arnold.inputhandling.CommandResult;
import arnold.inputhandling.strategies.InputHandlingStrategy;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.utils.TaskList;

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
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        return CommandResult.success("Here are the tasks in your list:\n" + taskList);
    }
}

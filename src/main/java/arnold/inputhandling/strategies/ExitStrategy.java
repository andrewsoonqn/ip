package arnold.inputhandling.strategies;

import arnold.inputhandling.CommandResult;
import arnold.inputhandling.Messages;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.utils.TaskList;

/**
 * Strategy for handling the exit command.
 */
public class ExitStrategy implements InputHandlingStrategy {
    /**
     * Handles the exit command by returning an exit result.
     *
     * @param command The parsed command (not used).
     * @param taskList The task list (not used).
     * @return The goodbye message with exit signal.
     */
    @Override
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        return CommandResult.exit(Messages.bye());
    }

    @Override
    public String getDescription() {
        return "Exit the application";
    }

    @Override
    public String getExampleUsage() {
        return "bye";
    }
}

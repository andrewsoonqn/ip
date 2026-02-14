package arnold.inputhandling.strategies;

import arnold.events.EventBus;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.Messages;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.utils.TaskList;

/**
 * Strategy for handling the exit command.
 */
public class ExitStrategy implements InputHandlingStrategy {
    /**
     * Handles the exit command by publishing a shutdown event.
     *
     * @param command The parsed command (not used).
     * @param taskList The task list (not used).
     * @return The goodbye message.
     */
    @Override
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        EventBus.getInstance().publishShutdown();
        return CommandResult.exit(Messages.bye());
    }
}

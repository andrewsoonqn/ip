package arnold.inputhandling.strategies;

import arnold.events.EventBus;
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
    public String handleInput(ParsedCommand command, TaskList taskList) {
        EventBus.getInstance().publishShutdown();
        return "Bye. Hope to see you again soon!";
    }
}

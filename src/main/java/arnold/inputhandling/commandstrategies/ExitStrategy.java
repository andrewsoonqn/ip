package arnold.inputhandling.commandstrategies;

import arnold.events.EventBus;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the exit command.
 */
public class ExitStrategy implements InputHandlingStrategy {
    /**
     * Handles the exit command by publishing a shutdown event.
     *
     * @param input The user input (not used).
     * @param taskList The task list (not used).
     * @return The goodbye message.
     */
    @Override
    public String handleInput(String input, TaskList taskList) {
        EventBus.getInstance().publishShutdown();
        return "Bye. Hope to see you again soon!";
    }
}

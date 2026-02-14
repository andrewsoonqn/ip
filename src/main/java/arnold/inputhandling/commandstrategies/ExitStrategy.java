package arnold.inputhandling.commandstrategies;

import arnold.events.EventBus;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the exit command.
 */
public class ExitStrategy implements InputHandlingStrategy {
    /**
     * Handles the exit command.
     *
     * @param input The user input (not used).
     * @param msg The messenger to use for communication.
     * @param taskList The task list (not used).
     */
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.printMessage("Bye. Hope to see you again soon!");

        // Bot will receive and shut down
        EventBus.getInstance().publishShutdown();
    }
}

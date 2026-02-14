package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the list command.
 */
public class ListStrategy implements InputHandlingStrategy {
    /**
     * Handles the list command.
     *
     * @param input The user input (not used).
     * @param msg The messenger to use for communication.
     * @param taskList The task list to display.
     */
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.printMessage(
            "Here are the tasks in your list:\n"
                + taskList);
    }
}

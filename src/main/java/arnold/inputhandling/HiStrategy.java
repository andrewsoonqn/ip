package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the greeting command.
 */
public class HiStrategy implements InputHandlingStrategy {
    /**
     * Handles the greeting command.
     *
     * @param input The user input (not used).
     * @param msg The messenger to use for communication.
     * @param taskList The task list (not used).
     */
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.hi();
    }
}

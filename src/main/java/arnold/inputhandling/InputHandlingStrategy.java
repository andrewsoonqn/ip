package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Interface for input handling strategies.
 */
public interface InputHandlingStrategy {
    /**
     * Handles the user input.
     *
     * @param input The user input to handle.
     * @param msg The messenger to use for communication.
     * @param taskList The task list to manage.
     */
    void handleInput(String input, Messenger msg, TaskList taskList);
}

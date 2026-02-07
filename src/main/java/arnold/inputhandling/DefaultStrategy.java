package arnold.inputhandling;

import arnold.chatbotexceptions.NoSuchCommandException;
import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Default strategy for handling unknown commands.
 */
public class DefaultStrategy implements InputHandlingStrategy {
    /**
     * Handles the user input.
     *
     * @param input The user input to handle.
     * @param msg The messenger to use for communication.
     * @param taskList The task list to manage.
     */
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        throw new NoSuchCommandException("Sorry, I don't recognise that command!");
    }
}

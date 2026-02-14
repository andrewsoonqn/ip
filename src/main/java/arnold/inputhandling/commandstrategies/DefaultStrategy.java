package arnold.inputhandling.commandstrategies;

import arnold.chatbotexceptions.NoSuchCommandException;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.tasks.TaskList;

/**
 * Default strategy for handling unknown commands.
 */
public class DefaultStrategy implements InputHandlingStrategy {
    /**
     * Handles the user input by throwing an exception for unrecognised commands.
     *
     * @param input The user input to handle.
     * @param taskList The task list to manage.
     * @return Never returns normally.
     * @throws NoSuchCommandException Always thrown.
     */
    @Override
    public String handleInput(String input, TaskList taskList) {
        throw new NoSuchCommandException("Sorry, I don't recognise that command!");
    }
}

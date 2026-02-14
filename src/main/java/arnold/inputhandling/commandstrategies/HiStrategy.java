package arnold.inputhandling.commandstrategies;

import arnold.inputhandling.InputHandlingStrategy;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the greeting command.
 */
public class HiStrategy implements InputHandlingStrategy {
    /**
     * Handles the greeting command.
     *
     * @param input The user input (not used).
     * @param taskList The task list (not used).
     * @return The greeting message.
     */
    @Override
    public String handleInput(String input, TaskList taskList) {
        return "Hello! I'm Arnold\nWhat can I do for you?";
    }
}

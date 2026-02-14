package arnold.inputhandling.viewtaskstrategies;

import arnold.inputhandling.InputHandlingStrategy;
import arnold.tasks.TaskList;

/**
 * Strategy for handling the list command.
 */
public class ListStrategy implements InputHandlingStrategy {
    /**
     * Handles the list command by displaying all tasks.
     *
     * @param input The user input (not used).
     * @param taskList The task list to display.
     * @return The formatted list of all tasks.
     */
    @Override
    public String handleInput(String input, TaskList taskList) {
        return "Here are the tasks in your list:\n" + taskList;
    }
}

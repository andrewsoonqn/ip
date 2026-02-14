package arnold.inputhandling.viewtaskstrategies;

import java.util.function.Predicate;

import arnold.inputhandling.InputHandlingStrategy;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Strategy for handling the find command.
 */
public class FindStrategy implements InputHandlingStrategy {
    /**
     * Handles the find command by searching for tasks that contain the input string.
     *
     * @param input The keyword to search for.
     * @param taskList The task list to search within.
     * @return The formatted list of matching tasks.
     */
    @Override
    public String handleInput(String input, TaskList taskList) {
        Predicate<Task> predicate = task -> task
            .getDescription()
            .toLowerCase()
            .contains(input.toLowerCase());

        return "Here are the matching tasks in your list:\n"
            + TaskString.listWithIndex(taskList.findTasks(predicate));
    }
}

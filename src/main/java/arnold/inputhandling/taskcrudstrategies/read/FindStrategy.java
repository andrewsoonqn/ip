package arnold.inputhandling.taskcrudstrategies.read;

import java.util.function.Predicate;

import arnold.inputhandling.InputHandlingStrategy;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Strategy for handling the find command.
 */
public class FindStrategy implements InputHandlingStrategy {
    /**
     * Handles the find command by searching for tasks that contain the keyword.
     *
     * @param command The parsed command containing the search keyword.
     * @param taskList The task list to search within.
     * @return The formatted list of matching tasks.
     */
    @Override
    public String handleInput(ParsedCommand command, TaskList taskList) {
        String keyword = command.getDescription();
        Predicate<Task> predicate = task -> task
            .getDescription()
            .toLowerCase()
            .contains(keyword.toLowerCase());

        return "Here are the matching tasks in your list:\n"
            + TaskString.listWithIndex(taskList.findTasks(predicate));
    }
}

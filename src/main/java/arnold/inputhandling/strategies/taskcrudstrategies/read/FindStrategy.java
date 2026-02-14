package arnold.inputhandling.strategies.taskcrudstrategies.read;

import java.util.function.Predicate;

import arnold.inputhandling.CommandResult;
import arnold.inputhandling.Messages;
import arnold.inputhandling.strategies.InputHandlingStrategy;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;
import arnold.tasks.utils.TaskString;

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
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        String keyword = command.getDescription();
        Predicate<Task> predicate = task -> task
            .getDescription()
            .toLowerCase()
            .contains(keyword.toLowerCase());

        return CommandResult.success(
            Messages.taskFind(TaskString.listWithIndex(taskList.findTasks(predicate))));
    }

    @Override
    public String getDescription() {
        return "Find tasks by keyword";
    }

    @Override
    public String getExampleUsage() {
        return "find book";
    }
}

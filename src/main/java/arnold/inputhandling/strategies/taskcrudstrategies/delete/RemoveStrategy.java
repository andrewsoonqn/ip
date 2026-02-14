package arnold.inputhandling.strategies.taskcrudstrategies.delete;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.Messages;
import arnold.inputhandling.strategies.InputHandlingStrategy;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;
import arnold.tasks.utils.TaskString;

/**
 * Strategy for handling the remove command.
 */
public class RemoveStrategy implements InputHandlingStrategy {
    /**
     * Handles the remove command by deleting the specified task.
     *
     * @param command The parsed command containing the task ID.
     * @param taskList The task list to remove the task from.
     * @return The response message confirming the task was removed.
     * @throws ChatbotArgumentException If the description is not a valid task ID.
     */
    @Override
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        int taskId;
        try {
            taskId = Integer.parseInt(command.getDescription());
        } catch (NumberFormatException e) {
            throw new ChatbotArgumentException(Messages.invalidTaskId());
        }

        Task task = taskList.removeTask(taskId);
        return CommandResult.success(
            Messages.taskRemoved(TaskString.withoutIndex(task), taskList.getSize()));
    }
}

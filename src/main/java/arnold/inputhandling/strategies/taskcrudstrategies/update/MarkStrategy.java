package arnold.inputhandling.strategies.taskcrudstrategies.update;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.strategies.InputHandlingStrategy;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;
import arnold.tasks.utils.TaskString;

/**
 * Strategy for handling the mark command.
 */
public class MarkStrategy implements InputHandlingStrategy {
    /**
     * Handles the mark command by marking the specified task as done.
     *
     * @param command The parsed command containing the task ID.
     * @param taskList The task list containing the task.
     * @return The response message confirming the task was marked.
     * @throws ChatbotArgumentException If the description is not a valid task ID.
     */
    @Override
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        int taskId;
        try {
            taskId = Integer.parseInt(command.getDescription());
        } catch (NumberFormatException e) {
            throw new ChatbotArgumentException("Please enter a valid task ID.");
        }
        Task task = taskList.markTask(taskId);
        return CommandResult.success("Nice! I've marked this task as done:\n"
            + TaskString.withoutIndex(task));
    }
}

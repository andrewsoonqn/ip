package arnold.inputhandling.strategies.taskcrudstrategies;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.ExampleUsage;
import arnold.inputhandling.Messages;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.inputhandling.strategies.InputHandlingStrategy;
import arnold.tasks.utils.TaskList;

/**
 * Abstract strategy for commands that operate on a single task identified by its ID.
 * Handles the shared logic of parsing the task ID from the command description.
 */
public abstract class TaskIdStrategy implements InputHandlingStrategy {
    @Override
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        int taskId;
        try {
            taskId = Integer.parseInt(command.getDescription());
        } catch (NumberFormatException e) {
            throw new ChatbotArgumentException(
                ExampleUsage.attach(Messages.invalidTaskId(taskList.getSize()), getExampleUsage()));
        }
        return execute(taskId, taskList);
    }

    /**
     * Executes the command on the task identified by the given ID.
     *
     * @param taskId The parsed task ID.
     * @param taskList The task list to operate on.
     * @return The command result.
     */
    protected abstract CommandResult execute(int taskId, TaskList taskList);
}

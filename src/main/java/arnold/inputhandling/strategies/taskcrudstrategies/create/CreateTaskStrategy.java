package arnold.inputhandling.strategies.taskcrudstrategies.create;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.ExampleUsage;
import arnold.inputhandling.Messages;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.inputhandling.strategies.InputHandlingStrategy;
import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;
import arnold.tasks.utils.TaskString;

/**
 * Abstract strategy for adding a task to the task list.
 */
public abstract class CreateTaskStrategy implements InputHandlingStrategy {
    /**
     * Returns a task object from the parsed command.
     *
     * @param command The parsed command containing description and flags.
     * @return The task object created from the command.
     */
    protected abstract Task getTask(ParsedCommand command);

    @Override
    public CommandResult handleInput(ParsedCommand command, TaskList taskList) {
        if (command.getDescription().isBlank()) {
            throw new ChatbotArgumentException(
                ExampleUsage.attach(Messages.blankDescription(), getExampleUsage()));
        }

        Task addedTask = taskList.addTask(getTask(command));
        return CommandResult.success(
            Messages.taskAdded(addedTask.getTaskType().toString(),
                TaskString.withoutIndex(addedTask), taskList.getSize()));
    }

}

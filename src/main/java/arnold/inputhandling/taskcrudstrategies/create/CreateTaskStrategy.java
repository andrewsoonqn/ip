package arnold.inputhandling.taskcrudstrategies.create;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

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
    public String handleInput(ParsedCommand command, TaskList taskList) {
        if (command.getDescription().isBlank()) {
            throw new ChatbotArgumentException(
                String.format("The description for a task cannot be blank.\n"
                    + "Example usage: %s", getExampleUsage()));
        }

        Task addedTask = taskList.addTask(getTask(command));
        return String.format("Got it. I've added this %s:\n", addedTask.getTaskType().toString())
            + TaskString.withoutIndex(addedTask)
            + String.format("\nNow you have %d tasks in the list.", taskList.getSize());
    }

    /**
     * Returns an example usage of the command.
     *
     * @return The example usage string for this command.
     */
    public abstract String getExampleUsage();
}

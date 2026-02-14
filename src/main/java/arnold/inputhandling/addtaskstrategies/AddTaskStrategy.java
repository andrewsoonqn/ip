package arnold.inputhandling.addtaskstrategies;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Abstract strategy for adding a task to the task list.
 */
public abstract class AddTaskStrategy implements InputHandlingStrategy {
    /**
     * Returns a task object from the user input.
     *
     * @param input The user input to extract the task from.
     * @return The task object created from the input.
     */
    protected abstract Task getTask(String input);

    @Override
    public String handleInput(String input, TaskList taskList) {
        if (input.isBlank()) {
            throw new ChatbotArgumentException(
                String.format("The description for a task cannot be blank.\n"
                    + "Example usage: %s", getExampleUsage()));
        }

        Task addedTask = taskList.addTask(getTask(input));
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

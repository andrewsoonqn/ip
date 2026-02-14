package arnold.inputhandling.edittaskstrategies;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Strategy for handling the unmark command.
 */
public class UnmarkStrategy implements InputHandlingStrategy {
    /**
     * Handles the unmark command by marking the specified task as not done.
     *
     * @param input The ID of the task to unmark.
     * @param taskList The task list containing the task.
     * @return The response message confirming the task was unmarked.
     * @throws ChatbotArgumentException If the input is not a valid integer.
     */
    @Override
    public String handleInput(String input, TaskList taskList) {
        int taskId;
        try {
            taskId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ChatbotArgumentException("Please enter a valid task ID.");
        }
        Task task = taskList.unmarkTask(taskId);
        return "OK, I've marked this task as not done yet:\n"
            + TaskString.withoutIndex(task);
    }
}

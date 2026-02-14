package arnold.inputhandling.edittaskstrategies;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Strategy for handling the mark command.
 */
public class MarkStrategy implements InputHandlingStrategy {
    /**
     * Handles the mark command by marking the specified task as done.
     *
     * @param input The ID of the task to mark.
     * @param taskList The task list containing the task.
     * @return The response message confirming the task was marked.
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
        Task task = taskList.markTask(taskId);
        return "Nice! I've marked this task as done:\n"
            + TaskString.withoutIndex(task);
    }
}

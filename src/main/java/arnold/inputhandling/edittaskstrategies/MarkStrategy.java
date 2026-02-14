package arnold.inputhandling.edittaskstrategies;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.messaging.Messenger;
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
     * @param msg The messenger to use for communication.
     * @param taskList The task list containing the task.
     * @throws ChatbotArgumentException If the input is not a valid integer.
     */
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        int taskId;
        try {
            taskId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ChatbotArgumentException("Please enter a valid task ID.");
        }
        Task task = taskList.markTask(taskId);
        msg.printMessage(
            "Nice! I've marked this task as done:\n"
                + TaskString.withoutIndex(task));
    }
}

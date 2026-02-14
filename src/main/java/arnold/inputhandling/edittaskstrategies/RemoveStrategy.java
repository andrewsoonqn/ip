package arnold.inputhandling.edittaskstrategies;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.inputhandling.InputHandlingStrategy;
import arnold.messaging.Messenger;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Strategy for handling the remove command.
 */
public class RemoveStrategy implements InputHandlingStrategy {
    /**
     * Handles the remove command by deleting the specified task.
     *
     * @param input The ID of the task to remove.
     * @param msg The messenger to use for communication.
     * @param taskList The task list to remove the task from.
     * @throws NumberFormatException If the input is not a valid integer.
     */
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        int taskId;
        try {
            taskId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ChatbotArgumentException("Please enter a valid task ID.");
        }

        Task task = taskList.removeTask(taskId);
        msg.printMessage(
            "Noted. I've removed this task:\n"
                + TaskString.withoutIndex(task)
                + String.format("\nNow you have %d tasks in the list.", taskList.getSize()));
    }
}

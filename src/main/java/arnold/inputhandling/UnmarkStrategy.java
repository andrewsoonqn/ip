package arnold.inputhandling;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.messaging.Messenger;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

public class UnmarkStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        int taskId;
        try {
            taskId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ChatbotArgumentException("Please enter a valid task ID.");
        }
        Task task = taskList.unmarkTask(taskId);
        msg.printMessage(
                "OK, I've marked this task as not done yet:\n"
                        + TaskString.withoutIndex(task));
    }
}

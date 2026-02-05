package arnold.inputhandling;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.messaging.Messenger;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

public class MarkStrategy implements InputHandlingStrategy {
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

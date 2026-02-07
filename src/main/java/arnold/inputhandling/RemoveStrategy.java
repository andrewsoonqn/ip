package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

/**
 * Strategy for handling the remove command.
 */
public class RemoveStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        int taskId = Integer.parseInt(input);
        Task task = taskList.removeTask(taskId);
        msg.printMessage(
            "Noted. I've removed this task:\n"
                + TaskString.withoutIndex(task)
                + String.format("\nNow you have %d tasks in the list.", taskList.getSize()));
    }
}

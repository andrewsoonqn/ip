package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.Task;
import arnold.tasks.TaskList;
import arnold.tasks.TaskString;

public class UnmarkStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        int taskId = Integer.parseInt(input);
        Task task = taskList.unmarkTask(taskId);
        msg.printMessage(
                "OK, I've marked this task as not done yet:\n"
                        + TaskString.withoutIndex(task));
    }
}

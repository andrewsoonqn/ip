package InputHandling;

import Messaging.Messenger;
import Tasks.Task;
import Tasks.TaskList;
import Tasks.TaskString;

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

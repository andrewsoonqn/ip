package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;
import Tasks.TaskString;

public class UnmarkStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        int taskId = Integer.parseInt(input);
        taskList.unmarkTask(taskId);
        msg.printMessage(
                "OK, I've marked this task as not done yet:\n"
                        + TaskString.withoutId(taskList.getTask(taskId)));
    }
}

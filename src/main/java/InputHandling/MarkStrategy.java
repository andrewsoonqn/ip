package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;
import Tasks.TaskString;

public class MarkStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        int taskId = Integer.parseInt(input);
        taskList.markTask(taskId);
        msg.printMessage(
                "Nice! I've marked this task as done:\n"
                        + TaskString.withoutId(taskList.getTask(taskId)));
    }
}

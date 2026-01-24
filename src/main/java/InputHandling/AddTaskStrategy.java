package InputHandling;

import Messaging.Messenger;
import Tasks.Task;
import Tasks.TaskList;
import Tasks.TaskString;

public abstract class AddTaskStrategy implements InputHandlingStrategy {
    protected abstract Task getTask(String input);

    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        Task addedTask = taskList.addTask(getTask(input));
        msg.printMessage(
                "Got it. I've added this task:\n"
                        + TaskString.withoutId(addedTask)
                        + String.format("\nNow you have %d tasks in the list.", taskList.getSize()));
    }
}

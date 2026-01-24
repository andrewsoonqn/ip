package InputHandling;

import Messaging.Messenger;
import Tasks.Deadline;
import Tasks.Task;
import Tasks.TaskList;

import java.util.Map;

public abstract class AddTaskStrategy implements InputHandlingStrategy {
    protected abstract Task getTask(String input);
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        String message = taskList.addTask(getTask(input));
        msg.printMessage(message);
    }
}

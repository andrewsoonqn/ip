package InputHandling;

import Messaging.Messenger;
import Tasks.Task;
import Tasks.TaskList;

public class DefaultStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        Task task = new Task(input);
        String message = taskList.addTask(task);
        msg.printMessage(message);
    }
}
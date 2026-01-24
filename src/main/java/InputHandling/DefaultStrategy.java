package InputHandling;

import Messaging.Messenger;
import Tasks.Task;
import Tasks.TaskList;
import Tasks.Todo;

public class DefaultStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        Task task = new Todo(input);
        String message = taskList.addTask(task);
        msg.printMessage(message);
    }
}
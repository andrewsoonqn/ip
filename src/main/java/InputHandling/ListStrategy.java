package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;

public class ListStrategy implements InputHandlingStrategy {
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.printMessage(taskList.listTasks());
    }
}

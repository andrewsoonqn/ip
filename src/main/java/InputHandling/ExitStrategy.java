package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;

public class ExitStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.printMessage("Bye. Hope to see you again soon!");
        System.exit(0);
    }
}

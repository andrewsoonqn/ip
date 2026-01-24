package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;

public class HiStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.printMessage("Hello! I'm Arnold\nWhat can I do for you?");
    }
}

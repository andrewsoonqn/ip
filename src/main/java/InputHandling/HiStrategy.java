package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;

public class HiStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.hi();
    }
}

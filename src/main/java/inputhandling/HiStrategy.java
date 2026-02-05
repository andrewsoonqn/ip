package inputhandling;

import messaging.Messenger;
import tasks.TaskList;

public class HiStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.hi();
    }
}

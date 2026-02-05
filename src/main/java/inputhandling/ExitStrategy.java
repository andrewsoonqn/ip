package inputhandling;

import events.EventBus;
import messaging.Messenger;
import Tasks.TaskList;

public class ExitStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        msg.printMessage("Bye. Hope to see you again soon!");

        // Bot will receive and shut down
        EventBus.getInstance().publishShutdown();
    }
}

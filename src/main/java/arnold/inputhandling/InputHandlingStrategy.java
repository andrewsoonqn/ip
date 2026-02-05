package arnold.inputhandling;

import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

public interface InputHandlingStrategy {
    // Processes command and performs necessary actions
    void handleInput(String input, Messenger msg, TaskList taskList);
}

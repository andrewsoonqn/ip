package inputhandling;

import Messaging.Messenger;
import Tasks.TaskList;

public interface InputHandlingStrategy {
    // Processes command and performs necessary actions
    void handleInput(String input, Messenger msg, TaskList taskList);
}

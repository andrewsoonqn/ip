package inputhandling;

import chatbotexceptions.NoSuchCommandException;
import messaging.Messenger;
import tasks.TaskList;

public class DefaultStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        throw new NoSuchCommandException("Sorry, I don't recognise that command!");
    }
}
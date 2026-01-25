package InputHandling;

import ChatbotExceptions.NoSuchCommandException;
import Messaging.Messenger;
import Tasks.TaskList;

public class DefaultStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        throw new NoSuchCommandException("Sorry, I don't recognise that command!");
    }
}
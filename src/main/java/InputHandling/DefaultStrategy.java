package InputHandling;

import ChatbotExceptions.ChatBotException;
import Messaging.Messenger;
import Tasks.TaskList;

public class DefaultStrategy implements InputHandlingStrategy {
    @Override
    public void handleInput(String input, Messenger msg, TaskList taskList) {
        throw new ChatBotException("Sorry, I don't recognise that command!");
    }
}
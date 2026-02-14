package arnold;

import arnold.inputhandling.InputProcessor;
import arnold.inputhandling.commandstrategies.HiStrategy;
import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Main chatbot class.
 */
public class Arnold {
    private final InputProcessor inputProcessor;

    /**
     * Initializes a new instance of the Arnold chatbot.
     *
     * @param msg The messenger to use for communication.
     * @param taskList The task list to manage.
     */
    public Arnold(Messenger msg, TaskList taskList) {
        this.inputProcessor = new InputProcessor(msg, taskList);
    }

    /**
     * Greets the user.
     */
    public String hi() {
        return inputProcessor.processInput(new HiStrategy(), "");
    }

    /**
     * Processes the given input string and generates a response.
     *
     * @param input The raw input string provided by the user.
     * @return The response message generated based on the processed input.
     */
    public String getResponse(String input) {
        return inputProcessor.processInput(input);
    }
}

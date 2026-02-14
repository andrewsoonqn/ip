package arnold;

import arnold.inputhandling.CommandResult;
import arnold.inputhandling.InputProcessor;
import arnold.tasks.utils.TaskList;

/**
 * Main chatbot class.
 */
public class Arnold {
    private static final String GREETING = "Hello! I'm Arnold\nWhat can I do for you?";
    private final InputProcessor inputProcessor;

    /**
     * Initializes a new instance of the Arnold chatbot.
     *
     * @param taskList The task list to manage.
     */
    public Arnold(TaskList taskList) {
        this.inputProcessor = new InputProcessor(taskList);
    }

    /**
     * Greets the user.
     *
     * @return The greeting message.
     */
    public String hi() {
        return GREETING;
    }

    /**
     * Processes the given input string and generates a response.
     *
     * @param input The raw input string provided by the user.
     * @return The response message generated based on the processed input.
     */
    public CommandResult getResponse(String input) {
        return inputProcessor.processInput(input);
    }
}

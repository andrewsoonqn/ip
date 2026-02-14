package arnold;

import arnold.inputhandling.InputProcessor;
import arnold.inputhandling.commandstrategies.HiStrategy;
import arnold.tasks.TaskList;

/**
 * Main chatbot class.
 */
public class Arnold {
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

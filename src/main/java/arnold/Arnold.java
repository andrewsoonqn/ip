package arnold;

import arnold.inputhandling.HiStrategy;
import arnold.inputhandling.InputProcessor;
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
    public void hi() {
        inputProcessor.processInput(new HiStrategy(), "");
    }

    public String getResponse(String input) {
        return inputProcessor.processInput(input);
    }
}

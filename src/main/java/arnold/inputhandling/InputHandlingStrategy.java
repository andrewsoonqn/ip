package arnold.inputhandling;

import arnold.tasks.TaskList;

/**
 * Interface for input handling strategies.
 */
public interface InputHandlingStrategy {
    /**
     * Handles the user input.
     *
     * @param input The user input to handle.
     * @param taskList The task list to manage.
     * @return The response message.
     * @throws arnold.chatbotexceptions.ChatbotException If an error occurs during processing.
     */
    String handleInput(String input, TaskList taskList);
}

package arnold.inputhandling;

import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.utils.TaskList;

/**
 * Interface for input handling strategies.
 */
public interface InputHandlingStrategy {
    /**
     * Handles the parsed command.
     *
     * @param command The parsed command containing description and flags.
     * @param taskList The task list to manage.
     * @return The response message.
     * @throws arnold.chatbotexceptions.ChatbotException If an error occurs during processing.
     */
    String handleInput(ParsedCommand command, TaskList taskList);
}

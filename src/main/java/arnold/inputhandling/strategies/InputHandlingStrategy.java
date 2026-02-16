package arnold.inputhandling.strategies;

import arnold.inputhandling.CommandResult;
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
     * @return The command result containing the response message and status.
     * @throws arnold.chatbotexceptions.ChatbotException If an error occurs during processing.
     */
    CommandResult handleInput(ParsedCommand command, TaskList taskList);

    /**
     * Returns a description of what this command does.
     *
     * @return The description string, or empty if not provided.
     */
    default String getDescription() {
        return "";
    }

    /**
     * Returns an example usage of the command.
     *
     * @return The example usage string, or empty if not provided.
     */
    default String getExampleUsage() {
        return "";
    }
}

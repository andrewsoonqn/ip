package arnold.inputhandling;

import java.util.Map;

import arnold.chatbotexceptions.ChatbotException;
import arnold.inputhandling.addtaskstrategies.DeadlineStrategy;
import arnold.inputhandling.addtaskstrategies.EventStrategy;
import arnold.inputhandling.addtaskstrategies.TodoStrategy;
import arnold.inputhandling.commandstrategies.DefaultStrategy;
import arnold.inputhandling.commandstrategies.ExitStrategy;
import arnold.inputhandling.edittaskstrategies.MarkStrategy;
import arnold.inputhandling.edittaskstrategies.RemoveStrategy;
import arnold.inputhandling.edittaskstrategies.UnmarkStrategy;
import arnold.inputhandling.parsing.ArgParser;
import arnold.inputhandling.viewtaskstrategies.FindStrategy;
import arnold.inputhandling.viewtaskstrategies.ListStrategy;
import arnold.tasks.TaskList;

/**
 * Processes user input and executes commands using appropriate strategies.
 */
public class InputProcessor {
    private static final Map<String, InputHandlingStrategy> COMMAND_STRATEGIES = Map.of(
        "bye", new ExitStrategy(),
        "list", new ListStrategy(),
        "mark", new MarkStrategy(),
        "unmark", new UnmarkStrategy(),
        "todo", new TodoStrategy(),
        "deadline", new DeadlineStrategy(),
        "event", new EventStrategy(),
        "delete", new RemoveStrategy(),
        "find", new FindStrategy()
    );
    private final TaskList taskList;

    /**
     * Initializes a new instance of the InputProcessor.
     *
     * @param taskList The task list to manage.
     */
    public InputProcessor(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Processes the user input string and executes the corresponding command.
     *
     * @param input The raw input string from the user.
     * @return The response message after processing the input.
     */
    public String processInput(String input) {
        String[] commandArgs = ArgParser.getCommandArgs(input);
        String command = commandArgs[0];
        String arg = commandArgs[1];

        try {
            InputHandlingStrategy strategy = COMMAND_STRATEGIES.getOrDefault(
                command, new DefaultStrategy());
            return strategy.handleInput(arg, taskList);
        } catch (ChatbotException e) {
            return e.getMessage();
        }
    }

    /**
     * Processes user input using a specific strategy.
     *
     * @param strategy The strategy to use for processing.
     * @param arg The argument for the strategy.
     * @return The response message.
     */
    public String processInput(InputHandlingStrategy strategy, String arg) {
        return strategy.handleInput(arg, taskList);
    }
}

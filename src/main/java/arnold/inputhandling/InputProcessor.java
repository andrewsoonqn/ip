package arnold.inputhandling;

import java.util.Map;

import arnold.chatbotexceptions.ChatbotException;
import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

/**
 * Processes user input and executes commands using appropriate strategies.
 */
public class InputProcessor {
    private static final Map<String, InputHandlingStrategy> commandStrategies = Map.of(
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
    private final Messenger msg;
    private final TaskList taskList;

    /**
     * Initializes a new instance of the InputProcessor.
     *
     * @param msg The messenger to use for communication.
     * @param taskList The task list to manage.
     */
    public InputProcessor(Messenger msg, TaskList taskList) {
        this.msg = msg;
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
            InputHandlingStrategy strategy = commandStrategies.get(command);
            if (strategy != null) {
                strategy.handleInput(arg, msg, taskList);
            } else {
                new DefaultStrategy().handleInput(input, msg, taskList);
            }
        } catch (ChatbotException e) {
            msg.printMessage(e.getMessage());
        }
        return msg.getLastMessage();
    }

    /**
     * Processes user input using a specific strategy.
     *
     * @param strategy The strategy to use for processing.
     * @param arg The argument for the strategy.
     * @return The response message.
     */
    public String processInput(InputHandlingStrategy strategy, String arg) {
        strategy.handleInput(arg, msg, taskList);
        return msg.getLastMessage();
    }
}

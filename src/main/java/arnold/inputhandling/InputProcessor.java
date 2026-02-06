package arnold.inputhandling;

import java.util.Map;

import arnold.chatbotexceptions.ChatbotException;
import arnold.messaging.Messenger;
import arnold.tasks.TaskList;

public class InputProcessor {
    private static final Map<String, InputHandlingStrategy> commandStrategies = Map.of(
            "bye", new ExitStrategy(),
            "list", new ListStrategy(),
            "mark", new MarkStrategy(),
            "unmark", new UnmarkStrategy(),
            "todo", new TodoStrategy(),
            "deadline", new DeadlineStrategy(),
            "event", new EventStrategy(),
            "delete", new RemoveStrategy()
    );
    private final Messenger msg;
    private final TaskList taskList;

    public InputProcessor(Messenger msg, TaskList taskList) {
        this.msg = msg;
        this.taskList = taskList;
    }

    public void processInput(String input) {
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
    }

    public void processInput(InputHandlingStrategy strategy, String arg) {
        strategy.handleInput(arg, msg, taskList);
    }
}

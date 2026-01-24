package InputHandling;

import Messaging.Messenger;
import Tasks.TaskList;

import java.util.Map;

public class InputProcessor {
    private Messenger msg;
    private TaskList taskList;

    public InputProcessor(Messenger msg, TaskList taskList) {
        this.msg = msg;
        this.taskList = taskList;
    }

    private static final Map<String, InputHandlingStrategy> commandStrategies = Map.of(
            "bye", new ExitStrategy(),
            "list", new ListStrategy(),
            "mark", new MarkStrategy(),
            "unmark", new UnmarkStrategy()
    );

    public void processInput(String input) {
        String[] commandParts = input.strip().toLowerCase().split(" ");
        String command = commandParts[0];
        String arg = commandParts.length > 1 ? commandParts[1] : null;

        InputHandlingStrategy strategy = commandStrategies.get(command);
        if (strategy != null) {
            strategy.handleInput(arg, msg, taskList);
        } else {
            new DefaultStrategy().handleInput(input, msg, taskList);
        }
    }

    public void processInput(InputHandlingStrategy strategy, String arg) {
        strategy.handleInput(arg, msg, taskList);
    }
}

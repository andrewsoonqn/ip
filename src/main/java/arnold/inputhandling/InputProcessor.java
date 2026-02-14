package arnold.inputhandling;

import arnold.chatbotexceptions.ChatbotException;
import arnold.inputhandling.parsing.Parser;
import arnold.inputhandling.strategies.ExitStrategy;
import arnold.inputhandling.strategies.HelpStrategy;
import arnold.inputhandling.strategies.taskcrudstrategies.create.DeadlineStrategy;
import arnold.inputhandling.strategies.taskcrudstrategies.create.EventStrategy;
import arnold.inputhandling.strategies.taskcrudstrategies.create.TodoStrategy;
import arnold.inputhandling.strategies.taskcrudstrategies.delete.RemoveStrategy;
import arnold.inputhandling.strategies.taskcrudstrategies.read.FindStrategy;
import arnold.inputhandling.strategies.taskcrudstrategies.read.ListStrategy;
import arnold.inputhandling.strategies.taskcrudstrategies.update.MarkStrategy;
import arnold.inputhandling.strategies.taskcrudstrategies.update.UnmarkStrategy;
import arnold.tasks.utils.TaskList;

/**
 * Processes user input and executes commands using appropriate strategies.
 */
public class InputProcessor {
    private final Parser parser;
    private final TaskList taskList;

    /**
     * Initializes a new instance of the InputProcessor.
     *
     * @param taskList The task list to manage.
     */
    public InputProcessor(TaskList taskList) {
        this.parser = makeDefaultParser();
        this.taskList = taskList;
    }

    private static Parser makeDefaultParser() {
        Parser parser = new Parser();

        parser.register("bye", new ExitStrategy());
        parser.register("list", new ListStrategy());
        parser.register("mark", new MarkStrategy());
        parser.register("unmark", new UnmarkStrategy());
        parser.register("todo", new TodoStrategy());
        parser.register("delete", new RemoveStrategy());
        parser.register("find", new FindStrategy());
        parser.register("deadline", new DeadlineStrategy(), "by");
        parser.register("event", new EventStrategy(), "from", "to");
        parser.register("help", new HelpStrategy(parser));

        return parser;
    }

    /**
     * Processes the user input string and executes the corresponding command.
     *
     * @param input The raw input string from the user.
     * @return The command result after processing the input.
     */
    public CommandResult processInput(String input) {
        try {
            Parser.Result result = parser.parse(input);
            return result.strategy().handleInput(result.command(), taskList);
        } catch (ChatbotException e) {
            return CommandResult.error(e.getMessage());
        }
    }
}

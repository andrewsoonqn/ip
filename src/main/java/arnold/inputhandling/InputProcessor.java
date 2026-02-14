package arnold.inputhandling;

import arnold.chatbotexceptions.ChatbotException;
import arnold.inputhandling.parsing.Parser;
import arnold.inputhandling.taskcrudstrategies.create.DeadlineStrategy;
import arnold.inputhandling.taskcrudstrategies.create.EventStrategy;
import arnold.inputhandling.taskcrudstrategies.create.TodoStrategy;
import arnold.inputhandling.taskcrudstrategies.delete.RemoveStrategy;
import arnold.inputhandling.taskcrudstrategies.read.FindStrategy;
import arnold.inputhandling.taskcrudstrategies.read.ListStrategy;
import arnold.inputhandling.taskcrudstrategies.update.MarkStrategy;
import arnold.inputhandling.taskcrudstrategies.update.UnmarkStrategy;
import arnold.tasks.TaskList;

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
        parser.register("deadline", new DeadlineStrategy(), "/by");
        parser.register("event", new EventStrategy(), "/from", "/to");

        return parser;
    }

    /**
     * Processes the user input string and executes the corresponding command.
     *
     * @param input The raw input string from the user.
     * @return The response message after processing the input.
     */
    public String processInput(String input) {
        try {
            Parser.Result result = parser.parse(input);
            return result.strategy().handleInput(result.command(), taskList);
        } catch (ChatbotException e) {
            return e.getMessage();
        }
    }
}

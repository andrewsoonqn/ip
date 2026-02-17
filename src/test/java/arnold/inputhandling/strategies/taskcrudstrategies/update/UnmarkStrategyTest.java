package arnold.inputhandling.strategies.taskcrudstrategies.update;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.datapersistence.NullStorage;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Todo;
import arnold.tasks.utils.TaskList;

public class UnmarkStrategyTest {
    private UnmarkStrategy strategy;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        strategy = new UnmarkStrategy();
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void handleInput_validIndex_unmarksTask() {
        taskList.addTask(new Todo("test"));
        taskList.markTask(1);
        ParsedCommand command = new ParsedCommand("1", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertFalse(taskList.getTask(1).isDone());
    }

    @Test
    public void handleInput_resultContainsTaskInfo() {
        taskList.addTask(new Todo("test"));
        taskList.markTask(1);
        ParsedCommand command = new ParsedCommand("1", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertTrue(result.getMessage().contains("not done"));
        assertTrue(result.getMessage().contains("[ ]"));
    }

    @Test
    public void handleInput_invalidIndex_throwsException() {
        ParsedCommand command = new ParsedCommand("1", Map.of());
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void handleInput_nonNumericIndex_throwsException() {
        ParsedCommand command = new ParsedCommand("abc", Map.of());
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void getDescription_returnsDescription() {
        assertTrue(strategy.getDescription().contains("not done"));
    }

    @Test
    public void getExampleUsage_returnsExample() {
        assertTrue(strategy.getExampleUsage().contains("unmark"));
    }
}

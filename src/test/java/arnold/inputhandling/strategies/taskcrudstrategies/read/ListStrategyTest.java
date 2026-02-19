package arnold.inputhandling.strategies.taskcrudstrategies.read;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arnold.datapersistence.NullStorage;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.parsing.ParsedCommand;
import arnold.tasks.Todo;
import arnold.tasks.utils.TaskList;

public class ListStrategyTest {
    private ListStrategy strategy;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        strategy = new ListStrategy();
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void handleInput_emptyList_returnsEmptyList() {
        ParsedCommand command = new ParsedCommand("", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertTrue(result.getMessage().contains("Here are the tasks"));
    }

    @Test
    public void handleInput_nonEmptyList_returnsAllTasks() {
        taskList.addTask(new Todo("task1"));
        taskList.addTask(new Todo("task2"));
        ParsedCommand command = new ParsedCommand("", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertTrue(result.getMessage().contains("task1"));
        assertTrue(result.getMessage().contains("task2"));
        assertTrue(result.getMessage().contains("1."));
        assertTrue(result.getMessage().contains("2."));
    }

    @Test
    public void getDescription_returnsDescription() {
        assertTrue(strategy.getDescription().contains("List"));
    }

    @Test
    public void getExampleUsage_returnsExample() {
        assertTrue(strategy.getExampleUsage().contains("list"));
    }
}

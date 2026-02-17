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

public class FindStrategyTest {
    private FindStrategy strategy;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        strategy = new FindStrategy();
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void handleInput_matchingKeyword_returnsMatchingTasks() {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("write code"));
        taskList.addTask(new Todo("read paper"));
        ParsedCommand command = new ParsedCommand("read", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertTrue(result.getMessage().contains("read book"));
        assertTrue(result.getMessage().contains("read paper"));
        assertFalse(result.getMessage().contains("write code"));
    }

    @Test
    public void handleInput_caseInsensitive_returnsMatchingTasks() {
        taskList.addTask(new Todo("Read Book"));
        ParsedCommand command = new ParsedCommand("read", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertTrue(result.getMessage().contains("Read Book"));
    }

    @Test
    public void handleInput_noMatches_returnsEmptyList() {
        taskList.addTask(new Todo("read book"));
        ParsedCommand command = new ParsedCommand("xyz", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertTrue(result.getMessage().contains("matching"));
    }

    @Test
    public void handleInput_emptyTaskList_returnsEmptyList() {
        ParsedCommand command = new ParsedCommand("read", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
    }

    @Test
    public void getDescription_returnsDescription() {
        assertTrue(strategy.getDescription().contains("Find"));
    }

    @Test
    public void getExampleUsage_returnsExample() {
        assertTrue(strategy.getExampleUsage().contains("find"));
    }
}

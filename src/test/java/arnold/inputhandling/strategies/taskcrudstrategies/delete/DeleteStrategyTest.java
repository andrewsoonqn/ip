package arnold.inputhandling.strategies.taskcrudstrategies.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class DeleteStrategyTest {
    private DeleteStrategy strategy;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        strategy = new DeleteStrategy();
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void handleInput_validIndex_removesTask() {
        taskList.addTask(new Todo("task1"));
        taskList.addTask(new Todo("task2"));
        ParsedCommand command = new ParsedCommand("1", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertEquals(1, taskList.getSize());
        assertEquals("task2", taskList.getTask(1).getDescription());
    }

    @Test
    public void handleInput_resultContainsTaskInfo() {
        taskList.addTask(new Todo("task1"));
        ParsedCommand command = new ParsedCommand("1", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertTrue(result.getMessage().contains("task1"));
        assertTrue(result.getMessage().contains("0"));
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
        assertEquals("Delete a task", strategy.getDescription());
    }

    @Test
    public void getExampleUsage_returnsExample() {
        assertEquals("delete 1", strategy.getExampleUsage());
    }
}

package arnold.inputhandling.strategies.taskcrudstrategies.create;

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

public class TodoStrategyTest {
    private TodoStrategy strategy;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        strategy = new TodoStrategy();
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void handleInput_validDescription_addsTask() {
        ParsedCommand command = new ParsedCommand("read book", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertEquals(1, taskList.getSize());
        assertTrue(taskList.getTask(1) instanceof Todo);
        assertEquals("read book", taskList.getTask(1).getDescription());
    }

    @Test
    public void handleInput_blankDescription_throwsException() {
        ParsedCommand command = new ParsedCommand("", Map.of());
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void handleInput_whitespaceOnlyDescription_throwsException() {
        ParsedCommand command = new ParsedCommand("   ", Map.of());
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void handleInput_resultContainsTaskInfo() {
        ParsedCommand command = new ParsedCommand("read book", Map.of());
        CommandResult result = strategy.handleInput(command, taskList);
        assertTrue(result.getMessage().contains("todo"));
        assertTrue(result.getMessage().contains("[T][ ] read book"));
        assertTrue(result.getMessage().contains("1"));
    }

    @Test
    public void getDescription_returnsDescription() {
        assertEquals("Add a todo task", strategy.getDescription());
    }

    @Test
    public void getExampleUsage_returnsExample() {
        assertEquals("todo read book", strategy.getExampleUsage());
    }
}

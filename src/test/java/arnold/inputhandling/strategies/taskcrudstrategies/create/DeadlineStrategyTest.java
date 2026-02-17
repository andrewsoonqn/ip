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
import arnold.tasks.Deadline;
import arnold.tasks.utils.TaskList;

public class DeadlineStrategyTest {
    private DeadlineStrategy strategy;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        strategy = new DeadlineStrategy();
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void handleInput_validDescriptionAndDate_addsTask() {
        ParsedCommand command = new ParsedCommand("submit report", Map.of("by", "25/12/2026 1400"));
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertEquals(1, taskList.getSize());
        assertTrue(taskList.getTask(1) instanceof Deadline);
        assertEquals("submit report", taskList.getTask(1).getDescription());
    }

    @Test
    public void handleInput_blankDescription_throwsException() {
        ParsedCommand command = new ParsedCommand("", Map.of("by", "25/12/2026 1400"));
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void handleInput_invalidDateFormat_throwsException() {
        ParsedCommand command = new ParsedCommand("submit report", Map.of("by", "invalid"));
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void handleInput_resultContainsTaskInfo() {
        ParsedCommand command = new ParsedCommand("submit report", Map.of("by", "25/12/2026 1400"));
        CommandResult result = strategy.handleInput(command, taskList);
        assertTrue(result.getMessage().contains("deadline"));
        assertTrue(result.getMessage().contains("[D][ ] submit report"));
        assertTrue(result.getMessage().contains("25/12/2026 1400"));
    }

    @Test
    public void getDescription_returnsDescription() {
        assertEquals("Add a task with a deadline", strategy.getDescription());
    }

    @Test
    public void getExampleUsage_returnsExample() {
        assertEquals("deadline submit report /by 1/12/2026 2359", strategy.getExampleUsage());
    }
}

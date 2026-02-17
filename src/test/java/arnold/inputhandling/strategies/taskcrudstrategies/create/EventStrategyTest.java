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
import arnold.tasks.Event;
import arnold.tasks.utils.TaskList;

public class EventStrategyTest {
    private EventStrategy strategy;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        strategy = new EventStrategy();
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void handleInput_validDescriptionAndTimes_addsTask() {
        ParsedCommand command = new ParsedCommand("meeting",
            Map.of("from", "25/12/2026 1000", "to", "25/12/2026 1200"));
        CommandResult result = strategy.handleInput(command, taskList);
        assertFalse(result.isError());
        assertEquals(1, taskList.getSize());
        assertTrue(taskList.getTask(1) instanceof Event);
        assertEquals("meeting", taskList.getTask(1).getDescription());
    }

    @Test
    public void handleInput_blankDescription_throwsException() {
        ParsedCommand command = new ParsedCommand("",
            Map.of("from", "25/12/2026 1000", "to", "25/12/2026 1200"));
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void handleInput_invalidFromDate_throwsException() {
        ParsedCommand command = new ParsedCommand("meeting",
            Map.of("from", "invalid", "to", "25/12/2026 1200"));
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void handleInput_invalidToDate_throwsException() {
        ParsedCommand command = new ParsedCommand("meeting",
            Map.of("from", "25/12/2026 1000", "to", "invalid"));
        assertThrows(ChatbotArgumentException.class, () -> strategy.handleInput(command, taskList));
    }

    @Test
    public void handleInput_resultContainsTaskInfo() {
        ParsedCommand command = new ParsedCommand("meeting",
            Map.of("from", "25/12/2026 1000", "to", "25/12/2026 1200"));
        CommandResult result = strategy.handleInput(command, taskList);
        assertTrue(result.getMessage().contains("event"));
        assertTrue(result.getMessage().contains("[E][ ] meeting"));
        assertTrue(result.getMessage().contains("25/12/2026 1000"));
        assertTrue(result.getMessage().contains("25/12/2026 1200"));
    }

    @Test
    public void getDescription_returnsDescription() {
        assertEquals("Add an event with start and end times", strategy.getDescription());
    }

    @Test
    public void getExampleUsage_returnsExample() {
        assertEquals("event attend meeting /from 1/12/2026 1000 /to 1/12/2026 1200",
            strategy.getExampleUsage());
    }
}

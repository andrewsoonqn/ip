package arnold.inputhandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arnold.datapersistence.NullStorage;
import arnold.tasks.utils.TaskList;

public class InputProcessorTest {
    private InputProcessor processor;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = TaskList.create(new NullStorage());
        processor = new InputProcessor(taskList);
    }

    @Test
    public void processInput_todoCommand_addsTask() {
        CommandResult result = processor.processInput("todo read book");
        assertFalse(result.isError());
        assertEquals(1, taskList.getSize());
        assertEquals("read book", taskList.getTask(1).getDescription());
    }

    @Test
    public void processInput_deadlineCommand_addsTask() {
        CommandResult result = processor.processInput("deadline submit /by 25/12/2026 1400");
        assertFalse(result.isError());
        assertEquals(1, taskList.getSize());
        assertEquals("submit", taskList.getTask(1).getDescription());
    }

    @Test
    public void processInput_eventCommand_addsTask() {
        CommandResult result = processor.processInput(
            "event meeting /from 25/12/2026 1000 /to 25/12/2026 1200");
        assertFalse(result.isError());
        assertEquals(1, taskList.getSize());
        assertEquals("meeting", taskList.getTask(1).getDescription());
    }

    @Test
    public void processInput_listCommand_success() {
        processor.processInput("todo task1");
        CommandResult result = processor.processInput("list");
        assertFalse(result.isError());
        assertTrue(result.getMessage().contains("task1"));
    }

    @Test
    public void processInput_markCommand_marksTask() {
        processor.processInput("todo test");
        CommandResult result = processor.processInput("mark 1");
        assertFalse(result.isError());
        assertTrue(taskList.getTask(1).isDone());
    }

    @Test
    public void processInput_unmarkCommand_unmarksTask() {
        processor.processInput("todo test");
        processor.processInput("mark 1");
        CommandResult result = processor.processInput("unmark 1");
        assertFalse(result.isError());
        assertFalse(taskList.getTask(1).isDone());
    }

    @Test
    public void processInput_deleteCommand_removesTask() {
        processor.processInput("todo test");
        CommandResult result = processor.processInput("delete 1");
        assertFalse(result.isError());
        assertEquals(0, taskList.getSize());
    }

    @Test
    public void processInput_findCommand_findsMatchingTasks() {
        processor.processInput("todo read book");
        processor.processInput("todo write code");
        CommandResult result = processor.processInput("find book");
        assertFalse(result.isError());
        assertTrue(result.getMessage().contains("read book"));
        assertFalse(result.getMessage().contains("write code"));
    }

    @Test
    public void processInput_byeCommand_returnsExitResult() {
        CommandResult result = processor.processInput("bye");
        assertTrue(result.shouldExit());
    }

    @Test
    public void processInput_helpCommand_returnsCommandList() {
        CommandResult result = processor.processInput("help");
        assertFalse(result.isError());
        assertTrue(result.getMessage().contains("todo"));
        assertTrue(result.getMessage().contains("deadline"));
        assertTrue(result.getMessage().contains("event"));
    }

    @Test
    public void processInput_unknownCommand_returnsError() {
        CommandResult result = processor.processInput("unknown");
        assertTrue(result.isError());
    }

    @Test
    public void processInput_blankTodoDescription_returnsError() {
        CommandResult result = processor.processInput("todo  ");
        assertTrue(result.isError());
    }

    @Test
    public void processInput_invalidMarkIndex_returnsError() {
        CommandResult result = processor.processInput("mark abc");
        assertTrue(result.isError());
    }

    @Test
    public void processInput_markOutOfBounds_returnsError() {
        CommandResult result = processor.processInput("mark 1");
        assertTrue(result.isError());
    }

    @Test
    public void processInput_deadlineMissingByFlag_returnsError() {
        CommandResult result = processor.processInput("deadline submit report");
        assertTrue(result.isError());
    }

    @Test
    public void processInput_eventMissingFlags_returnsError() {
        CommandResult result = processor.processInput("event meeting");
        assertTrue(result.isError());
    }

    @Test
    public void processInput_deadlineInvalidDate_returnsError() {
        CommandResult result = processor.processInput("deadline submit /by invalid");
        assertTrue(result.isError());
    }
}

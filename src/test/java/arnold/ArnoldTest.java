package arnold;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arnold.datapersistence.NullStorage;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.Messages;
import arnold.tasks.utils.TaskList;

public class ArnoldTest {
    private Arnold arnold;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = TaskList.create(new NullStorage());
        arnold = new Arnold(taskList);
    }

    @Test
    public void hi_returnsWelcomeMessage() {
        assertEquals(Messages.welcomeMessage(), arnold.hi());
    }

    @Test
    public void getResponse_validTodoCommand_returnsSuccess() {
        CommandResult result = arnold.getResponse("todo read book");
        assertFalse(result.isError());
        assertFalse(result.shouldExit());
        assertEquals(1, taskList.getSize());
    }

    @Test
    public void getResponse_invalidCommand_returnsError() {
        CommandResult result = arnold.getResponse("invalid command");
        assertTrue(result.isError());
    }

    @Test
    public void getResponse_byeCommand_returnsExitResult() {
        CommandResult result = arnold.getResponse("bye");
        assertTrue(result.shouldExit());
        assertFalse(result.isError());
    }

    @Test
    public void getResponse_multipleCommands_maintainsState() {
        arnold.getResponse("todo task1");
        arnold.getResponse("todo task2");
        assertEquals(2, taskList.getSize());

        arnold.getResponse("delete 1");
        assertEquals(1, taskList.getSize());
    }
}

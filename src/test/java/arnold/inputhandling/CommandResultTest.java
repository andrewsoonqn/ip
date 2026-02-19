package arnold.inputhandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {

    @Test
    public void success_createsNonErrorNonExitResult() {
        CommandResult result = CommandResult.success("ok");
        assertEquals("ok", result.getMessage());
        assertFalse(result.isError());
        assertFalse(result.shouldExit());
    }

    @Test
    public void error_createsErrorNonExitResult() {
        CommandResult result = CommandResult.error("bad");
        assertEquals("bad", result.getMessage());
        assertTrue(result.isError());
        assertFalse(result.shouldExit());
    }

    @Test
    public void exit_createsNonErrorExitResult() {
        CommandResult result = CommandResult.exit("bye");
        assertEquals("bye", result.getMessage());
        assertFalse(result.isError());
        assertTrue(result.shouldExit());
    }

    @Test
    public void getMessage_returnsCorrectMessage() {
        CommandResult result = CommandResult.success("test message");
        assertEquals("test message", result.getMessage());
    }

    @Test
    public void success_emptyMessage_success() {
        CommandResult result = CommandResult.success("");
        assertEquals("", result.getMessage());
        assertFalse(result.isError());
    }
}

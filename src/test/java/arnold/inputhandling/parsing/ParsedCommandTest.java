package arnold.inputhandling.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ParsedCommandTest {

    @Test
    public void getDescription_returnsDescription() {
        ParsedCommand command = new ParsedCommand("read book", Map.of());
        assertEquals("read book", command.getDescription());
    }

    @Test
    public void getDescription_emptyDescription_returnsEmpty() {
        ParsedCommand command = new ParsedCommand("", Map.of());
        assertEquals("", command.getDescription());
    }

    @Test
    public void getFlag_existingFlag_returnsValue() {
        Map<String, String> flags = new HashMap<>();
        flags.put("by", "tomorrow");
        ParsedCommand command = new ParsedCommand("task", flags);
        assertEquals("tomorrow", command.getFlag("by"));
    }

    @Test
    public void getFlag_nonExistentFlag_returnsNull() {
        ParsedCommand command = new ParsedCommand("task", Map.of());
        assertNull(command.getFlag("by"));
    }

    @Test
    public void getFlag_multipleFlags_returnsCorrectValues() {
        Map<String, String> flags = new HashMap<>();
        flags.put("from", "Mon 2pm");
        flags.put("to", "Mon 4pm");
        ParsedCommand command = new ParsedCommand("meeting", flags);
        assertEquals("Mon 2pm", command.getFlag("from"));
        assertEquals("Mon 4pm", command.getFlag("to"));
    }
}

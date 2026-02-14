package arnold.inputhandling.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.chatbotexceptions.NoSuchCommandException;
import arnold.inputhandling.CommandResult;
import arnold.inputhandling.strategies.InputHandlingStrategy;

// Tests generated via Junie by JetBrains
public class ParserTest {
    private Parser parser;
    private InputHandlingStrategy dummyStrategy;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
        dummyStrategy = (command, taskList) -> CommandResult.success("dummy");
    }

    @Test
    public void parse_validCommandWithFlags_success() {
        parser.register("deadline", dummyStrategy, "by");
        Parser.Result result = parser.parse("deadline submit report /by tomorrow");
        assertEquals(dummyStrategy, result.strategy());
        assertEquals("submit report", result.command().getDescription());
        assertEquals("tomorrow", result.command().getFlag("by"));
    }

    @Test
    public void parse_validCommandWithMultipleFlags_success() {
        parser.register("event", dummyStrategy, "from", "to");
        Parser.Result result = parser.parse("event meeting /from Mon 2pm /to Mon 4pm");
        assertEquals(dummyStrategy, result.strategy());
        assertEquals("meeting", result.command().getDescription());
        assertEquals("Mon 2pm", result.command().getFlag("from"));
        assertEquals("Mon 4pm", result.command().getFlag("to"));
    }

    @Test
    public void parse_commandWithoutFlags_success() {
        parser.register("todo", dummyStrategy);
        Parser.Result result = parser.parse("todo read book");
        assertEquals(dummyStrategy, result.strategy());
        assertEquals("read book", result.command().getDescription());
    }

    @Test
    public void parse_missingRequiredFlag_throwsException() {
        parser.register("deadline", dummyStrategy, "by");
        assertThrows(ChatbotArgumentException.class, () -> parser.parse("deadline submit report"));
    }

    @Test
    public void parse_blankRequiredFlag_throwsException() {
        parser.register("deadline", dummyStrategy, "by");
        assertThrows(ChatbotArgumentException.class, () -> parser.parse("deadline submit report /by "));
    }

    @Test
    public void parse_unregisteredCommand_throwsException() {
        assertThrows(NoSuchCommandException.class, () -> parser.parse("unknown command"));
    }

    @Test
    public void parse_malformedFlagMissingSpace_throwsException() {
        parser.register("deadline", dummyStrategy, "by");
        // "deadline submit report/by tomorrow" -> description will be "submit report/by tomorrow",
        // flag "by" will be missing
        assertThrows(ChatbotArgumentException.class, () -> parser.parse("deadline submit report/by tomorrow"));
    }

    @Test
    public void parse_extraFlags_ignored() {
        parser.register("todo", dummyStrategy);
        Parser.Result result = parser.parse("todo read book /by tomorrow");
        assertEquals("read book", result.command().getDescription());
        assertEquals("tomorrow", result.command().getFlag("by"));
    }

    @Test
    public void parse_multipleSpaces_success() {
        parser.register("deadline", dummyStrategy, "by");
        Parser.Result result = parser.parse("deadline   submit report   /by   tomorrow  ");
        assertEquals("submit report", result.command().getDescription());
        assertEquals("tomorrow", result.command().getFlag("by"));
    }

    @Test
    public void parse_emptyDescription_success() {
        parser.register("deadline", dummyStrategy, "by");
        Parser.Result result = parser.parse("deadline /by tomorrow");
        assertEquals("", result.command().getDescription());
        assertEquals("tomorrow", result.command().getFlag("by"));
    }
}

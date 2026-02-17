package arnold.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import arnold.tasks.utils.TaskType;

public class EventTest {

    @Test
    public void constructor_validDescriptionAndTimes_success() {
        LocalDateTime from = LocalDateTime.of(2026, 12, 20, 10, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 20, 12, 0);
        Event event = new Event("meeting", from, to);
        assertEquals("meeting", event.getDescription());
        assertFalse(event.isDone());
    }

    @Test
    public void toString_notDone_correctFormat() {
        LocalDateTime from = LocalDateTime.of(2026, 12, 20, 10, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 20, 12, 0);
        Event event = new Event("meeting", from, to);
        assertEquals("[E][ ] meeting (from: 20/12/2026 1000 to: 20/12/2026 1200)", event.toString());
    }

    @Test
    public void toString_done_correctFormat() {
        LocalDateTime from = LocalDateTime.of(2026, 12, 20, 10, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 20, 12, 0);
        Event event = new Event("meeting", from, to);
        event.mark();
        assertEquals("[E][X] meeting (from: 20/12/2026 1000 to: 20/12/2026 1200)", event.toString());
    }

    @Test
    public void toString_sameDay_correctFormat() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 5, 9, 0);
        LocalDateTime to = LocalDateTime.of(2026, 1, 5, 17, 0);
        Event event = new Event("work", from, to);
        assertEquals("[E][ ] work (from: 5/1/2026 0900 to: 5/1/2026 1700)", event.toString());
    }

    @Test
    public void toString_differentDays_correctFormat() {
        LocalDateTime from = LocalDateTime.of(2026, 6, 1, 8, 0);
        LocalDateTime to = LocalDateTime.of(2026, 6, 3, 18, 0);
        Event event = new Event("conference", from, to);
        assertEquals("[E][ ] conference (from: 1/6/2026 0800 to: 3/6/2026 1800)", event.toString());
    }

    @Test
    public void getTaskType_returnsEvent() {
        LocalDateTime from = LocalDateTime.of(2026, 12, 20, 10, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 20, 12, 0);
        Event event = new Event("test", from, to);
        assertEquals(TaskType.EVENT, event.getTaskType());
    }

    @Test
    public void mark_setsIsDoneTrue() {
        LocalDateTime from = LocalDateTime.of(2026, 12, 20, 10, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 20, 12, 0);
        Event event = new Event("test", from, to);
        event.mark();
        assertTrue(event.isDone());
    }

    @Test
    public void unmark_setsIsDoneFalse() {
        LocalDateTime from = LocalDateTime.of(2026, 12, 20, 10, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 20, 12, 0);
        Event event = new Event("test", from, to);
        event.mark();
        event.unmark();
        assertFalse(event.isDone());
    }
}

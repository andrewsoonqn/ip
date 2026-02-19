package arnold.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import arnold.tasks.utils.TaskType;

public class DeadlineTest {

    @Test
    public void constructor_validDescriptionAndDate_success() {
        LocalDateTime by = LocalDateTime.of(2026, 12, 25, 14, 0);
        Deadline deadline = new Deadline("submit report", by);
        assertEquals("submit report", deadline.getDescription());
        assertFalse(deadline.isDone());
    }

    @Test
    public void toString_notDone_correctFormat() {
        LocalDateTime by = LocalDateTime.of(2026, 12, 25, 14, 0);
        Deadline deadline = new Deadline("submit report", by);
        assertEquals("[D][ ] submit report (by: 25/12/2026 1400)", deadline.toString());
    }

    @Test
    public void toString_done_correctFormat() {
        LocalDateTime by = LocalDateTime.of(2026, 12, 25, 14, 0);
        Deadline deadline = new Deadline("submit report", by);
        deadline.mark();
        assertEquals("[D][X] submit report (by: 25/12/2026 1400)", deadline.toString());
    }

    @Test
    public void toString_midnight_correctFormat() {
        LocalDateTime by = LocalDateTime.of(2027, 1, 1, 0, 0);
        Deadline deadline = new Deadline("new year task", by);
        assertEquals("[D][ ] new year task (by: 1/1/2027 0000)", deadline.toString());
    }

    @Test
    public void toString_singleDigitDayMonth_correctFormat() {
        LocalDateTime by = LocalDateTime.of(2026, 3, 5, 9, 30);
        Deadline deadline = new Deadline("task", by);
        assertEquals("[D][ ] task (by: 5/3/2026 0930)", deadline.toString());
    }

    @Test
    public void getTaskType_returnsDeadline() {
        LocalDateTime by = LocalDateTime.of(2026, 12, 25, 14, 0);
        Deadline deadline = new Deadline("test", by);
        assertEquals(TaskType.DEADLINE, deadline.getTaskType());
    }

    @Test
    public void mark_setsIsDoneTrue() {
        LocalDateTime by = LocalDateTime.of(2026, 12, 25, 14, 0);
        Deadline deadline = new Deadline("test", by);
        deadline.mark();
        assertTrue(deadline.isDone());
    }

    @Test
    public void unmark_setsIsDoneFalse() {
        LocalDateTime by = LocalDateTime.of(2026, 12, 25, 14, 0);
        Deadline deadline = new Deadline("test", by);
        deadline.mark();
        deadline.unmark();
        assertFalse(deadline.isDone());
    }
}

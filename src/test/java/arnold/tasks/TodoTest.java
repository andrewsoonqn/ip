package arnold.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import arnold.tasks.utils.TaskType;

public class TodoTest {

    @Test
    public void constructor_validDescription_success() {
        Todo todo = new Todo("read book");
        assertEquals("read book", todo.getDescription());
        assertFalse(todo.isDone());
    }

    @Test
    public void constructor_emptyDescription_success() {
        Todo todo = new Todo("");
        assertEquals("", todo.getDescription());
    }

    @Test
    public void toString_notDone_correctFormat() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void toString_done_correctFormat() {
        Todo todo = new Todo("read book");
        todo.mark();
        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void getTaskType_returnsTodo() {
        Todo todo = new Todo("test");
        assertEquals(TaskType.TODO, todo.getTaskType());
    }

    @Test
    public void mark_setsIsDoneTrue() {
        Todo todo = new Todo("test");
        assertFalse(todo.isDone());
        todo.mark();
        assertTrue(todo.isDone());
    }

    @Test
    public void unmark_setsIsDoneFalse() {
        Todo todo = new Todo("test");
        todo.mark();
        assertTrue(todo.isDone());
        todo.unmark();
        assertFalse(todo.isDone());
    }

    @Test
    public void mark_alreadyMarked_remainsMarked() {
        Todo todo = new Todo("test");
        todo.mark();
        todo.mark();
        assertTrue(todo.isDone());
    }

    @Test
    public void unmark_alreadyUnmarked_remainsUnmarked() {
        Todo todo = new Todo("test");
        todo.unmark();
        assertFalse(todo.isDone());
    }
}

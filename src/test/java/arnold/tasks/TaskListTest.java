package arnold.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arnold.chatbotexceptions.ChatbotArgumentException;
import arnold.datapersistence.NullStorage;
import arnold.tasks.utils.TaskList;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void addTask_todo_success() {
        Task todo = new Todo("read book");
        taskList.addTask(todo);
        assertEquals(1, taskList.getSize());
        assertEquals(todo, taskList.getTask(1));
    }

    @Test
    public void addTask_deadline_success() {
        LocalDateTime by = LocalDateTime.of(2023, 10, 15, 18, 0);
        Task deadline = new Deadline("submit report", by);
        taskList.addTask(deadline);
        assertEquals(1, taskList.getSize());
        assertEquals(deadline, taskList.getTask(1));
    }

    @Test
    public void addTask_event_success() {
        LocalDateTime from = LocalDateTime.of(2023, 10, 15, 14, 0);
        LocalDateTime to = LocalDateTime.of(2023, 10, 15, 16, 0);
        Task event = new Event("meeting", from, to);
        taskList.addTask(event);
        assertEquals(1, taskList.getSize());
        assertEquals(event, taskList.getTask(1));
    }

    @Test
    public void getTask_invalidIndex_throwsException() {
        assertThrows(ChatbotArgumentException.class, () -> taskList.getTask(1));

        taskList.addTask(new Todo("test"));
        assertThrows(ChatbotArgumentException.class, () -> taskList.getTask(2));
        assertThrows(ChatbotArgumentException.class, () -> taskList.getTask(0));
    }

    @Test
    public void markTask_validIndex_success() {
        taskList.addTask(new Todo("test"));
        Task task = taskList.markTask(1);
        assertTrue(task.isDone());
        assertTrue(taskList.getTask(1).isDone());
    }

    @Test
    public void unmarkTask_validIndex_success() {
        taskList.addTask(new Todo("test"));
        taskList.markTask(1);
        Task task = taskList.unmarkTask(1);
        assertFalse(task.isDone());
        assertFalse(taskList.getTask(1).isDone());
    }

    @Test
    public void removeTask_validIndex_success() {
        Task t1 = new Todo("task 1");
        Task t2 = new Todo("task 2");
        taskList.addTask(t1);
        taskList.addTask(t2);

        Task removed = taskList.removeTask(1);
        assertEquals(t1, removed);
        assertEquals(1, taskList.getSize());
        assertEquals(t2, taskList.getTask(1));
    }

    @Test
    public void removeTask_invalidIndex_throwsException() {
        assertThrows(ChatbotArgumentException.class, () -> taskList.removeTask(1));
    }

    @Test
    public void getSize_emptyList_returnsZero() {
        assertEquals(0, taskList.getSize());
    }

    @Test
    public void getSize_nonEmptyList_returnsCorrectSize() {
        taskList.addTask(new Todo("t1"));
        taskList.addTask(new Todo("t2"));
        assertEquals(2, taskList.getSize());
    }
}

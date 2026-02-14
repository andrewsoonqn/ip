package arnold.datapersistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import arnold.tasks.Deadline;
import arnold.tasks.Event;
import arnold.tasks.Task;
import arnold.tasks.Todo;
import arnold.tasks.utils.TaskList;

public class TaskFileStorageTest {

    @TempDir
    Path tempDir;

    private Path tempFile;
    private TaskFileStorage storage;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        tempFile = tempDir.resolve("tasks.json");
        storage = new TaskFileStorage(tempFile.toString());
        taskList = TaskList.create(new NullStorage());
    }

    @Test
    public void saveAndLoad_allTaskTypes_success() {
        Task todo = new Todo("read book");
        LocalDateTime by = LocalDateTime.of(2026, 2, 14, 18, 0);
        Task deadline = new Deadline("submit report", by);
        LocalDateTime from = LocalDateTime.of(2026, 2, 14, 14, 0);
        LocalDateTime to = LocalDateTime.of(2026, 2, 14, 16, 0);
        Task event = new Event("meeting", from, to);

        taskList.addTask(todo);
        taskList.addTask(deadline);
        taskList.addTask(event);

        storage.save(taskList);

        TaskList loadedList = TaskList.create(storage);
        assertEquals(3, loadedList.getSize());

        assertTrue(loadedList.getTask(1) instanceof Todo);
        assertEquals("read book", loadedList.getTask(1).getDescription());

        assertTrue(loadedList.getTask(2) instanceof Deadline);
        assertEquals("submit report", loadedList.getTask(2).getDescription());
        assertTrue(loadedList.getTask(2).toString().contains("14/2/2026 1800"));

        assertTrue(loadedList.getTask(3) instanceof Event);
        assertEquals("meeting", loadedList.getTask(3).getDescription());
        assertTrue(loadedList.getTask(3).toString().contains("14/2/2026 1400"));
        assertTrue(loadedList.getTask(3).toString().contains("14/2/2026 1600"));
    }

    @Test
    public void load_emptyJson_doesNothing() throws IOException {
        Files.writeString(tempFile, "");
        storage.load(taskList);
        assertEquals(0, taskList.getSize());

        Files.writeString(tempFile, "   ");
        storage.load(taskList);
        assertEquals(0, taskList.getSize());

        Files.writeString(tempFile, "null");
        storage.load(taskList);
        assertEquals(0, taskList.getSize());
    }

    @Test
    public void load_malformedJson_logsErrorAndDoesNotCrash() throws IOException {
        Files.writeString(tempFile, "{ malformed json }");
        // It should catch JsonProcessingException and print to stderr
        storage.load(taskList);
        assertEquals(0, taskList.getSize());
    }

    @Test
    public void saveAndLoad_preservesIsDoneStatus() {
        Task todo = new Todo("task 1");
        todo.mark();
        Task todo2 = new Todo("task 2");

        taskList.addTask(todo);
        taskList.addTask(todo2);

        storage.save(taskList);

        TaskList loadedList = TaskList.create(storage);
        assertTrue(loadedList.getTask(1).isDone());
        assertFalse(loadedList.getTask(2).isDone());
    }

    @Test
    public void saveAndLoad_dateTimeFields_correctHandling() {
        LocalDateTime by = LocalDateTime.of(2026, 2, 14, 10, 30);
        Deadline deadline = new Deadline("test deadline", by);
        taskList.addTask(deadline);

        storage.save(taskList);

        TaskList loadedList = TaskList.create(storage);
        Deadline loadedDeadline = (Deadline) loadedList.getTask(1);
        assertTrue(loadedDeadline.toString().contains("14/2/2026 1030"));
    }
}

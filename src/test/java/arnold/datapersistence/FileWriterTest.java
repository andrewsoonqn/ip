package arnold.datapersistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterTest {

    @TempDir
    Path tempDir;

    @Test
    public void writeToFilePath_validPath_writesContent() throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.createFile(file);
        FileWriter.writeToFilePath(file.toString(), "hello world");
        assertEquals("hello world", Files.readString(file));
    }

    @Test
    public void writeToFilePath_overwritesExistingContent() throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "old content");
        FileWriter.writeToFilePath(file.toString(), "new content");
        assertEquals("new content", Files.readString(file));
    }

    @Test
    public void writeToFilePath_emptyContent_writesEmpty() throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.createFile(file);
        FileWriter.writeToFilePath(file.toString(), "");
        assertEquals("", Files.readString(file));
    }

    @Test
    public void createFile_validPath_createsFile() {
        Path file = tempDir.resolve("newfile.txt");
        FileWriter.createFile(file.toString());
        assertTrue(Files.exists(file));
    }

    @Test
    public void createFile_alreadyExists_throwsStorageException() throws IOException {
        Path file = tempDir.resolve("existing.txt");
        Files.createFile(file);
        // createFile should never be called when the file already exists;
        // if it is, the user must be alerted via StorageException
        assertThrows(arnold.chatbotexceptions.StorageException.class,
            () -> FileWriter.createFile(file.toString()));
    }

    @Test
    public void createDirectories_validPath_createsParentDirs() {
        Path file = tempDir.resolve("a/b/c/file.txt");
        FileWriter.createDirectories(file);
        assertTrue(Files.exists(tempDir.resolve("a/b/c")));
    }

    @Test
    public void createDirectories_alreadyExists_doesNotThrow() throws IOException {
        Path dir = tempDir.resolve("existing");
        Files.createDirectories(dir);
        Path file = dir.resolve("file.txt");
        // Should not throw
        FileWriter.createDirectories(file);
        assertTrue(Files.exists(dir));
    }

    @Test
    public void createDirectories_noParent_doesNotThrow() {
        Path fileNoParent = Path.of("just-a-filename.txt");
        // Should not throw (parent is null case)
        FileWriter.createDirectories(fileNoParent);
    }
}

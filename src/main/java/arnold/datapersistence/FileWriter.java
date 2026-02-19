package arnold.datapersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import arnold.chatbotexceptions.StorageException;

/**
 * Utility class for writing to and creating files and directories.
 */
public class FileWriter {
    /**
     * Writes content to a file.
     *
     * @param filePath The path of the file to write to.
     * @param content The content to write.
     * @throws StorageException If the file cannot be written.
     */
    public static void writeToFilePath(String filePath, String content) {
        assert filePath != null : "File path must not be null";
        assert content != null : "Content must not be null";
        try {
            Files.writeString(Paths.get(filePath), content);
        } catch (IOException e) {
            throw new StorageException(
                "Failed to save tasks. Your changes may not be persisted.", e);
        }
    }

    /**
     * Creates a new file.
     *
     * @param filePath The path of the file to create.
     * @throws StorageException If the file cannot be created.
     */
    public static void createFile(String filePath) {
        assert !Files.exists(Paths.get(filePath)) : "createFile should not be called if file already exists";
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            throw new StorageException(
                "Failed to create data file. Your changes may not be persisted.", e);
        }
    }

    /**
     * Creates directories for a given path if they don't exist.
     *
     * @param path The path for which directories should be created.
     * @throws StorageException If the directories cannot be created.
     */
    public static void createDirectories(Path path) {
        // Parent directory will be null if not specified, e.g. "data.txt"
        // Prevent NullPointerException
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new StorageException(
                    "Failed to create data directory. Your changes may not be persisted.", e);
            }
        }
    }
}

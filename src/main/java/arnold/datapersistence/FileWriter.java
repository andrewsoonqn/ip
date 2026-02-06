package arnold.datapersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for writing to and creating files and directories.
 */
public class FileWriter {
    /**
     * Writes content to a file.
     *
     * @param filePath The path of the file to write to.
     * @param content The content to write.
     */
    public static void writeToFilePath(String filePath, String content) {
        try {
            Files.writeString(Paths.get(filePath), content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates a new file.
     *
     * @param filePath The path of the file to create.
     */
    public static void createFile(String filePath) {
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Creates directories for a given path if they don't exist.
     *
     * @param path The path for which directories should be created.
     */
    public static void createDirectories(Path path) {
        // Parent directory will be null if not specified, e.g. "data.txt"
        // Prevent NullPointerException
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                System.err.println("Error creating directories: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

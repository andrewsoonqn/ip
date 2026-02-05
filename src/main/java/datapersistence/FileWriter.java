package datapersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriter {
    public static void writeToFilePath(String filePath, String content) {
        try {
            Files.writeString(Paths.get(filePath), content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createFile(String filePath) {
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

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

package DataPersistence;

import InputHandling.InputProcessor;
import Messaging.NullMessenger;
import Tasks.TaskList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DataLoader {
    public static void loadData(TaskList taskList) {
        InputProcessor inputProcessor = new InputProcessor(new NullMessenger(), taskList);
        Path path = Paths.get(DataPaths.TASKS_FILE_PATH);

        try (Stream<String> commands = Files.lines(path)) {
            // Process each line as a command
            commands.forEach(inputProcessor::processInput);
        } catch (NoSuchFileException e) {
            // File doesn't exist yet, so create it'
            FileWriter.createDirectories(path);
            FileWriter.createFile(path.toString());
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

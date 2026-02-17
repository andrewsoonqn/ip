package arnold.datapersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import arnold.chatbotexceptions.StorageException;
import arnold.tasks.Task;
import arnold.tasks.utils.TaskList;

/**
 * Concrete implementation of Storage that uses a file for persistence.
 */
public class TaskFileStorage implements Storage {
    private final String filePath;
    private final ObjectMapper objectMapper;

    /**
     * Initializes a new instance of TaskFileStorage.
     *
     * @param filePath The path to the file used for persistence.
     */
    public TaskFileStorage(String filePath) {
        assert filePath != null && !filePath.isBlank() : "File path must be valid";
        this.filePath = filePath;
        this.objectMapper = createObjectMapper();
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.deactivateDefaultTyping();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Use ISO-8601 strings to store DateTime
        // e.g., "2026-02-14T10:30:00"
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    @Override
    public void load(TaskList taskList) {
        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            FileWriter.createDirectories(path);
            FileWriter.createFile(path.toString());
            return;
        }

        String json = readFileContent(path);
        if (json == null || json.isBlank()) {
            return;
        }

        loadTasksFromJson(taskList, json);
    }

    private String readFileContent(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void loadTasksFromJson(TaskList taskList, String json) {
        try {
            List<Task> tasks = objectMapper.readValue(json, new TypeReference<List<Task>>() {
            });
            if (tasks != null) {
                taskList.loadTasks(tasks);
            }
        } catch (IOException e) {
            System.err.println("Error parsing task data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void save(TaskList taskList) {
        Path path = Path.of(filePath);
        FileWriter.createDirectories(path);

        try {
            String json = objectMapper.writerFor(new TypeReference<List<Task>>() {
                })
                .writeValueAsString(taskList.getTasks());
            FileWriter.writeToFilePath(filePath, json);
        } catch (JsonProcessingException e) {
            // Should not reach here
            throw new StorageException(
                "Failed to save tasks. Your changes may not be persisted.", e);
        }
    }
        }
    }
}

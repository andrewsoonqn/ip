package arnold;

import java.util.Scanner;

import arnold.datapersistence.DataPaths;
import arnold.datapersistence.Storage;
import arnold.datapersistence.TaskFileStorage;
import arnold.messaging.DefaultMessenger;
import arnold.tasks.TaskList;

/**
 * Entry point for the Arnold chatbot application.
 */
public class Main {
    /**
     * Main method to start the chatbot.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Storage storage = new TaskFileStorage(DataPaths.TASKS_FILE_PATH);
        TaskList taskList = TaskList.create(storage);

        Arnold bot = new Arnold(new DefaultMessenger(), taskList);

        // Scanner is used to get user input later
        Scanner scanner = new Scanner(System.in);

        bot.hi();
        bot.run(scanner);
    }
}

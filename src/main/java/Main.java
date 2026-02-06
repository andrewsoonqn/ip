import arnold.Arnold;
import arnold.datapersistence.DataPaths;
import arnold.datapersistence.Storage;
import arnold.datapersistence.TaskFileStorage;
import arnold.messaging.DefaultMessenger;
import arnold.tasks.TaskList;

import java.util.Scanner;

public class Main {
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

package arnold;

import java.io.IOException;

import arnold.datapersistence.DataPaths;
import arnold.datapersistence.Storage;
import arnold.datapersistence.TaskFileStorage;
import arnold.tasks.utils.TaskList;
import arnold.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The main application class for the Arnold chatbot.
 */
public class ArnoldApplication extends Application {
    private static final double MIN_WINDOW_HEIGHT = 220;
    private static final double MIN_WINDOW_WIDTH = 417;
    private static final double MONOSPACE_FONT_SIZE = 14;
    private static final double SANS_SERIF_FONT_SIZE = 15;

    @Override
    public void start(Stage primaryStage) {
        Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), MONOSPACE_FONT_SIZE);
        Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-Regular.ttf"), SANS_SERIF_FONT_SIZE);

        try {
            primaryStage.setTitle("Arnold");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);

            primaryStage.setMinHeight(MIN_WINDOW_HEIGHT);
            primaryStage.setMinWidth(MIN_WINDOW_WIDTH);

            MainWindow mainWindow = fxmlLoader.getController();

            // Register MainWindow as the storage event listener before loading,
            // so any load errors are displayed to the user in the UI.
            Storage storage = new TaskFileStorage(DataPaths.TASKS_FILE_PATH);
            storage.setEventListener(mainWindow);
            TaskList taskList = TaskList.create(storage);

            Arnold arnold = new Arnold(taskList);
            mainWindow.setArnold(arnold); // Inject Arnold instance
            primaryStage.show();
        } catch (IOException e) {
            // Should never happen
            assert false : "Failed to load MainWindow.fxml";
            e.printStackTrace();
        }
    }
}

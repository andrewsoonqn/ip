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
    @Override
    public void start(Stage primaryStage) {
        Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-Regular.ttf"), 15);

        Storage storage = new TaskFileStorage(DataPaths.TASKS_FILE_PATH);
        TaskList taskList = TaskList.create(storage);

        Arnold arnold = new Arnold(taskList);

        try {
            primaryStage.setTitle("Arnold");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);

            primaryStage.setMinHeight(220);
            primaryStage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setArnold(arnold); // Inject the Arnold instance
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

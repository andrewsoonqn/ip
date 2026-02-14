package arnold;

import java.io.IOException;
import java.util.Scanner;

import arnold.datapersistence.DataPaths;
import arnold.datapersistence.Storage;
import arnold.datapersistence.TaskFileStorage;
import arnold.messaging.DefaultMessenger;
import arnold.tasks.TaskList;
import arnold.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ArnoldApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Storage storage = new TaskFileStorage(DataPaths.TASKS_FILE_PATH);
        TaskList taskList = TaskList.create(storage);

        Arnold arnold = new Arnold(new DefaultMessenger(), taskList);

        // Scanner is used to get user input later
        Scanner scanner = new Scanner(System.in);

        arnold.hi();
        arnold.run(scanner);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setArnold(arnold); // Inject the Arnold instance
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

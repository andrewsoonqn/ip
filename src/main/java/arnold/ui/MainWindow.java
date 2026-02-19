package arnold.ui;

import arnold.Arnold;
import arnold.datapersistence.StorageEventListener;
import arnold.inputhandling.CommandResult;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane implements StorageEventListener {
    private static final double EXIT_DELAY_SECONDS = 3;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Arnold arnold;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image arnoldImage = new Image(this.getClass().getResourceAsStream("/images/arnold.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Arnold instance
     */
    public void setArnold(Arnold arnold) {
        this.arnold = arnold;
        dialogContainer.getChildren().addAll(
            DialogBox.getArnoldDialog(arnold.hi(), arnoldImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Arnold's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        CommandResult result = arnold.getResponse(input);
        DialogBox responseBox = DialogBox.getArnoldDialog(result.getMessage(), arnoldImage);
        if (result.isError()) {
            responseBox.setErrorStyle();
        }
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            responseBox
        );
        userInput.clear();

        if (result.shouldExit()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
            PauseTransition delay = new PauseTransition(Duration.seconds(EXIT_DELAY_SECONDS));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    /**
     * Displays a storage load error as an Arnold message with error styling.
     *
     * @param message A user-facing message describing what went wrong.
     */
    @Override
    public void onLoadError(String message) {
        DialogBox errorBox = DialogBox.getArnoldDialog(message, arnoldImage);
        errorBox.setErrorStyle();
        dialogContainer.getChildren().add(errorBox);
    }
}

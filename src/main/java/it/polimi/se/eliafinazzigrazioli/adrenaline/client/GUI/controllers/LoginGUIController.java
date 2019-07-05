package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Login gui controller.
 */
public class LoginGUIController extends GUIController {
    @FXML
    private StackPane rootStackPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label errorLabel;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Button buttonLogin;

    @FXML
    private ChoiceBox<Avatar> availableAvatarsList;

    @FXML
    private AnchorPane overlayAnchorPane;

    private VBox loaderBox;

    /**
     * Instantiates a new Login gui controller.
     */
    public LoginGUIController() {
        super();
        ProgressIndicator progressIndicator = new ProgressIndicator();

        loaderBox = new VBox(progressIndicator);
        loaderBox.setAlignment(Pos.CENTER);
        loaderBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);");

        loaderBox.setVisible(false);

    }


    /**
     * Perform login.
     *
     * @param actionEvent the action event
     */
    public void performLogin(ActionEvent actionEvent) {

        view.showMessage("Try login: " + txtUsername.getText());

        // Grey Background
        errorLabel.setVisible(false);
        loaderBox.setVisible(true);
        view.notifyLoginRequestEvent(txtUsername.getText(), availableAvatarsList.getValue());
    }

    /**
     * Sets retry.
     */
    public void setRetry() {
        errorLabel.setVisible(true);
        txtUsername.setDisable(false);
        buttonLogin.setDisable(false);
        loaderBox.setVisible(false);
    }

    /**
     * Sets available avatars list.
     *
     * @param availableAvatars the available avatars
     */
    public void setAvailableAvatarsList(List<Avatar> availableAvatars) {
        availableAvatarsList.getItems().addAll(availableAvatars);
    }

    /**
     * Wait for match start.
     */
    public void waitForMatchStart() {
        overlayAnchorPane.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        Platform.runLater(() -> rootStackPane.getChildren().add(loaderBox));
    }

    /**
     * Show message.
     *
     * @param message the message
     */
    public void showMessage(Object message) {
        Platform.runLater(() -> {
            messageTextArea.appendText(String.format("%n%s", message.toString()));
            messageTextArea.setScrollTop(Double.MIN_VALUE);
        });
    }
}
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

public class LoginGUIController extends AbstractGUIController {
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

    public LoginGUIController() {
        super();
        ProgressIndicator progressIndicator = new ProgressIndicator();

        loaderBox = new VBox(progressIndicator);
        loaderBox.setAlignment(Pos.CENTER);
        loaderBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);");

        loaderBox.setVisible(false);

    }


    public void performLogin(ActionEvent actionEvent) {

        view.showMessage("Try login: " + txtUsername.getText());

        // Grey Background
        errorLabel.setVisible(false);
        loaderBox.setVisible(true);
        view.notifyLoginRequestEvent(txtUsername.getText(), availableAvatarsList.getValue());
    }

    public void setRetry() {
        errorLabel.setVisible(true);
        txtUsername.setDisable(false);
        buttonLogin.setDisable(false);
        loaderBox.setVisible(false);
    }

    public void setAvailableAvatarsList(List<Avatar> availableAvatars) {
        availableAvatarsList.getItems().addAll(availableAvatars);
    }

    public void waitForMatchStart() {
        overlayAnchorPane.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        Platform.runLater(() -> rootStackPane.getChildren().add(loaderBox));
    }

    public void showMessage(Object message) {
        Platform.runLater(() -> {
            messageTextArea.appendText(String.format("%n%s", message.toString()));
            messageTextArea.setScrollTop(Double.MIN_VALUE);
        });
    }
}
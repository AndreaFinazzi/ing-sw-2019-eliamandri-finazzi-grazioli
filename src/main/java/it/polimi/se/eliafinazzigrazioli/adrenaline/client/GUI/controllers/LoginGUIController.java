package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class LoginGUIController extends AbstractGUIController {
    @FXML
    private StackPane rootStackPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button buttonLogin;

    @FXML
    private ChoiceBox<Avatar> availableAvatarsList;

    @FXML
    private AnchorPane overlayAnchorPane;

    public LoginGUIController() {
        super();
    }


    public void performLogin(ActionEvent actionEvent) {

        System.out.println("Try login: " + txtUsername.getText());


        view.notifyLoginRequestEvent(txtUsername.getText(), availableAvatarsList.getValue());

        ProgressIndicator progressIndicator = new ProgressIndicator();
        VBox box = new VBox(progressIndicator);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);");
        // Grey Background
        rootStackPane.getChildren().add(box);
    }

    public void setLoggable(boolean loggable) {
        txtUsername.setDisable(!loggable);
        buttonLogin.setDisable(!loggable);
    }

    public void setAvailableAvatarsList(ArrayList<Avatar> availableAvatars) {
        availableAvatarsList.getItems().addAll(availableAvatars);
    }

    public void waitForMatchStart() {
        overlayAnchorPane.setVisible(true);
    }
}
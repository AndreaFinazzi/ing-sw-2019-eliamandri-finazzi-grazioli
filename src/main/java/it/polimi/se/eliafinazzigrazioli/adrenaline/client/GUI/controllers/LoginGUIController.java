package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginGUIController extends AbstractGUIController {
    @FXML
    private StackPane rootStackPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button buttonLogin;


    public void performLogin(ActionEvent actionEvent) {

        System.out.println("Try login: " + txtUsername.getText());
        txtUsername.setDisable(true);
        buttonLogin.setDisable(true);

        view.notifyLoginRequestEvent(txtUsername.getText(), Avatar.BANSHEE);

        ProgressIndicator progressIndicator = new ProgressIndicator();
        VBox box = new VBox(progressIndicator);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);");
        // Grey Background
        rootStackPane.getChildren().add(box);
    }

}
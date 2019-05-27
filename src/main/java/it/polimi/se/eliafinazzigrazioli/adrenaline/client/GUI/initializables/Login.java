package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.initializables;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private StackPane rootStackPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button buttonLogin;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void performLogin(ActionEvent actionEvent) {

        System.out.println("Try login: " + txtUsername.getText());
        txtUsername.setDisable(true);
        buttonLogin.setDisable(true);

        ProgressIndicator progressIndicator = new ProgressIndicator();
        VBox box = new VBox(progressIndicator);
        box.setAlignment(Pos.CENTER);
        // Grey Background
        rootStackPane.getChildren().add(box);
    }

}

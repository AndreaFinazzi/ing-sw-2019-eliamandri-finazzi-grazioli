package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OpponentPlayerGUIController extends AbstractGUIController {
    @FXML
    private AnchorPane opponentPlayerRootAnchorPane;
    @FXML
    private TextArea playerInfoTextArea;

    private PlayerBoardGUIController playerBoardGUIController;

    public OpponentPlayerGUIController() {

    }

    public void loadScene(Avatar avatar) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/GUI/fxml/player_board.fxml"));
        loader.load();

        playerBoardGUIController = loader.getController();
        playerBoardGUIController.loadScene(avatar);

        opponentPlayerRootAnchorPane.getChildren().add(loader.getRoot());
    }
}

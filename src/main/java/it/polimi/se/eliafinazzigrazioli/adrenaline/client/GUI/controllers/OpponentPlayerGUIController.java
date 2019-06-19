package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OpponentPlayerGUIController extends AbstractGUIController {
    @FXML
    private AnchorPane opponentPlayerRootAnchorPane;
    @FXML
    private TextArea playerInfoTextArea;

    private PlayerBoardGUIController playerBoardGUIController;

    public OpponentPlayerGUIController(GUI view) {
        super(view);
    }

    public void loadPlayerBoard(Avatar avatar) throws IOException {
        playerBoardGUIController = new PlayerBoardGUIController(view, avatar);
        loadFXML(GUI.FXML_PATH_PLAYER_BOARD, opponentPlayerRootAnchorPane, playerBoardGUIController);
    }
}

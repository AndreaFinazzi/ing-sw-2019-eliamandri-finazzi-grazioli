package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;

public class OpponentPlayerGUIController extends AbstractGUIController {
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private HBox overlayHBox;
    @FXML
    private TextArea playerInfoTextArea;


    private PlayerBoardGUIController playerBoardGUIController;

    public OpponentPlayerGUIController(GUI view) {
        super(view);
    }

    public void loadPlayerBoard(Avatar avatar) throws IOException {
        playerBoardGUIController = new PlayerBoardGUIController(view, avatar);
        loadFXML(GUI.FXML_PATH_PLAYER_BOARD, mainAnchorPane, playerBoardGUIController);
    }

    public void setCardCollected(PowerUpCardClient cardCollected) throws IOException {
        overlayHBox.setVisible(true);
        Node loadedCardSlot = loadFXML(GUI.FXML_PATH_MY_POWER_UP, overlayHBox, this);
        Platform.runLater(() -> loadedCardSlot.setStyle("-fx-background-image: url('" + view.getPowerUpAsset(cardCollected.getId()) + "'); "));
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            overlayHBox.getChildren().clear();
            overlayHBox.setVisible(false);
        });
        delay.play();
    }
}

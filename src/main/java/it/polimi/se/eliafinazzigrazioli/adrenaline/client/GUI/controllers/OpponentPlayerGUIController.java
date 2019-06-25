package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.List;

public class OpponentPlayerGUIController extends AbstractGUIController {
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TilePane ammoStack;
    @FXML
    private TextArea playerInfoTextArea;

    private String player;

    private PlayerBoardGUIController playerBoardGUIController;

    public OpponentPlayerGUIController(GUI view) {
        super(view);
    }

    public void loadPlayerBoard(String player) throws IOException {
        this.player = player;
        playerBoardGUIController = new PlayerBoardGUIController(view, player, true);
        loadFXML(GUI.FXML_PATH_PLAYER_BOARD, mainAnchorPane, playerBoardGUIController);
    }

    public void setCardCollected(PowerUpCardClient cardCollected) throws IOException {
        playerBoardGUIController.setCardCollected(cardCollected);
    }

    public void setCardCollected(WeaponCardClient cardCollected) throws IOException {
        playerBoardGUIController.setCardCollected(cardCollected);
    }

    public void setCardCollected(AmmoCardClient cardCollected) throws IOException {
        playerBoardGUIController.setCardCollected(cardCollected);
    }

    public void showAmmoCollected(Ammo ammo, boolean actuallyCollected) {
        if (actuallyCollected) playerBoardGUIController.addAmmoToStack(ammo);
    }

    public void updatePlayerAmmo() {
        playerBoardGUIController.updateAmmoStack();
    }

    public void showPaymentUpdate(List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos) {
        Platform.runLater(() -> playerBoardGUIController.getPaymentTransition(powerUpCardClients, ammos).play());
    }

    public void highlight(boolean setHighlight) {
        playerBoardGUIController.highlight(setHighlight);
    }

    public void showDamageReceived(List<DamageMark> damages, List<DamageMark> marks) throws IOException {
        playerBoardGUIController.updateDamages();
        playerBoardGUIController.updateMarks();
    }
}

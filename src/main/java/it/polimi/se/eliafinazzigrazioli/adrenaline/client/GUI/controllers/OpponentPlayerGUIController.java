package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class OpponentPlayerGUIController extends GUIController {
    @FXML
    private StackPane rootStackPane;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TilePane ammoStack;
    @FXML
    private Label playerInfoLabel;

    private String player;

    private PlayerBoardGUIController playerBoardGUIController;

    public OpponentPlayerGUIController(GUI view, String player) {
        super(view);
        this.player = player;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        playerBoardGUIController = new PlayerBoardGUIController(view, player, true);
        try {
            loadFXML(GUI.FXML_PATH_PLAYER_BOARD, mainAnchorPane, playerBoardGUIController);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        playerInfoLabel.setText(String.format("%s%n%n%s", player, playerBoardGUIController.getAvatar()));
    }

    public void loadPlayerBoard(String player) throws IOException {
        this.player = player;
        playerBoardGUIController = new PlayerBoardGUIController(view, player, true);
        loadFXML(GUI.FXML_PATH_PLAYER_BOARD, mainAnchorPane, playerBoardGUIController);

        playerInfoLabel.setText(String.format("%s%n%n%s", player, playerBoardGUIController.getAvatar()));
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
        playerBoardGUIController.updateAmmoStack();
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

    public void setDisconnected() {
        Platform.runLater(() -> {
            rootStackPane.setDisable(true);
            rootStackPane.getStyleClass().add(GUI.STYLE_CLASS_DISABLE);
        });
    }

    public void setReconnected() throws IOException {
        Platform.runLater(() -> {
            rootStackPane.setDisable(false);
            rootStackPane.getStyleClass().remove(GUI.STYLE_CLASS_DISABLE);
        });

        playerBoardGUIController.updateWeaponCards();
        playerBoardGUIController.updatePowerUpCards();
        playerBoardGUIController.updateDamages();
        playerBoardGUIController.updateMarks();
        playerBoardGUIController.updateAmmoStack();
    }

    public void updatePoints() {
        playerBoardGUIController.updatePoints();
    }

    public void showSuddenDeath() {
        playerBoardGUIController.setDeath(true);
    }

    public void showRespawn() {
        playerBoardGUIController.setDeath(false);
    }

    public void showSkullUpdate() {
        playerBoardGUIController.updateSkulls();
    }
}

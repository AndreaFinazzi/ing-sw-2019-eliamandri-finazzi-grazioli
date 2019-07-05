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

/**
 * The type Opponent player gui controller.
 */
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

    /**
     * Instantiates a new Opponent player gui controller.
     *
     * @param view the view
     * @param player the player
     */
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

    /**
     * Load player board.
     *
     * @param player the player
     * @throws IOException the io exception
     */
    public void loadPlayerBoard(String player) throws IOException {
        this.player = player;
        playerBoardGUIController = new PlayerBoardGUIController(view, player, true);
        loadFXML(GUI.FXML_PATH_PLAYER_BOARD, mainAnchorPane, playerBoardGUIController);

        playerInfoLabel.setText(String.format("%s%n%n%s", player, playerBoardGUIController.getAvatar()));
    }

    /**
     * Sets card collected.
     *
     * @param cardCollected the card collected
     * @throws IOException the io exception
     */
    public void setCardCollected(PowerUpCardClient cardCollected) throws IOException {
        playerBoardGUIController.setCardCollected(cardCollected);
    }

    /**
     * Sets card collected.
     *
     * @param cardCollected the card collected
     * @throws IOException the io exception
     */
    public void setCardCollected(WeaponCardClient cardCollected) throws IOException {
        playerBoardGUIController.setCardCollected(cardCollected);
    }

    /**
     * Sets card collected.
     *
     * @param cardCollected the card collected
     * @throws IOException the io exception
     */
    public void setCardCollected(AmmoCardClient cardCollected) throws IOException {
        playerBoardGUIController.setCardCollected(cardCollected);
    }

    /**
     * Show ammo collected.
     *
     * @param ammo the ammo
     * @param actuallyCollected the actually collected
     */
    public void showAmmoCollected(Ammo ammo, boolean actuallyCollected) {
        playerBoardGUIController.updateAmmoStack();
    }

    /**
     * Update player ammo.
     */
    public void updatePlayerAmmo() {
        playerBoardGUIController.updateAmmoStack();
    }

    /**
     * Show payment update.
     *
     * @param powerUpCardClients the power up card clients
     * @param ammos the ammos
     */
    public void showPaymentUpdate(List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos) {
        Platform.runLater(() -> playerBoardGUIController.getPaymentTransition(powerUpCardClients, ammos).play());
    }

    /**
     * Highlight.
     *
     * @param setHighlight the set highlight
     */
    public void highlight(boolean setHighlight) {
        playerBoardGUIController.highlight(setHighlight);
    }

    /**
     * Show damage received.
     *
     * @param damages the damages
     * @param marks the marks
     * @throws IOException the io exception
     */
    public void showDamageReceived(List<DamageMark> damages, List<DamageMark> marks) throws IOException {
        playerBoardGUIController.updateDamages();
        playerBoardGUIController.updateMarks();
    }

    /**
     * Sets disconnected.
     */
    public void setDisconnected() {
        Platform.runLater(() -> {
            rootStackPane.setDisable(true);
            rootStackPane.getStyleClass().add(GUI.STYLE_CLASS_DISABLE);
        });
    }

    /**
     * Sets reconnected.
     *
     * @throws IOException the io exception
     */
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

    /**
     * Update points.
     */
    public void updatePoints() {
        playerBoardGUIController.updatePoints();
    }

    /**
     * Show sudden death.
     */
    public void showSuddenDeath() {
        playerBoardGUIController.setDeath(true);
    }

    /**
     * Show respawn.
     */
    public void showRespawn() {
        playerBoardGUIController.setDeath(false);
    }

    /**
     * Show skull update.
     */
    public void showSkullUpdate() {
        playerBoardGUIController.updateSkulls();
    }

    /**
     * Sets final frenzy.
     *
     * @throws IOException the io exception
     */
    public void setFinalFrenzy() throws IOException {
        playerBoardGUIController.setFinalFrenzy();
    }
}

package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.Transitions.TransitionManager;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class PlayerBoardGUIController extends AbstractGUIController {
    private static final String PLAYER_BOARD_STYLE_CLASS_DEFAULT = "playerBoard_NONE";
    private static final String PLAYER_BOARD_STYLE_CLASS_PREFIX = "playerBoard_";

    @FXML
    private HBox overlayHBox;
    @FXML
    private GridPane playerBoardGridPane;

    @FXML
    private HBox damagesHBox;
    @FXML
    private HBox marksHBox;

    @FXML
    private TilePane ammoStack;

    @FXML
    private GridPane cardsGridPane;
    @FXML
    private HBox weaponCardSlots;
    @FXML
    private HBox powerUpCardSlots;

    private String player;
    private Avatar avatar;
    private boolean isOpponent;


    public PlayerBoardGUIController(GUI view, String player, boolean isOpponent) {
        super(view);
        this.player = player;
        this.avatar = view.getLocalModel().getPlayersAvatarMap().get(player);
        this.isOpponent = isOpponent;

    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void setCardCollected(PowerUpCardClient cardCollected) throws IOException {
        overlayHBox.setVisible(true);
        Node loadedCardSlot = loadFXML(GUI.FXML_PATH_POWER_UP, overlayHBox, this);
        loadedCardSlot.setDisable(true);
        view.applyBackground(loadedCardSlot, view.getPowerUpAsset(cardCollected.getId()));
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            overlayHBox.getChildren().remove(loadedCardSlot);
            overlayHBox.setVisible(false);
        });
        delay.play();

        updatePowerUpCards();
    }

    public void setCardCollected(WeaponCardClient cardCollected) throws IOException {
        overlayHBox.setVisible(true);
        Node loadedCardSlot = loadFXML(GUI.FXML_PATH_WEAPON, overlayHBox, this);
        view.applyBackground(loadedCardSlot, view.getWeaponAsset(cardCollected.getId()));
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            //TODO
            overlayHBox.getChildren().remove(loadedCardSlot);
            overlayHBox.setVisible(false);
        });
        delay.play();

        updateWeaponCards();
    }

    public void setCardCollected(AmmoCardClient cardCollected) throws IOException {
        overlayHBox.setVisible(true);
        //TODO should define a specific fxml?
        Node loadedCardSlot = loadFXML(GUI.FXML_PATH_POWER_UP, overlayHBox, this);
        loadedCardSlot.setDisable(true);
        view.applyBackground(loadedCardSlot, view.getAmmoAsset(cardCollected.getId()));

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            overlayHBox.getChildren().remove(loadedCardSlot);
            overlayHBox.setVisible(false);
        });
        delay.play();
    }

    public void addAmmoToStack(Ammo ammo) {
        Platform.runLater(() -> {
            Image ammoIcon = new Image(view.getAmmoAsset(ammo.name()), 20, 20, true, true);
            ImageView ammoSlot = new ImageView(ammoIcon);
            ammoSlot.getProperties().put(GUI.PROPERTIES_AMMO_KEY, ammo.name());

            ammoStack.getChildren().add(ammoSlot);
        });
    }

    public void updateAmmoStack() {
        List<Ammo> ammos;
        if (isOpponent)
            ammos = view.getLocalModel().getOpponentInfo(player).getAmmos();
        else
            ammos = view.getLocalModel().getAmmos();

        Platform.runLater(() -> {
            ammoStack.getChildren().clear();
            for (Ammo ammo : ammos) {
                Image ammoIcon = new Image(view.getAmmoAsset(ammo.name()), 20, 20, true, true);
                ImageView ammoSlot = new ImageView(ammoIcon);
                ammoSlot.getProperties().put(GUI.PROPERTIES_AMMO_KEY, ammo.name());

                ammoStack.getChildren().add(ammoSlot);
            }
        });
    }

    public void updateWeaponCards() throws IOException {
        if (!player.equals(view.getClient().getPlayerName())) {
            Platform.runLater(() -> weaponCardSlots.getChildren().clear());

            for (WeaponCardClient weaponCard : view.getLocalModel().getOpponentInfo(player).getWeapons()) {
                Button weaponSlot = (Button) loadFXML(GUI.FXML_PATH_WEAPON, weaponCardSlots, this);
                weaponSlot.getStyleClass().add(GUI.STYLE_CLASS_WEAPON_MY);

                Platform.runLater(() -> {
                    if (!weaponCard.isLoaded()) {
                        weaponSlot.getProperties().put(GUI.PROPERTIES_CARD_ID_KEY, weaponCard.getId());
                        view.applyBackground(weaponSlot, view.getWeaponAsset(weaponCard.getId()));
                    } else {
                        view.applyBackground(weaponSlot, view.getWeaponAsset(GUI.ASSET_ID_HIDDEN_CARD));
                    }
                    weaponSlot.setDisable(true);
                });
            }
        }
    }

    public void updatePowerUpCards() throws IOException {
        if (!player.equals(view.getClient().getPlayerName())) {
            Platform.runLater(() -> powerUpCardSlots.getChildren().clear());

            for (int i = 0; i < view.getLocalModel().getOpponentInfo(player).getPowerUps(); i++) {
                Button weaponSlot = (Button) loadFXML(GUI.FXML_PATH_POWER_UP, powerUpCardSlots, this);

                Platform.runLater(() -> {
                    view.applyBackground(weaponSlot, view.getPowerUpAsset(GUI.ASSET_ID_HIDDEN_CARD));
                    weaponSlot.setDisable(true);
                });
            }
        }
    }

    public ParallelTransition getPaymentTransition(List<PowerUpCardClient> powerUpCards, List<Ammo> ammos) {
        List<Node> payedAmmoNodes = new ArrayList<>();
        for (Ammo ammo : ammos) {
            Node ammoNode = getAmmoNodeInStack(ammo);
            payedAmmoNodes.add(ammoNode);
        }

        ParallelTransition paymentTransition = TransitionManager.generateParallelTransition(TransitionManager.ammoPaymentAnimator, payedAmmoNodes);

        if (isOpponent) {
            List<Node> payedPowerUpNodes = new ArrayList<>();
            for (PowerUpCardClient powerUpCard : powerUpCards) {
                Node elementNode = GUI.getChildrenByProperty(powerUpCardSlots.getChildren(), GUI.PROPERTIES_CARD_ID_KEY, powerUpCard.getId());
                view.applyBackground(elementNode, view.getPowerUpAsset(powerUpCard.getId()));
                payedPowerUpNodes.add(elementNode);
            }
            ParallelTransition powerUpTransition = TransitionManager.generateParallelTransition(TransitionManager.powerUpPaymentAnimator, payedPowerUpNodes);

            // Back to player board at the end.
            powerUpTransition.setOnFinished(event -> {
                cardsGridPane.setVisible(false);
                playerBoardGridPane.setVisible(true);
            });

            // Switch to opponent's cards panel
            Platform.runLater(() -> {
                playerBoardGridPane.setVisible(false);
                cardsGridPane.setVisible(true);
            });

            paymentTransition.getChildren().add(powerUpTransition);
        }

        paymentTransition.setOnFinished(event -> {
            try {
                updateAmmoStack();
                updatePowerUpCards();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        });

        return paymentTransition;
    }

    private Node getAmmoNodeInStack(Ammo ammo) {
        for (Node ammoNode : ammoStack.getChildren()) {
            if (ammoNode.hasProperties() && ammoNode.getProperties().get(GUI.PROPERTIES_AMMO_KEY).equals(ammo.name()))
                return ammoNode;
        }

        return null;
    }

    private void toggleView() {
        playerBoardGridPane.setVisible(cardsGridPane.visibleProperty().get());
        cardsGridPane.setVisible(!playerBoardGridPane.visibleProperty().get());
    }

    public void highlight(boolean setHighlight) {
        if (setHighlight) playerBoardGridPane.getStyleClass().add(GUI.STYLE_CLASS_HIGHLIGHT);
        else playerBoardGridPane.getStyleClass().remove(GUI.STYLE_CLASS_HIGHLIGHT);
    }

    public void updateDamages() throws IOException {
        List<DamageMark> damages;
        if (isOpponent) {
            damages = view.getLocalModel().getOpponentInfo(player).getDamages();
        } else {
            damages = view.getLocalModel().getDamages();
        }

        Platform.runLater(() -> damagesHBox.getChildren().clear());
        for (DamageMark damageMark : damages) {
            ImageView damageNode = (ImageView) loadFXML(GUI.FXML_PATH_MARK, damagesHBox, this);
            Platform.runLater(() -> damageNode.setImage(new Image(view.getMarkAsset(damageMark))));
        }
    }

    public void updateMarks() throws IOException {
        Platform.runLater(() -> marksHBox.getChildren().clear());
        List<DamageMark> marks;
        if (isOpponent) {
            marks = view.getLocalModel().getOpponentInfo(player).getMarks();
        } else {
            marks = view.getLocalModel().getMarks();
        }

        for (DamageMark damageMark : marks) {
            ImageView damageNode = (ImageView) loadFXML(GUI.FXML_PATH_MARK, marksHBox, this);
            Platform.runLater(() -> damageNode.setImage(new Image(view.getMarkAsset(damageMark))));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!initialized) {
            initialized = true;
            super.initialize(location, resources);

            playerBoardGridPane.getStyleClass().remove(PLAYER_BOARD_STYLE_CLASS_DEFAULT);
            playerBoardGridPane.getStyleClass().add(PLAYER_BOARD_STYLE_CLASS_PREFIX + avatar.getDamageMark().name());

            if (isOpponent) {
                cardsGridPane.setOnMouseClicked((event) -> toggleView());
                playerBoardGridPane.setOnMouseClicked((event) -> toggleView());
            }
        }
    }
}

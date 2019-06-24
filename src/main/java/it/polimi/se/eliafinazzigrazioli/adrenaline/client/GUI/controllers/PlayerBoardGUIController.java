package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.animation.*;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class PlayerBoardGUIController extends AbstractGUIController {
    private static final String PLAYER_BOARD_STYLE_CLASS_DEFAULT = "playerBoard_NONE";
    private static final String PLAYER_BOARD_STYLE_CLASS_PREFIX = "playerBoard_";

    private static final double TRANSITION_PAYMENT_DELTA_Y = -70;
    private static final double TRANSITION_PAYMENT_DURATION = 2;
    private static final double TRANSITION_PAYMENT_SCALING_FACTOR = 1.1;
    private static final double TRANSITION_PAYMENT_FADE_INITIAL = 1;
    private static final double TRANSITION_PAYMENT_FADE_FINAL = 0;

    @FXML
    private HBox overlayHBox;
    @FXML
    private GridPane playerBoardGridPane;

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
                        weaponSlot.setStyle("-fx-background-image: url('" + view.getWeaponAsset(weaponCard.getId()) + "'); ");
                    } else {
                        weaponSlot.setStyle("-fx-background-image: url('" + view.getWeaponAsset(GUI.ASSET_ID_HIDDEN_CARD) + "'); ");
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
                    weaponSlot.setStyle("-fx-background-image: url('" + view.getPowerUpAsset(GUI.ASSET_ID_HIDDEN_CARD) + "'); ");
                    weaponSlot.setDisable(true);
                });
            }
        }
    }

    public void showPayment(List<PowerUpCardClient> powerUpCards, List<Ammo> ammos) {
        ParallelTransition paymentTransition = new ParallelTransition();

        if (isOpponent) {
            for (PowerUpCardClient powerUpCard : powerUpCards) {
                Node elementNode = GUI.getChildrenById(powerUpCardSlots.getChildren(), powerUpCard.getId());
                view.applyBackground(elementNode, view.getPowerUpAsset(powerUpCard.getId()));
                paymentTransition.getChildren().add(generatePaymentTransition(elementNode));
            }
        }

        for (Ammo ammo : ammos) {
            Node ammoNode = getAmmoNodeInStack(ammo);
            paymentTransition.getChildren().add(generatePaymentTransition(ammoNode));
        }

        paymentTransition.setOnFinished(event -> {
            if (isOpponent) {
                cardsGridPane.setVisible(false);
                playerBoardGridPane.setVisible(true);
            }
            try {
                updateAmmoStack();
                updatePowerUpCards();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        });

        Platform.runLater(() -> {
            if (isOpponent) {
                playerBoardGridPane.setVisible(false);
                cardsGridPane.setVisible(true);
            }
            paymentTransition.play();
        });

    }

    private Transition generatePaymentTransition(Node payedElement) {
        Duration duration = Duration.seconds(TRANSITION_PAYMENT_DURATION);

        TranslateTransition translateTransition = new TranslateTransition(duration, payedElement);
        ScaleTransition scaleTransition = new ScaleTransition(duration, payedElement);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(TRANSITION_PAYMENT_DURATION + 1), payedElement);

        translateTransition.setByY(TRANSITION_PAYMENT_DELTA_Y);

        scaleTransition.setByX(TRANSITION_PAYMENT_SCALING_FACTOR);
        scaleTransition.setByY(TRANSITION_PAYMENT_SCALING_FACTOR);

        fadeTransition.setFromValue(TRANSITION_PAYMENT_FADE_INITIAL);
        fadeTransition.setToValue(TRANSITION_PAYMENT_FADE_FINAL);

        return new ParallelTransition(translateTransition, scaleTransition, fadeTransition);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        playerBoardGridPane.getStyleClass().remove(PLAYER_BOARD_STYLE_CLASS_DEFAULT);
        playerBoardGridPane.getStyleClass().add(PLAYER_BOARD_STYLE_CLASS_PREFIX + avatar.getDamageMark().name());

        if (isOpponent) {
            cardsGridPane.setOnMouseClicked((event) -> toggleView());
            playerBoardGridPane.setOnMouseClicked((event) -> toggleView());
        }
    }
}

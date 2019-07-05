package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.transitions.TransitionManager;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponEffectClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

/**
 * The type Player board gui controller.
 */
public class PlayerBoardGUIController extends GUIController {

    @FXML
    private HBox deathOverlayHBox;
    @FXML
    private HBox overlayHBox;
    @FXML
    private GridPane playerBoardGridPane;
    @FXML
    private GridPane detailsGridPane;
    @FXML
    private HBox detailsImageHBox;
    @FXML
    private VBox detailsTextVBox;

    @FXML
    private HBox damagesHBox;
    @FXML
    private HBox marksHBox;
    @FXML
    private HBox skullsTrack;

    @FXML
    private Label pointsLabel;
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

    private AtomicReference<WeaponEffectClient> selectedWeaponEffect;

    /**
     * Instantiates a new Player board gui controller.
     *
     * @param view the view
     * @param player the player
     * @param isOpponent the is opponent
     */
    public PlayerBoardGUIController(GUI view, String player, boolean isOpponent) {
        super(view);
        this.player = player;
        this.avatar = view.getLocalModel().getPlayersAvatarMap().get(player);
        this.isOpponent = isOpponent;

    }

    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Sets avatar.
     *
     * @param avatar the avatar
     */
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    /**
     * Sets selected weapon effect.
     *
     * @param selectedWeaponEffect the selected weapon effect
     */
    public void setSelectedWeaponEffect(AtomicReference<WeaponEffectClient> selectedWeaponEffect) {
        this.selectedWeaponEffect = selectedWeaponEffect;
    }

    /**
     * Sets card collected.
     *
     * @param cardCollected the card collected
     * @throws IOException the io exception
     */
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

    /**
     * Sets card collected.
     *
     * @param cardCollected the card collected
     * @throws IOException the io exception
     */
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


    /**
     * Sets card collected.
     *
     * @param cardCollected the card collected
     * @throws IOException the io exception
     */
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

    /**
     * Add ammo to stack.
     *
     * @param ammo the ammo
     */
    public void addAmmoToStack(Ammo ammo) {
        Platform.runLater(() -> {
            Image ammoIcon = new Image(view.getAmmoAsset(ammo.name()), 20, 20, true, true);
            ImageView ammoSlot = new ImageView(ammoIcon);
            ammoSlot.getProperties().put(GUI.PROPERTIES_KEY_AMMO, ammo.name());

            ammoStack.getChildren().add(ammoSlot);
        });
    }

    /**
     * Update ammo stack.
     */
    public synchronized void updateAmmoStack() {
        List<Ammo> ammos;
        if (isOpponent)
            ammos = view.getLocalModel().getOpponentInfo(player).getAmmos();
        else
            ammos = view.getLocalModel().getAmmos();

        Platform.runLater(() -> {
            synchronized (ammoStack.getChildren()) {
                ammoStack.getChildren().clear();
                for (Ammo ammo : ammos) {
                    Image ammoIcon = new Image(view.getAmmoAsset(ammo.name()), 20, 20, true, true);
                    ImageView ammoSlot = new ImageView(ammoIcon);
                    ammoSlot.getProperties().put(GUI.PROPERTIES_KEY_AMMO, ammo.name());

                    ammoStack.getChildren().add(ammoSlot);
                }
            }
        });
    }

    /**
     * Update weapon cards.
     *
     * @throws IOException the io exception
     */
    public void updateWeaponCards() throws IOException {
        if (!player.equals(view.getClient().getPlayerName())) {
            Platform.runLater(() -> weaponCardSlots.getChildren().clear());

            for (WeaponCardClient weaponCard : view.getLocalModel().getOpponentInfo(player).getWeapons()) {
                Button weaponSlot = (Button) loadFXML(GUI.FXML_PATH_WEAPON, weaponCardSlots, this);
                weaponSlot.getStyleClass().add(GUI.STYLE_CLASS_WEAPON_MY);

                Platform.runLater(() -> {
                    if (!weaponCard.isLoaded()) {
                        weaponSlot.getProperties().put(GUI.PROPERTIES_KEY_CARD_ID, weaponCard.getId());
                        view.applyBackground(weaponSlot, view.getWeaponAsset(weaponCard.getId()));
                    } else {
                        view.applyBackground(weaponSlot, view.getWeaponAsset(GUI.ASSET_ID_HIDDEN_CARD));
                    }
                    weaponSlot.setDisable(true);
                });
            }
        }
    }

    /**
     * Update power up cards.
     *
     * @throws IOException the io exception
     */
    public void updatePowerUpCards() throws IOException {
        if (!player.equals(view.getClient().getPlayerName())) {
            Platform.runLater(() -> powerUpCardSlots.getChildren().clear());

            for (int i = 0; i < view.getLocalModel().getOpponentInfo(player).getPowerUps(); i++) {
                Button powerUpSlot = (Button) loadFXML(GUI.FXML_PATH_POWER_UP, powerUpCardSlots, this);

                Platform.runLater(() -> {
                    view.applyBackground(powerUpSlot, view.getPowerUpAsset(GUI.ASSET_ID_HIDDEN_CARD));
                    powerUpSlot.setDisable(true);
                });
            }
        }
    }

    /**
     * Gets payment transition.
     *
     * @param powerUpCards the power up cards
     * @param ammos the ammos
     * @return the payment transition
     */
    public ParallelTransition getPaymentTransition(List<PowerUpCardClient> powerUpCards, List<Ammo> ammos) {
        List<Node> payedAmmoNodes = new ArrayList<>();
        for (Ammo ammo : ammos) {
            Node ammoNode = getAmmoNodeInStack(ammo);
            if (ammoNode != null) payedAmmoNodes.add(ammoNode);
        }

        ParallelTransition paymentTransition = TransitionManager.generateParallelTransition(TransitionManager.ammoPaymentAnimator, payedAmmoNodes);

        if (isOpponent) {
            List<Node> payedPowerUpNodes = new ArrayList<>();
            if (!powerUpCardSlots.getChildren().isEmpty()) {
                for (PowerUpCardClient powerUpCard : powerUpCards) {
                    Node elementNode = powerUpCardSlots.getChildren().get(0);
                    view.applyBackground(elementNode, view.getPowerUpAsset(powerUpCard.getId()));
                    payedPowerUpNodes.add(elementNode);
                }
            }
            ParallelTransition powerUpTransition = TransitionManager.generateParallelTransition(TransitionManager.powerUpPaymentAnimator, payedPowerUpNodes);

            // Back to player board at the end.
            powerUpTransition.setOnFinished(event -> {
                cardsGridPane.setVisible(false);
            });

            // Switch to opponent's cards panel
            Platform.runLater(() -> {
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
        Node ammoNode = GUI.getChildrenByProperty(ammoStack.getChildren(), GUI.PROPERTIES_KEY_AMMO, ammo.name());
        if (ammoNode != null) ammoNode.getProperties().remove(GUI.PROPERTIES_KEY_AMMO);
        return ammoNode;
    }

    private void toggleCardsView() {
//        playerBoardGridPane.setVisible(cardsGridPane.visibleProperty().get());
        cardsGridPane.setVisible(!cardsGridPane.visibleProperty().get());
    }

    private void toggleDetailsView() {
//        playerBoardGridPane.setVisible(detailsGridPane.visibleProperty().get());
        detailsGridPane.setVisible(!detailsGridPane.visibleProperty().get());
    }

    /**
     * Highlight.
     *
     * @param setHighlight the set highlight
     */
    public void highlight(boolean setHighlight) {
        if (setHighlight) playerBoardGridPane.getStyleClass().add(GUI.STYLE_CLASS_HIGHLIGHT);
        else playerBoardGridPane.getStyleClass().remove(GUI.STYLE_CLASS_HIGHLIGHT);
    }

    /**
     * Update damages.
     *
     * @throws IOException the io exception
     */
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

    /**
     * Update marks.
     *
     * @throws IOException the io exception
     */
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

    /**
     * Show details.
     *
     * @param weaponCard the weapon card
     * @throws IOException the io exception
     */
    public void showDetails(WeaponCardClient weaponCard) throws IOException {
        if (!isOpponent && weaponCard != null) {
            Platform.runLater(() -> {
                detailsImageHBox.getChildren().clear();
                detailsTextVBox.getChildren().clear();
            });
            detailsGridPane.setVisible(true);
            Node imageNode = loadFXML(GUI.FXML_PATH_WEAPON, detailsImageHBox, null);
            view.applyBackground(imageNode, view.getWeaponAsset(weaponCard.getId()));

            Platform.runLater(() -> {
                for (WeaponEffectClient effect : weaponCard.getEffects()) {
                    Label effectLabel = new Label(effect.getEffectName().toUpperCase() + String.format("%n") + effect.getEffectDescription());
                    effectLabel.getProperties().put(GUI.PROPERTIES_KEY_EFFECT, effect.getEffectName());
                    effectLabel.getStyleClass().add(GUI.STYLE_CLASS_EFFECT_DEFAULT);
                    detailsTextVBox.getChildren().add(effectLabel);
                }
            });
        }
    }

    /**
     * Show details.
     *
     * @param powerUpCard the power up card
     * @throws IOException the io exception
     */
    public void showDetails(PowerUpCardClient powerUpCard) throws IOException {
        if (!isOpponent && powerUpCard != null) {
            Platform.runLater(() -> {
                detailsImageHBox.getChildren().clear();
                detailsTextVBox.getChildren().clear();
                detailsGridPane.setVisible(true);
            });
            Node imageNode = loadFXML(GUI.FXML_PATH_POWER_UP, detailsImageHBox, null);
            view.applyBackground(imageNode, view.getPowerUpAsset(powerUpCard.getId()));

            Platform.runLater(() -> {
                Label descriptionLabel = new Label(powerUpCard.getPowerUpType().toUpperCase() + String.format("%n") + powerUpCard.getDescription());
                descriptionLabel.getStyleClass().add(GUI.STYLE_CLASS_EFFECT_DEFAULT);
                detailsTextVBox.getChildren().add(descriptionLabel);
            });
        }
    }

    /**
     * Hide details.
     */
    public void hideDetails() {
        if (!isOpponent) {
            detailsGridPane.setVisible(false);
        }
    }

    /**
     * Sets selectable effects.
     *
     * @param weaponCard the weapon card
     * @param callableEffects the callable effects
     * @throws IOException the io exception
     */
    public void setSelectableEffects(WeaponCardClient weaponCard, List<WeaponEffectClient> callableEffects) throws IOException {
        showDetails(weaponCard);
        Platform.runLater(() -> {
            for (WeaponEffectClient effect : callableEffects) {
                Label effectLabel = (Label) GUI.getChildrenByProperty(detailsTextVBox.getChildren(), GUI.PROPERTIES_KEY_EFFECT, effect.getEffectName());
                if (effectLabel != null) {

                    effectLabel.setDisable(false);
                    effectLabel.getStyleClass().add(GUI.STYLE_CLASS_EFFECT_SELECTABLE);
                    effectLabel.setOnMouseClicked(event -> {
                        selectedWeaponEffect.set(effect);
                        disableEffects();
                        view.disableAll();
                        semaphore.release();
                    });
                }
            }
        });
    }

    /**
     * Disable effects.
     */
    protected void disableEffects() {
        Platform.runLater(() -> {
            detailsTextVBox.getChildren().forEach(label -> {
                label.getStyleClass().remove(GUI.STYLE_CLASS_EFFECT_SELECTABLE);
                label.setDisable(true);
            });
        });
    }

    /**
     * Sets death.
     *
     * @param dead the dead
     */
    public void setDeath(boolean dead) {
        if (dead) {
            deathOverlayHBox.setVisible(true);
            TransitionManager.generateSimpleTransition(TransitionManager.deathTransition, deathOverlayHBox).play();
        } else {
            deathOverlayHBox.setVisible(false);
        }
    }

    /**
     * Update skulls.
     */
    public void updateSkulls() {
        int skulls;
        if (isOpponent)
            skulls = view.getLocalModel().getOpponentInfo(player).getSkulls();
        else
            skulls = view.getLocalModel().getSkulls();

        Platform.runLater(() -> {
            skullsTrack.getChildren().clear();
            for (int i = 0; i < skulls; i++) {
                Image skullImage = new Image(view.getAsset(GUI.ASSET_ID_SKULL), 24, 0, true, true);
                ImageView skullNode = new ImageView(skullImage);
                skullNode.getStyleClass().add(GUI.STYLE_CLASS_PLAYER_BOARD_SKULL);
                Platform.runLater(() -> skullsTrack.getChildren().add(skullNode));
            }
        });
    }

    /**
     * Update points.
     */
    public void updatePoints() {
        int points = view.getLocalModel().getPoints().get(player);
        Platform.runLater(() -> pointsLabel.setText("Points: " + points));
    }


    /**
     * Sets final frenzy.
     *
     * @throws IOException the io exception
     */
    public void setFinalFrenzy() throws IOException {
        view.applyBackground(playerBoardGridPane, view.getPlayerBoardFFAsset(avatar));
        updateDamages();
        updateSkulls();
        updateMarks();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!initialized) {
            initialized = true;
            super.initialize(location, resources);

            view.applyBackground(playerBoardGridPane, view.getPlayerBoardAsset(avatar));

            if (isOpponent) {
                cardsGridPane.setOnMouseClicked((event) -> toggleCardsView());
                playerBoardGridPane.setOnMouseClicked((event) -> toggleCardsView());
            } else {
                detailsGridPane.setOnMouseClicked((event) -> toggleDetailsView());
                playerBoardGridPane.setOnMouseClicked((event) -> toggleDetailsView());
            }

            try {
                updateAmmoStack();
                updateMarks();
                updateDamages();
                updatePowerUpCards();
                updateWeaponCards();
                updateSkulls();
                updatePoints();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

        }
    }
}

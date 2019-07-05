package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.transitions.TransitionManager;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

/**
 * The type Commands gui controller.
 */
public class CommandsGUIController extends GUIController {

    private List<PowerUpCardClient> myPowerUps;

    private AtomicReference<PowerUpCardClient> selectedPowerUp;

    private AtomicReference<Ammo> selectedAmmo;

    private AtomicReference<WeaponCardClient> selectedWeapon;

    private AtomicReference<MoveDirection> selectedMove;
    private AtomicReference<PlayerAction> chosenAction;

    private PlayerBoardGUIController playerBoardGUIController;

    @FXML
    private GridPane rootGridPane;

    @FXML
    private FlowPane actionsFlowPane;

    @FXML
    private GridPane arrowsGridPane;
    @FXML
    private AnchorPane myPlayerBoardAnchorPane;
    @FXML
    private HBox spawnPowerUpSlots;

    @FXML
    private GridPane myCardsGridPane;
    @FXML
    private HBox myPowerUpCardSlots;
    @FXML
    private HBox myWeaponCardSlots;
    @FXML
    private Button proceedButton;
    @FXML
    private TextArea messagesTextArea;

    @FXML
    private Button arrowUP;

    @FXML
    private Button arrowRIGHT;
    @FXML
    private Button arrowDOWN;
    @FXML
    private Button arrowLEFT;
    @FXML
    private Button arrowSTOP;
    private Map<MoveDirection, Button> moveDirectionButtonEnumMap = new EnumMap<>(MoveDirection.class);

    /**
     * Instantiates a new Commands gui controller.
     *
     * @param view the view
     */
    public CommandsGUIController(GUI view) {
        super(view);
    }

    /**
     * Sets spawn power up cards.
     *
     * @param powerUpCards the power up cards
     * @throws IOException the io exception
     */
    public void setSpawnPowerUpCards(List<PowerUpCardClient> powerUpCards) throws IOException {
        for (PowerUpCardClient powerUpCard : powerUpCards) {
            Button button = (Button) loadFXML(GUI.FXML_PATH_POWER_UP, spawnPowerUpSlots, null);
            Platform.runLater(() -> {
                button.setDisable(false);
                button.getProperties().put(GUI.PROPERTIES_KEY_CARD_ID, powerUpCard.getId());
                view.applyBackground(button, view.getPowerUpAsset(powerUpCard.getId()));
                button.setOnAction(event -> {
                    selectedPowerUp.set(view.getPowerUpById(powerUpCards, (String) button.getProperties().get(GUI.PROPERTIES_KEY_CARD_ID)));
                    spawnPowerUpSlots.getChildren().clear();
                    spawnPowerUpSlots.setVisible(false);
                    myCardsGridPane.setVisible(true);
                    semaphore.release();
                });
            });

        }

        myCardsGridPane.setVisible(false);
        spawnPowerUpSlots.setVisible(true);
    }

    /**
     * Sets selectable ammo.
     *
     * @param selectableAmmo the selectable ammo
     * @throws IOException the io exception
     */
    public void setSelectableAmmo(List<Ammo> selectableAmmo) throws IOException {
        for (Ammo ammo : selectableAmmo) {
            Button button = (Button) loadFXML(GUI.FXML_PATH_POWER_UP, spawnPowerUpSlots, null);
            Platform.runLater(() -> {
                button.setDisable(false);
                button.getProperties().put(GUI.PROPERTIES_KEY_AMMO, ammo);
                view.applyBackground(button, view.getAmmoAsset(ammo.name()));
                button.setOnAction(event -> {
                    selectedAmmo.set((Ammo) button.getProperties().get(GUI.PROPERTIES_KEY_AMMO));
                    spawnPowerUpSlots.getChildren().clear();
                    spawnPowerUpSlots.setVisible(false);
                    myCardsGridPane.setVisible(true);
                    semaphore.release();
                });
            });

        }

        myCardsGridPane.setVisible(false);
        spawnPowerUpSlots.setVisible(true);
    }


    /**
     * Gets player board gui controller.
     *
     * @return the player board gui controller
     */
    public PlayerBoardGUIController getPlayerBoardGUIController() {
        return playerBoardGUIController;
    }

    /**
     * Update power up cards.
     */
    public void updatePowerUpCards() {
        resetPowerUpSlots();

        for (PowerUpCardClient powerUpCard : view.getLocalModel().getPowerUpCards()) {
            Node powerUpSlot = myPowerUpCardSlots.getChildren().get(powerUpCard.getSlotPosition());
            Platform.runLater(() -> {
                powerUpSlot.getProperties().put(GUI.PROPERTIES_KEY_CARD_ID, powerUpCard.getId());
            });
            view.applyBackground(powerUpSlot, view.getPowerUpAsset(powerUpCard.getId()));
        }
    }

    /**
     * Update weapon cards.
     */
    public void updateWeaponCards() {
        resetWeaponSlots();

        for (WeaponCardClient weaponCard : view.getLocalModel().getWeaponCards()) {
            Node weaponSlot = myWeaponCardSlots.getChildren().get(weaponCard.getSlotPosition());
            Platform.runLater(() -> {
                weaponSlot.getProperties().put(GUI.PROPERTIES_KEY_CARD_ID, weaponCard.getId());
                disableCards();
            });
            view.applyBackground(weaponSlot, view.getWeaponAsset(weaponCard.getId()));
        }
    }

    private void resetPowerUpSlots() {
        for (Node slot : myPowerUpCardSlots.getChildren()) {
            view.applyBackground(slot, view.getPowerUpAsset(GUI.ASSET_ID_HIDDEN_CARD));
            if (slot.hasProperties())
                Platform.runLater(() -> slot.getProperties().remove(GUI.PROPERTIES_KEY_CARD_ID));
        }
    }

    private void resetWeaponSlots() {
        for (Node slot : myWeaponCardSlots.getChildren()) {
            view.applyBackground(slot, view.getWeaponAsset(GUI.ASSET_ID_HIDDEN_CARD));
            if (slot.hasProperties())
                Platform.runLater(() -> slot.getProperties().remove(GUI.PROPERTIES_KEY_CARD_ID));
        }
    }

    /**
     * Sets selected weapon.
     *
     * @param selectedWeapon the selected weapon
     */
    public void setSelectedWeapon(AtomicReference<WeaponCardClient> selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    /**
     * Sets selected power up.
     *
     * @param selectedPowerUp the selected power up
     */
    public void setSelectedPowerUp(AtomicReference<PowerUpCardClient> selectedPowerUp) {
        this.selectedPowerUp = selectedPowerUp;
    }

    /**
     * Sets selected move.
     *
     * @param selectedMove the selected move
     */
    public void setSelectedMove(AtomicReference<MoveDirection> selectedMove) {
        this.selectedMove = selectedMove;
    }

    /**
     * Sets selected ammo.
     *
     * @param selectedAmmo the selected ammo
     */
    public void setSelectedAmmo(AtomicReference<Ammo> selectedAmmo) {
        this.selectedAmmo = selectedAmmo;
    }

    /**
     * Sets chosen action.
     *
     * @param chosenAction the chosen action
     */
    public void setChosenAction(AtomicReference<PlayerAction> chosenAction) {
        this.chosenAction = chosenAction;
    }

    /**
     * Sets selectable power up.
     *
     * @param cards the cards
     */
    public void setSelectablePowerUp(List<PowerUpCardClient> cards) {
        for (Node cardNode : myPowerUpCardSlots.getChildren()) {
            if (cardNode.hasProperties() && cards.contains(view.getLocalModel().getPowerUpCardById((String) cardNode.getProperties().get(GUI.PROPERTIES_KEY_CARD_ID)))) {
                Platform.runLater(() -> {
                    cardNode.setDisable(false);
                    cardNode.getStyleClass().add(GUI.STYLE_CLASS_HIGHLIGHT);
                });
            }
        }

        proceedButton.setDisable(false);
    }

    /**
     * Sets selectable weapon.
     *
     * @param selectableWeapons the selectable weapons
     */
    public void setSelectableWeapon(List<WeaponCardClient> selectableWeapons) {
        for (Node cardNode : myWeaponCardSlots.getChildren()) {
            if (cardNode.hasProperties() && selectableWeapons.contains(view.getLocalModel().getWeaponByIdInHand((String) cardNode.getProperties().get(GUI.PROPERTIES_KEY_CARD_ID)))) {
                Platform.runLater(() -> {
                    cardNode.setDisable(false);
                    cardNode.getStyleClass().add(GUI.STYLE_CLASS_HIGHLIGHT);
                });
            }
        }

        proceedButton.setDisable(false);
    }

    /**
     * Sets available moves.
     *
     * @param availableMoves the available moves
     */
    public void setAvailableMoves(List<MoveDirection> availableMoves) {
        arrowsGridPane.setDisable(false);
        proceedButton.setDisable(false);
        for (Map.Entry<MoveDirection, Button> moveDirectionButtonEntry : moveDirectionButtonEnumMap.entrySet()) {
            if (availableMoves.contains(moveDirectionButtonEntry.getKey()))
                moveDirectionButtonEntry.getValue().setDisable(false);
            else
                moveDirectionButtonEntry.getValue().setDisable(true);
        }

        arrowSTOP.setDisable(false);
    }

    /**
     * Disable arrows.
     */
    public void disableArrows() {
        moveDirectionButtonEnumMap.values().forEach(arrow -> arrow.setDisable(true));
    }

    /**
     * Disable cards.
     */
    public void disableCards() {
        myPowerUpCardSlots.getChildren().forEach(card -> {
            card.setDisable(true);
            card.getStyleClass().remove(GUI.STYLE_CLASS_HIGHLIGHT);
        });
        myWeaponCardSlots.getChildren().forEach(card -> {
            card.setDisable(true);
            card.getStyleClass().remove(GUI.STYLE_CLASS_HIGHLIGHT);
        });

        playerBoardGUIController.disableEffects();
        proceedButton.setDisable(true);
    }

    /**
     * Generate card node node.
     *
     * @param powerUpCard the power up card
     * @return the node
     */
    public Node generateCardNode(PowerUpCardClient powerUpCard) {
        Button button = null;
        try {
            button = (Button) loadFXML(GUI.FXML_PATH_POWER_UP, (GUIController) null);
            button.setDisable(true);
            Button finalButton = button;
            button.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    try {
                        playerBoardGUIController.showDetails(view.getLocalModel().getPowerUpCardById((String) finalButton.getProperties().get(GUI.PROPERTIES_KEY_CARD_ID)));
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }
                } else {
                    disableCards();
                    proceedButton.setDisable(true);
                    selectedPowerUp.set(view.getLocalModel().getPowerUpCardById((String) finalButton.getProperties().get(GUI.PROPERTIES_KEY_CARD_ID)));
                    finalButton.getProperties().put(GUI.PROPERTIES_KEY_CARD_SPENT, true);
                    semaphore.release();
                }
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return button;
    }

    /**
     * Generate card node node.
     *
     * @param weaponCardClient the weapon card client
     * @return the node
     */
    public Node generateCardNode(WeaponCardClient weaponCardClient) {
        Button button = null;
        try {
            button = (Button) loadFXML(GUI.FXML_PATH_WEAPON, this);
            button.getStyleClass().add(GUI.STYLE_CLASS_WEAPON_MY);
            button.setDisable(true);
            Button finalButton = button;
            button.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    try {
                        playerBoardGUIController.showDetails(view.getLocalModel().getWeaponByIdInHand((String) finalButton.getProperties().get(GUI.PROPERTIES_KEY_CARD_ID)));
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }
                } else {
                    disableCards();
                    proceedButton.setDisable(true);
                    selectedWeapon.set(view.getLocalModel().getWeaponByIdInHand((String) finalButton.getProperties().get(GUI.PROPERTIES_KEY_CARD_ID)));
                    semaphore.release();
                }
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return button;
    }

    /**
     * My power up selected.
     *
     * @param actionEvent the action event
     */
    public void myPowerUpSelected(ActionEvent actionEvent) {
        selectedPowerUp.set(myPowerUps.get(((Button) actionEvent.getSource()).getParent().getChildrenUnmodifiable().indexOf(actionEvent.getSource())));
        semaphore.release();
    }

    /**
     * Action selected.
     *
     * @param actionEvent the action event
     */
    public void actionSelected(ActionEvent actionEvent) {
        selectedPowerUp.set(myPowerUps.get(((Button) actionEvent.getSource()).getParent().getChildrenUnmodifiable().indexOf(actionEvent.getSource())));
        semaphore.release();
    }


    /**
     * Sets locked commands.
     */
    public void setLockedCommands() {
        actionsFlowPane.setVisible(true);
        actionsFlowPane.setDisable(true);
        arrowsGridPane.setVisible(false);
    }

    /**
     * Sets chose action.
     */
    public void setChoseAction() {
        actionsFlowPane.setVisible(true);
        actionsFlowPane.setDisable(false);
        arrowsGridPane.setVisible(false);
    }

    /**
     * Sets get move.
     */
    public void setGetMove() {
        arrowsGridPane.setVisible(true);
        actionsFlowPane.setVisible(false);
    }

    /**
     * Load my player board.
     *
     * @throws IOException the io exception
     */
    public void loadMyPlayerBoard() throws IOException {
        playerBoardGUIController = new PlayerBoardGUIController(view, view.getClient().getPlayerName(), false);
        loadFXML(GUI.FXML_PATH_PLAYER_BOARD, myPlayerBoardAnchorPane, playerBoardGUIController);

    }

    /**
     * Show message.
     *
     * @param message the message
     */
    public void showMessage(Object message) {
        Platform.runLater(() -> {

            messagesTextArea.appendText(String.format("%n%s", message.toString()));
            messagesTextArea.setScrollTop(Double.MIN_VALUE);
        });
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
     * Update my ammo.
     */
    public void updateMyAmmo() {
        playerBoardGUIController.updateAmmoStack();
    }

    /**
     * Show payment update.
     *
     * @param powerUpCards the power up cards
     * @param ammos the ammos
     */
    public void showPaymentUpdate(List<PowerUpCardClient> powerUpCards, List<Ammo> ammos) {
        // get ammos transition from playerBoard
        ParallelTransition paymentTransition = playerBoardGUIController.getPaymentTransition(powerUpCards, ammos);

        List<Node> payedPowerUpNodes = GUI.getChildrensByProperty(myPowerUpCardSlots.getChildren(), GUI.PROPERTIES_KEY_CARD_SPENT, true);
        for (Node payedPowerUpNode : payedPowerUpNodes) {
            payedPowerUpNode.getProperties().remove(GUI.PROPERTIES_KEY_CARD_SPENT);
        }

        // get powerups transition from TransitionManager and combine it with the previous one
        paymentTransition.getChildren().add(TransitionManager.generateParallelTransition(TransitionManager.powerUpPaymentAnimator, payedPowerUpNodes));

        paymentTransition.setOnFinished(event -> updatePowerUpCards());

        Platform.runLater(paymentTransition::play);
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
     * Show details.
     *
     * @param weaponCard the weapon card
     * @throws IOException the io exception
     */
    public void showDetails(WeaponCardClient weaponCard) throws IOException {
        playerBoardGUIController.showDetails(weaponCard);
    }

    /**
     * Sets selectable effects.
     *
     * @param semaphore the semaphore
     * @param selectedEffect the selected effect
     * @param weapon the weapon
     * @param callableEffects the callable effects
     * @throws IOException the io exception
     */
    public void setSelectableEffects(Semaphore semaphore, AtomicReference<WeaponEffectClient> selectedEffect, WeaponCardClient weapon, List<WeaponEffectClient> callableEffects) throws IOException {
        proceedButton.setDisable(false);

        playerBoardGUIController.setSemaphore(semaphore);
        playerBoardGUIController.setSelectedWeaponEffect(selectedEffect);

        playerBoardGUIController.setSelectableEffects(weapon, callableEffects);
    }

    /**
     * Sets disconnected.
     */
    public void setDisconnected() {
        rootGridPane.setDisable(true);
        rootGridPane.getStyleClass().add(GUI.STYLE_CLASS_DISABLE);
    }


    /**
     * Sets reconnected.
     *
     * @throws IOException the io exception
     */
    public void setReconnected() throws IOException {
        rootGridPane.setDisable(false);
        rootGridPane.getStyleClass().remove(GUI.STYLE_CLASS_DISABLE);

        playerBoardGUIController.updateWeaponCards();
        playerBoardGUIController.updatePowerUpCards();
        playerBoardGUIController.updateDamages();
        playerBoardGUIController.updateMarks();
        playerBoardGUIController.updateAmmoStack();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!initialized) {
            super.initialize(location, resources);
            initialized = true;

            // Vars initialization
            moveDirectionButtonEnumMap.put(MoveDirection.UP, arrowUP);
            moveDirectionButtonEnumMap.put(MoveDirection.RIGHT, arrowRIGHT);
            moveDirectionButtonEnumMap.put(MoveDirection.DOWN, arrowDOWN);
            moveDirectionButtonEnumMap.put(MoveDirection.LEFT, arrowLEFT);
            moveDirectionButtonEnumMap.put(MoveDirection.STOP, arrowSTOP);

            // Initialize player actions
            for (PlayerAction playerAction : PlayerAction.values()) {
                if (playerAction.isGuiEnabled()) {
                    Button actionButton = new Button(playerAction.toString());
                    actionButton.setOnAction(event -> {
                        chosenAction.set(playerAction);
                        semaphore.release();
                    });
                    actionsFlowPane.getChildren().add(actionButton);
                }
            }
            actionsFlowPane.setDisable(true);

            // Initialize my cards
            // Weapons
            for (int i = 0; i < Rules.PLAYER_CARDS_MAX_WEAPONS; i++) {
                myWeaponCardSlots.getChildren().add(generateCardNode((WeaponCardClient) null));
            }

            // PowerUps
            for (int i = 0; i < Rules.PLAYER_CARDS_MAX_POWER_UPS; i++) {
                myPowerUpCardSlots.getChildren().add(generateCardNode(((PowerUpCardClient) null)));
            }

            proceedButton.setOnAction(event -> {
                proceedButton.setDisable(true);
                if (selectedPowerUp != null) selectedPowerUp.set(null);
                if (selectedWeapon != null) selectedWeapon.set(null);
                disableCards();
                view.releaseCurrentSemaphore();
            });

            // Arrow panel initialization
            String initialText;
            Image arrowIcon;
            String normalizedName;
            Button currentButton;
            for (MoveDirection moveDirection : MoveDirection.values()) {
                normalizedName = moveDirection.name().toLowerCase();
                currentButton = moveDirectionButtonEnumMap.get(moveDirection);
                initialText = currentButton.getText();
                try {
                    arrowIcon = new Image(view.getIconAsset(GUI.ASSET_PREFIX_ICONS_ARROWS + normalizedName + GUI.ASSET_FORMAT), 50, 50, true, true);
                    currentButton.setGraphic(new ImageView(arrowIcon));
                    currentButton.setText(null);
                } catch (Exception e) {
                    LOGGER.log(Level.INFO, e.getMessage(), e);
                    currentButton.setGraphic(null);
                    currentButton.setText(initialText);
                } finally {
                    currentButton.setOnMouseClicked(event -> {
                        proceedButton.setDisable(true);
                        selectedMove.set(moveDirection);
                        disableArrows();
                        semaphore.release();
                    });
                }
            }
            arrowsGridPane.setVisible(false);

            playerBoardGUIController = new PlayerBoardGUIController(view, view.getClient().getPlayerName(), false);
            try {
                loadFXML(GUI.FXML_PATH_PLAYER_BOARD, myPlayerBoardAnchorPane, playerBoardGUIController);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

            updatePowerUpCards();
            updateWeaponCards();
        }
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
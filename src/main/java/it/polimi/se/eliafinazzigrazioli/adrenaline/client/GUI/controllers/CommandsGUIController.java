package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.MoveDirection;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PlayerAction;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public class CommandsGUIController extends AbstractGUIController {

    private List<PowerUpCardClient> myPowerUps;

    private AtomicReference<PowerUpCardClient> selectedPowerUp;

    private AtomicReference<WeaponCardClient> selectedWeapon;

    private AtomicReference<MoveDirection> selectedMove;
    private AtomicReference<PlayerAction> chosenAction;
    private PlayerBoardGUIController playerBoardGUIController;

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
    private Button proceedCardsButton;

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

    public CommandsGUIController(GUI view) {
        super(view);
    }

    public void setSpawnPowerUpCards(List<PowerUpCardClient> powerUpCards) {
        for (PowerUpCardClient powerUpCard : powerUpCards) {
            try {
                Button button = (Button) loadFXML(GUI.FXML_PATH_POWER_UP, spawnPowerUpSlots, this);
                button.setDisable(false);
                button.getProperties().put(GUI.PROPERTIES_CARD_ID_KEY, powerUpCard.getId());
                view.applyBackground(button, view.getPowerUpAsset(powerUpCard.getId()));
                button.setOnAction(event -> {
                    selectedPowerUp.set(view.getPowerUpById(powerUpCards, (String) button.getProperties().get(GUI.PROPERTIES_CARD_ID_KEY)));
                    spawnPowerUpSlots.getChildren().clear();
                    spawnPowerUpSlots.setVisible(false);
                    myCardsGridPane.setVisible(true);
                    semaphore.release();
                });
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        myCardsGridPane.setVisible(false);
        spawnPowerUpSlots.setVisible(true);
    }

    public void updatePowerUpCards() {
        resetPowerUpSlots();
        for (PowerUpCardClient powerUpCard : view.getLocalModel().getPowerUpCards()) {
            view.applyBackground(myPowerUpCardSlots.getChildren().get(powerUpCard.getSlotPosition()), view.getPowerUpAsset(powerUpCard.getId()));
        }
    }

    public void updateWeaponCards() {
        resetWeaponSlots();
        for (WeaponCardClient weaponCard : view.getLocalModel().getWeaponCards()) {
            Platform.runLater(() -> {
                Node weaponSlot = myWeaponCardSlots.getChildren().get(weaponCard.getSlotPosition());
                weaponSlot.getProperties().put(GUI.PROPERTIES_CARD_ID_KEY, weaponCard.getId());
                view.applyBackground(weaponSlot, view.getWeaponAsset(weaponCard.getId()));
            });
        }
    }

    private void resetPowerUpSlots() {
        for (Node slot : myPowerUpCardSlots.getChildren()) {
            view.applyBackground(slot, view.getPowerUpAsset(GUI.ASSET_ID_HIDDEN_CARD));
        }
    }

    private void resetWeaponSlots() {
        for (Node slot : myWeaponCardSlots.getChildren()) {
            view.applyBackground(slot, view.getWeaponAsset(GUI.ASSET_ID_HIDDEN_CARD));
        }
    }

    public void setSelectedWeapon(AtomicReference<WeaponCardClient> selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public void setSelectedPowerUp(AtomicReference<PowerUpCardClient> selectedPowerUp) {
        this.selectedPowerUp = selectedPowerUp;
    }

    public void setSelectedMove(AtomicReference<MoveDirection> selectedMove) {
        this.selectedMove = selectedMove;
    }

    public void setChosenAction(AtomicReference<PlayerAction> chosenAction) {
        this.chosenAction = chosenAction;
    }

    public void setSelectablePowerUp(List<PowerUpCardClient> cards) {
        for (PowerUpCardClient card : cards) {
            if (card.getSlotPosition() != -1) {
                myPowerUpCardSlots.getChildren().get(card.getSlotPosition()).setDisable(false);
            } else {
                myPowerUpCardSlots.getChildren().get(myPowerUps.indexOf(card)).setDisable(false);
            }
        }

        proceedCardsButton.setDisable(false);
    }

    public void setSelectableWeapon(List<WeaponCardClient> selectableWeapons) {
        for (WeaponCardClient card : selectableWeapons) {
            myWeaponCardSlots.getChildren().get(card.getSlotPosition()).setDisable(false);
        }
    }

    public void setAvailableMoves(ArrayList<MoveDirection> availableMoves) {
        arrowsGridPane.setDisable(false);
        for (Map.Entry<MoveDirection, Button> moveDirectionButtonEntry : moveDirectionButtonEnumMap.entrySet()) {
            if (availableMoves.contains(moveDirectionButtonEntry.getKey()))
                moveDirectionButtonEntry.getValue().setDisable(false);
            else
                moveDirectionButtonEntry.getValue().setDisable(true);
        }

        arrowSTOP.setDisable(false);
    }

    public void disableArrows() {
        for (Button arrow : moveDirectionButtonEnumMap.values()) {
            arrow.setDisable(true);
        }
    }

    public void disableCards() {
        myPowerUpCardSlots.getChildren().forEach(card -> card.setDisable(true));
        myWeaponCardSlots.getChildren().forEach(card -> card.setDisable(true));
    }
// USELESS
//    public void arrowPressed(MouseEvent actionEvent) {
//        selectedMove.set(MoveDirection.STOP);

//        disableArrows();

//        semaphore.release();

//    }

    public void myPowerUpSelected(ActionEvent actionEvent) {
        selectedPowerUp.set(myPowerUps.get(((Button) actionEvent.getSource()).getParent().getChildrenUnmodifiable().indexOf(actionEvent.getSource())));
        semaphore.release();
    }

    public void actionSelected(ActionEvent actionEvent) {
        selectedPowerUp.set(myPowerUps.get(((Button) actionEvent.getSource()).getParent().getChildrenUnmodifiable().indexOf(actionEvent.getSource())));
        semaphore.release();
    }


    public void setLockedCommands() {
        actionsFlowPane.setVisible(true);
        actionsFlowPane.setDisable(true);
        arrowsGridPane.setVisible(false);
    }

    public void setChoseAction() {
        actionsFlowPane.setVisible(true);
        actionsFlowPane.setDisable(false);
        arrowsGridPane.setVisible(false);
    }

    public void setGetMove() {
        arrowsGridPane.setVisible(true);
        actionsFlowPane.setVisible(false);
    }

    public void loadMyPlayerBoard() throws IOException {
        playerBoardGUIController = new PlayerBoardGUIController(view, view.getClient().getPlayerName(), false);
        loadFXML(GUI.FXML_PATH_PLAYER_BOARD, myPlayerBoardAnchorPane, playerBoardGUIController);

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
                Button actionButton = new Button(playerAction.toString());
                actionButton.setOnAction(event -> {
                    chosenAction.set(playerAction);
                    semaphore.release();
                });
                actionsFlowPane.getChildren().add(actionButton);
            }
            actionsFlowPane.setDisable(true);

            // Initialize my cards
            // Weapons
            for (int i = 0; i < Rules.PLAYER_CARDS_MAX_WEAPONS; i++) {
                try {
                    Button button = (Button) loadFXML(GUI.FXML_PATH_WEAPON, myWeaponCardSlots, this);
                    button.getStyleClass().add(GUI.STYLE_CLASS_WEAPON_MY);
                    button.setDisable(true);
                    button.setOnAction(event -> {
                        disableCards();
                        selectedWeapon.set(view.getLocalModel().getWeaponCardByIdOnMap((String) button.getProperties().get(GUI.PROPERTIES_CARD_ID_KEY)));
                        semaphore.release();
                    });
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }

            // PowerUps
            for (int i = 0; i < Rules.PLAYER_CARDS_MAX_POWER_UPS; i++) {
                try {
                    Button button = (Button) loadFXML(GUI.FXML_PATH_POWER_UP, myPowerUpCardSlots, this);
                    button.setDisable(true);
                    button.setOnAction(event -> {
                        disableCards();
                        proceedCardsButton.setDisable(true);
                        selectedPowerUp.set(view.getLocalModel().getPowerUpCardById((String) button.getProperties().get(GUI.PROPERTIES_CARD_ID_KEY)));
                        semaphore.release();
                    });
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }

            proceedCardsButton.setOnAction(event -> {
                proceedCardsButton.setDisable(true);
                selectedPowerUp.set(null);
                semaphore.release();
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
                    arrowIcon = new Image(view.getIconAsset(GUI.ASSET_PREFIX_ICONS_ARROWS + normalizedName + GUI.ASSET_FORMAT_ICONS), 50, 50, true, true);
                    currentButton.setGraphic(new ImageView(arrowIcon));
                    currentButton.setText(null);
                } catch (Exception e) {
                    LOGGER.log(Level.INFO, e.getMessage(), e);
                    currentButton.setGraphic(null);
                    currentButton.setText(initialText);
                } finally {
                    currentButton.setOnMouseClicked(event -> {
                        selectedMove.set(moveDirection);
                        disableArrows();
                        semaphore.release();
                    });
                }
            }
            arrowsGridPane.setVisible(false);


        }
    }

    public void showMessage(Object message) {
        Platform.runLater(() -> {

            messagesTextArea.appendText(String.format("%n%s", message.toString()));
            messagesTextArea.setScrollTop(Double.MIN_VALUE);
        });
    }


    public void showAmmoCollected(Ammo ammo, boolean actuallyCollected) {
        if (actuallyCollected) playerBoardGUIController.addAmmoToStack(ammo);
    }

    public void updateMyAmmo() {
        playerBoardGUIController.updateAmmoStack();
    }

    public void showPaymentUpdate(List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos) {
        playerBoardGUIController.showPayment(powerUpCardClients, ammos);

    }
}
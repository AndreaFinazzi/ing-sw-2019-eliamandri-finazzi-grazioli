package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.MoveDirection;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PlayerAction;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

public class CommandGUIController extends AbstractGUIController {
    private boolean initialized = false;

    private List<PowerUpCardClient> myPowerUps;
    private List<WeaponCardClient> myWeaponCards;

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
    private HBox myPowerUpSlots;
    @FXML
    private HBox myWeaponCardSlots;
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
    private Map<MoveDirection, Button> moveDirectionButtonHashMap = new EnumMap<>(MoveDirection.class);

    public CommandGUIController(GUI view) {
        super(view);
    }

    public void setPowerUpCards(List<PowerUpCardClient> powerUpCards) {
        myPowerUps = powerUpCards;
        for (int i = 0; i < powerUpCards.size(); i++) {
            int position = i;
            Platform.runLater(() -> myPowerUpSlots.getChildren().get(position).setStyle("-fx-background-image: url('" + view.getPowerUpAsset(powerUpCards.get(position).getId()) + "'); "));
        }
    }

    // Weapon card id is missing
//    public void setWeaponCards(List<WeaponCardClient> weaponCards) {
//        myWeaponCards = weaponCards;
//        for (int i = 0; i < weaponCards.size(); i++) {
//            int position = i;
//            Platform.runLater(() -> myWeaponCardSlots.getChildren().get(position).setStyle("-fx-background-image: url('" + view.getWeaponAsset(weaponCards.get(position).getId()) + "'); "));
//        }
//    }

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

    public void setAvailableMoves(ArrayList<MoveDirection> availableMoves) {
        for (MoveDirection availableMoveDirection : availableMoves) {
            moveDirectionButtonHashMap.get(availableMoveDirection).setDisable(false);
        }

        arrowSTOP.setDisable(false);
    }

    public void disableArrows() {
        for (Button arrow : moveDirectionButtonHashMap.values()) {
            arrow.setDisable(true);
        }
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

    public void loadMyPlayerBoard(Avatar avatar) throws IOException {
        playerBoardGUIController = new PlayerBoardGUIController(view, avatar);
        loadFXML(GUI.FXML_PATH_PLAYER_BOARD, myPlayerBoardAnchorPane, playerBoardGUIController);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!initialized) {
            super.initialize(location, resources);
            initialized = true;

            // Vars initialization
            moveDirectionButtonHashMap.put(MoveDirection.UP, arrowUP);
            moveDirectionButtonHashMap.put(MoveDirection.RIGHT, arrowRIGHT);
            moveDirectionButtonHashMap.put(MoveDirection.DOWN, arrowDOWN);
            moveDirectionButtonHashMap.put(MoveDirection.LEFT, arrowLEFT);
            moveDirectionButtonHashMap.put(MoveDirection.STOP, arrowSTOP);

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
                    Button button = (Button) loadFXML(GUI.FXML_PATH_MY_WEAPON, myWeaponCardSlots, this);
                    button.setOnAction(event -> {
                        selectedWeapon.set(myWeaponCards.get((button.getParent().getChildrenUnmodifiable().indexOf(button))));
                        semaphore.release();
                    });
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }

            // PowerUps
            for (int i = 0; i < Rules.PLAYER_CARDS_MAX_POWER_UPS; i++) {
                try {
                    Button button = (Button) loadFXML(GUI.FXML_PATH_MY_POWER_UP, myPowerUpSlots, this);
                    button.setOnAction(event -> {
                        selectedPowerUp.set(myPowerUps.get((button.getParent().getChildrenUnmodifiable().indexOf(button))));
                        semaphore.release();
                    });
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }

            // Arrow panel initialization
            String initialText;
            Image arrowIcon;
            String normalizedName;
            Button currentButton;
            for (MoveDirection moveDirection : MoveDirection.values()) {
                normalizedName = moveDirection.name().toLowerCase();
                currentButton = moveDirectionButtonHashMap.get(moveDirection);
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
        }
    }
}

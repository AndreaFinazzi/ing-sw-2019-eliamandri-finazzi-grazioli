package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.MoveDirection;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PlayerAction;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class CommandGUIController extends AbstractGUIController {
    private List<PowerUpCardClient> myPowerUps;
    private List<WeaponCardClient> myWeaponCards;

    private AtomicReference<PowerUpCardClient> selectedPowerUp;
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
    private Button arrowUP;
    @FXML
    private Button arrowRIGHT;
    @FXML
    private Button arrowDOWN;
    @FXML
    private Button arrowLEFT;
    @FXML
    private Button arrowSTOP;

    public CommandGUIController() {
        super();
    }

    public void setPowerUpCards(List<PowerUpCardClient> powerUpCards) {
        myPowerUps = powerUpCards;
        for (int i = 0; i < powerUpCards.size(); i++) {
            int position = i;
            Platform.runLater(() -> myPowerUpSlots.getChildren().get(position).setStyle("-fx-background-image: url('" + view.getPowerUpAsset(powerUpCards.get(position).getId()) + "'); "));
        }
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
            switch (availableMoveDirection) {
                case UP:
                    arrowUP.setDisable(false);
                    break;
                case RIGHT:
                    arrowRIGHT.setDisable(false);
                    break;
                case DOWN:
                    arrowDOWN.setDisable(false);
                    break;
                case LEFT:
                    arrowLEFT.setDisable(false);
                    break;
            }
        }

        arrowSTOP.setDisable(false);
    }

    public void disableArrows() {
        arrowUP.setDisable(true);
        arrowRIGHT.setDisable(true);
        arrowDOWN.setDisable(true);
        arrowLEFT.setDisable(true);
        arrowSTOP.setDisable(true);
    }

    public void arrowSTOPButtonPressed(MouseEvent actionEvent) {
        selectedMove.set(MoveDirection.STOP);
        disableArrows();
        semaphore.release();
    }

    public void arrowRIGHTPressed(MouseEvent actionEvent) {
//        Coordinates currentPosition = view.getLocalModel().getGameBoard().getPlayerPositionByName("toni").getCoordinates();
//        selectedPath.add(new Coordinates(currentPosition.getXCoordinate() + 1, currentPosition.getYCoordinate()));
        selectedMove.set(MoveDirection.RIGHT);
        disableArrows();
        semaphore.release();
    }

    public void arrowDOWNPressed(MouseEvent actionEvent) {
        selectedMove.set(MoveDirection.DOWN);
        disableArrows();
        semaphore.release();
    }

    public void arrowLEFTPressed(MouseEvent actionEvent) {
        selectedMove.set(MoveDirection.LEFT);
        disableArrows();
        semaphore.release();
    }

    public void arrowUPPressed(MouseEvent actionEvent) {
        selectedMove.set(MoveDirection.UP);
        disableArrows();
        semaphore.release();
    }

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

    public void loadScene(Avatar avatar) throws IOException {
        playerBoardGUIController = (PlayerBoardGUIController) loadFXML(GUI.FXML_PATH_PLAYER_BOARD, myPlayerBoardAnchorPane);
        playerBoardGUIController.loadScene(avatar);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        for (PlayerAction playerAction : PlayerAction.values()) {
            Button actionButton = new Button(playerAction.toString());
            actionButton.setOnAction(event -> {
                chosenAction.set(playerAction);
                semaphore.release();
            });
            actionsFlowPane.getChildren().add(actionButton);
        }

        actionsFlowPane.setDisable(true);
    }
}

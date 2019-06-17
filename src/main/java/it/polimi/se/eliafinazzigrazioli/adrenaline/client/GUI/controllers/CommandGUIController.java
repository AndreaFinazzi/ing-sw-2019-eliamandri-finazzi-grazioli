package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.MoveDirection;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.atomic.AtomicReference;

public class CommandGUIController extends AbstractGUIController {
    private AtomicReference<MoveDirection> selectedMove;

    public CommandGUIController() {
        super();
    }

    public void setSelectedMove(AtomicReference<MoveDirection> selectedMove) {
        this.selectedMove = selectedMove;
    }

    public void pathCONFIRMButtonPressed(MouseEvent actionEvent) {
        System.out.println("UNLOCKING SEMAPHORE.");

        semaphore.release();
    }

    public void arrowRIGHTPressed(MouseEvent actionEvent) {
//        Coordinates currentPosition = view.getLocalModel().getGameBoard().getPlayerPositionByName("toni").getCoordinates();
//        selectedPath.add(new Coordinates(currentPosition.getXCoordinate() + 1, currentPosition.getYCoordinate()));
        selectedMove.set(MoveDirection.RIGHT);
        semaphore.release();
    }

    public void arrowDOWNPressed(MouseEvent actionEvent) {
        selectedMove.set(MoveDirection.RIGHT);
        semaphore.release();
    }

    public void arrowLEFTPressed(MouseEvent actionEvent) {
        selectedMove.set(MoveDirection.RIGHT);
        semaphore.release();
    }

    public void arrowUPPressed(MouseEvent actionEvent) {
        selectedMove.set(MoveDirection.RIGHT);
        semaphore.release();
    }
}

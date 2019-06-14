package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class MainGUIController extends AbstractGUIController {

    ArrayList<Coordinates> selectedPath;
    Semaphore semaphore;

    public MainGUIController() {
        super();
    }

    public void setSelectedPath(ArrayList<Coordinates> selectedPath) {
        this.selectedPath = selectedPath;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void pathCONFIRMButtonPressed(MouseEvent actionEvent) {
        System.out.println("UNLOCKING SEMAPHORE.");

        semaphore.release();
    }

    public void arrowRIGHTPressed(MouseEvent actionEvent) {
//        Coordinates currentPosition = view.getLocalModel().getGameBoard().getPlayerPositionByName("toni").getCoordinates();
//        selectedPath.add(new Coordinates(currentPosition.getXCoordinate() + 1, currentPosition.getYCoordinate()));
        selectedPath.add(new Coordinates(2, 2));
        System.out.println("arrow RIGHT pressed.");
    }

    public void arrowDOWNPressed(MouseEvent actionEvent) {
        selectedPath.add(new Coordinates(3, 2));
        System.out.println("arrow DOWN pressed.");

    }

    public void arrowLEFTPressed(MouseEvent actionEvent) {
        selectedPath.add(new Coordinates(2, 3));
        System.out.println("arrow LEFT pressed.");

    }

    public void arrowUPPressed(MouseEvent actionEvent) {
        selectedPath.add(new Coordinates(3, 4));
        System.out.println("arrow UP pressed.");

    }
}

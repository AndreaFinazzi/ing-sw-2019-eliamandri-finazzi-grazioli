package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PlayerAction;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public class MainGUIController extends AbstractGUIController {

    @FXML
    private ChoiceBox<MapType> availableMapsChoiceBox;
    @FXML
    private StackPane mapVoteOverlayStackPane;
    @FXML
    private ImageView voteMapPreviewImage;

    @FXML
    private AnchorPane overlay;

    @FXML
    private Pane mainBoardPane;

    private Semaphore semaphore;
    private AtomicReference<PlayerAction> chosenAction;
    private ArrayList<Coordinates> selectedPath;

    public MainGUIController() {
        super();
    }

    public void setVoteMap(ArrayList<MapType> availableMaps) {
        mapVoteOverlayStackPane.setVisible(true);

        availableMapsChoiceBox.getItems().addAll(availableMaps);
        availableMapsChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MapType>() {
            @Override
            public void changed(ObservableValue<? extends MapType> observable, MapType oldValue, MapType newValue) {

                File mapImageFile = new File(getClass().getResource(view.getMapFileName(newValue)).getFile());
                Image mapImage = new Image(mapImageFile.toURI().toString());

                // TODO not working
                voteMapPreviewImage.setImage(mapImage);
                voteMapPreviewImage.setFitWidth(200);
                voteMapPreviewImage.setFitHeight(260);
                voteMapPreviewImage.setVisible(true);
                voteMapPreviewImage.setCache(true);
            }
        });
    }

    public void voteMap(ActionEvent actionEvent) {
        showOverlay();

        view.notifyMapVoteEvent(availableMapsChoiceBox.getValue());
    }

    public void showOverlay() {
        overlay.setVisible(true);
    }

    public void hideOverlay() {
        overlay.setVisible(false);
    }

    public void loadMap() {
        String mapClass = "map_" + view.getLocalModel().getGameBoard().getMapType().name();
        mainBoardPane.getStyleClass().add(mapClass);

        hideOverlay();
        mapVoteOverlayStackPane.setVisible(false);
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void setSelectedPath(ArrayList<Coordinates> selectedPath) {
        this.selectedPath = selectedPath;
    }

    public void setChosenAction(AtomicReference<PlayerAction> chosenAction) {
        this.chosenAction = chosenAction;
    }
}
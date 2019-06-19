package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class MainGUIController extends AbstractGUIController {

    // Slots
    @FXML
    private VBox playerBoardsVBox;
    @FXML
    private AnchorPane commandsContainerAnchorPane;

    // Map vote overlay
    @FXML
    private ChoiceBox<MapType> availableMapsChoiceBox;
    @FXML
    private StackPane mapVoteOverlayStackPane;
    @FXML
    private ImageView voteMapPreviewImage;
    @FXML
    private Button voteMapButton;

    @FXML
    private AnchorPane overlay;

    // Main board
    @FXML
    private Pane mainBoardPane;

    public MainGUIController(GUI view) {
        super(view);
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
        mapVoteOverlayStackPane.setVisible(false);
        view.notifyMapVoteEvent(availableMapsChoiceBox.getValue());
    }

    public void showOverlay() {
        overlay.setVisible(true);
    }

    public void hideOverlay() {
        overlay.setVisible(false);
    }

    public void loadMap() {
        // Load chosen map
        String mapClass = GUI.MAP_STYLE_CLASS_PREFIX + view.getLocalModel().getGameBoard().getMapType().name();
        mainBoardPane.getStyleClass().remove(GUI.MAP_STYLE_CLASS_DEFAULT);
        mainBoardPane.getStyleClass().add(mapClass);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        voteMapButton.setOnAction(this::voteMap);

        // Initialize player boards
        try {
            ArrayList<OpponentPlayerGUIController> opponentPlayerGUIControllers = new ArrayList<>();

            for (String player : view.getLocalModel().getPlayersAvatarMap().keySet()) {
                if (player.equals(view.getClient().getPlayerName())) {
                    CommandGUIController commandGUIController = new CommandGUIController(view);
                    loadFXML(GUI.FXML_PATH_COMMANDS, commandsContainerAnchorPane, commandGUIController);
                    view.setCommandsGUIController(commandGUIController);
                    commandGUIController.loadMyPlayerBoard(view.getLocalModel().getPlayersAvatarMap().get(player));

                } else {
                    OpponentPlayerGUIController opponentPlayerGUIController = new OpponentPlayerGUIController(view);
                    loadFXML(GUI.FXML_PATH_OPPONENT_PLAYER_INFO, playerBoardsVBox, opponentPlayerGUIController);

                    opponentPlayerGUIControllers.add(opponentPlayerGUIController);
                    opponentPlayerGUIController.loadPlayerBoard(view.getLocalModel().getPlayersAvatarMap().get(player));
                }
            }

            mapVoteOverlayStackPane.setVisible(false);
            hideOverlay();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
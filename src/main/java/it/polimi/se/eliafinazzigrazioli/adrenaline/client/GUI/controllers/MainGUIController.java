package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

public class MainGUIController extends AbstractGUIController {

    // Slots
    @FXML
    private VBox playerBoardsVBox;
    @FXML
    private AnchorPane commandsContainerAnchorPane;

    @FXML
    private GridPane gameBoardGridPane;
    private Map<Coordinates, BoardSquareGUIController> coordinatesBoardSquareGUIControllerMap = new HashMap<>();
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

    @FXML
    private Pane weaponCardSlots_RED;
    @FXML
    private Pane weaponCardSlots_BLUE;
    @FXML
    private Pane weaponCardSlots_YELLOW;
    private Map<Room, Pane> roomWeaponCardSlotsEnumMap = new EnumMap<>(Room.class);
    private ArrayList<Room> rotatedAssetsRooms = new ArrayList<>();

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

    public void showCardCollected(String player, PowerUpCardClient cardCollected) {

    }

    public void updateWeaponCardOnMap(WeaponCardClient weaponCard, Room room) {
        Node weaponCardSlot = roomWeaponCardSlotsEnumMap.get(room).getChildren().get(weaponCard.getSlotPosition());
        if (weaponCardSlot == null) {
            throw new NullPointerException(String.format("WeaponCardSlot not found for:\t%s\n\tin:\t%s", weaponCard, room));
        } else {
            String uri = rotatedAssetsRooms.contains(room) ? view.getWeaponRotatedAsset(weaponCard.getId()) : view.getWeaponAsset(weaponCard.getId());
            Platform.runLater(() -> weaponCardSlot.setStyle("-fx-background-image: url('" + uri + "'); "));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        rotatedAssetsRooms.add(Room.BLUE);
        roomWeaponCardSlotsEnumMap.put(Room.BLUE, weaponCardSlots_BLUE);
        roomWeaponCardSlotsEnumMap.put(Room.YELLOW, weaponCardSlots_YELLOW);
        roomWeaponCardSlotsEnumMap.put(Room.RED, weaponCardSlots_RED);


        // Initialize GameBoard grid
//        for (Node boardSquare : gameBoardGridPane.getChildren()) {
//            // check if Pane is explicitly placed in grid
//            if (boardSquare.hasProperties()) {
//                Coordinates squareCoordinates = new Coordinates(GridPane.getRowIndex(boardSquare), GridPane.getColumnIndex(boardSquare));
//                gameBoardMap.put(squareCoordinates, (Pane) boardSquare);
//            }
//        }

        int nRows = gameBoardGridPane.getRowConstraints().size();
        int nColumns = gameBoardGridPane.getColumnConstraints().size();
        for (int x = 0; x < nRows; x++) {
            for (int y = 0; y < nColumns; y++) {
                BoardSquareGUIController boardSquareGUIController = new BoardSquareGUIController(view);
                try {
                    Node boardSquare = loadFXML(GUI.FXML_PATH_BOARD_SQUARE, boardSquareGUIController);
                    int gridPaneX = nRows - x - 1;
                    int gridPaneY = nColumns - y - 1;
                    double rowHeight = gameBoardGridPane.getRowConstraints().get(gridPaneX).prefHeightProperty().doubleValue();
                    double columnWidth = gameBoardGridPane.getColumnConstraints().get(gridPaneY).prefWidthProperty().doubleValue();
                    boardSquare.minWidth(columnWidth);
                    boardSquare.minHeight(rowHeight);
                    boardSquare.prefWidth(columnWidth);
                    boardSquare.prefHeight(rowHeight);
                    boardSquare.maxWidth(columnWidth);
                    boardSquare.maxHeight(rowHeight);
                    gameBoardGridPane.add(boardSquare, gridPaneX, gridPaneY);
                    coordinatesBoardSquareGUIControllerMap.put(new Coordinates(x, y), boardSquareGUIController);
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        voteMapButton.setOnAction(this::voteMap);

        // Initialize player boards
        try {
            Map<String, OpponentPlayerGUIController> opponentPlayerToGUIControllerMap = new HashMap<>();

            for (String player : view.getLocalModel().getPlayersAvatarMap().keySet()) {
                if (player.equals(view.getClient().getPlayerName())) {
                    CommandsGUIController commandsGUIController = new CommandsGUIController(view);
                    loadFXML(GUI.FXML_PATH_COMMANDS, commandsContainerAnchorPane, commandsGUIController);
                    view.setCommandsGUIController(commandsGUIController);
                    commandsGUIController.loadMyPlayerBoard(view.getLocalModel().getPlayersAvatarMap().get(player));

                } else {
                    OpponentPlayerGUIController opponentPlayerGUIController = new OpponentPlayerGUIController(view);
                    loadFXML(GUI.FXML_PATH_OPPONENT_PLAYER_INFO, playerBoardsVBox, opponentPlayerGUIController);

                    opponentPlayerToGUIControllerMap.put(player, opponentPlayerGUIController);
                    opponentPlayerGUIController.loadPlayerBoard(view.getLocalModel().getPlayersAvatarMap().get(player));
                }
            }
            view.setOpponentPlayerToGUIControllerMap(opponentPlayerToGUIControllerMap);
            mapVoteOverlayStackPane.setVisible(false);
            hideOverlay();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void updateAmmoCardOnMap(Coordinates coordinates, AmmoCardClient ammoCard) {
        BoardSquareGUIController boardSquareGUIController = coordinatesBoardSquareGUIControllerMap.get(coordinates);
        if (boardSquareGUIController == null) {
            throw new NullPointerException(String.format("BoardSquare controller not found in:\t%s", coordinates));
        } else {
            boardSquareGUIController.setAmmo(ammoCard);
        }
    }
}
package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Selectable;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public class MainGUIController extends AbstractGUIController {

    private AtomicReference<WeaponCardClient> selectedWeapon;
    private AtomicReference<Coordinates> selectedCoordinates;
    private AtomicReference<String> selectedPlayer;


    // Slots
    @FXML
    private VBox playerBoardsVBox;

    @FXML
    private AnchorPane commandsContainerAnchorPane;
    @FXML
    private GridPane gameBoardGridPane;
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

    private Map<Coordinates, BoardSquareGUIController> coordinatesBoardSquareGUIControllerMap = new HashMap<>();

    private Map<String, Node> playersNodeMap;

    private Class<? extends Selectable> selectionTargetType;

    public MainGUIController(GUI view) {
        super(view);
        playersNodeMap = new HashMap<>();
    }

    public void setSelectedWeapon(AtomicReference<WeaponCardClient> selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public void setSelectedCoordinates(AtomicReference<Coordinates> selectedCoordinates) {
        this.selectedCoordinates = selectedCoordinates;
    }

    public void setSelectedPlayer(AtomicReference<String> selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public void setVoteMap(ArrayList<MapType> availableMaps) {
        mapVoteOverlayStackPane.setVisible(true);

        availableMapsChoiceBox.getItems().addAll(availableMaps);
        availableMapsChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MapType>() {
            @Override
            public void changed(ObservableValue<? extends MapType> observable, MapType oldValue, MapType newValue) {

                Image mapImage = new Image(view.getMapAsset(newValue));

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
        String mapClass = GUI.STYLE_CLASS_MAP_PREFIX + view.getLocalModel().getGameBoard().getMapType().name();
        mainBoardPane.getStyleClass().remove(GUI.STYLE_CLASS_MAP_DEFAULT);
        mainBoardPane.getStyleClass().add(mapClass);

    }

    private void disableWeaponCards() {
        for (Pane weaponCardSlots : roomWeaponCardSlotsEnumMap.values()) {
            for (Node weaponCard :
                    weaponCardSlots.getChildren()) {
                weaponCard.setDisable(true);
            }
        }
    }

    public void setSelectableWeaponCards(List<WeaponCardClient> selectableWeapons) {
        for (WeaponCardClient weaponCard : selectableWeapons) {
            Pane weaponCardSlotsPane = roomWeaponCardSlotsEnumMap.get(weaponCard.getSpawnBoardSquare());
            weaponCardSlotsPane.getChildren().get(weaponCard.getSlotPosition()).setDisable(false);
            Platform.runLater(weaponCardSlotsPane::requestFocus);
        }
    }

    public void setSelectableCoordinates(List<Coordinates> selectableCoordinates) {
        for (Coordinates coordinates : selectableCoordinates) {
            coordinatesBoardSquareGUIControllerMap.get(coordinates).makeSelectable(event -> {
                disableBoardSqures();
                selectedCoordinates.set(coordinates);
                semaphore.release();
            });
        }
    }

    private void disableBoardSqures() {
        coordinatesBoardSquareGUIControllerMap.forEach((coordinates, boardSquareGUIController) -> {
            boardSquareGUIController.makeUnselectable();
        });
    }

    public void updateWeaponCardOnMap(WeaponCardClient weaponCard) {
        Node weaponCardSlot = roomWeaponCardSlotsEnumMap.get(weaponCard.getSpawnBoardSquare()).getChildren().get(weaponCard.getSlotPosition());
        if (weaponCardSlot == null) {
            throw new NullPointerException(String.format("WeaponCardSlot not found for:\t%s\n\tin:\t%s", weaponCard, weaponCard.getSpawnBoardSquare()));
        } else {
            weaponCardSlot.getProperties().put(GUI.PROPERTIES_CARD_ID_KEY, weaponCard.getId());
            String uri = rotatedAssetsRooms.contains(weaponCard.getSpawnBoardSquare()) ? view.getWeaponRotatedAsset(weaponCard.getId()) : view.getWeaponAsset(weaponCard.getId());
            view.applyBackground(weaponCardSlot, uri);
        }
    }

    public void updateWeaponCardOnMap(WeaponCardClient weaponCard, Room room, int position) {
        String uri;
        Node weaponCardSlot = roomWeaponCardSlotsEnumMap.get(room).getChildren().get(position);

        if (weaponCardSlot == null) {
            throw new NullPointerException(String.format("WeaponCardSlot not found for:\t%s\n\tin:\t%s", weaponCard, room));
        } else {
            if (weaponCard != null) {
                uri = rotatedAssetsRooms.contains(room) ? view.getWeaponRotatedAsset(weaponCard.getId()) : view.getWeaponAsset(weaponCard.getId());
            } else {
                uri = rotatedAssetsRooms.contains(room) ? view.getWeaponRotatedAsset(GUI.ASSET_ID_HIDDEN_CARD) : view.getWeaponAsset(GUI.ASSET_ID_HIDDEN_CARD);
            }
            view.applyBackground(weaponCardSlot, uri);
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

    public void removeWeaponCardFromMap(Room room, WeaponCardClient weaponCard, WeaponCardClient droppedCard) {
        String uri;
        Node weaponCardNode = GUI.getChildrenByProperty(roomWeaponCardSlotsEnumMap.get(room).getChildren(), GUI.PROPERTIES_CARD_ID_KEY, weaponCard.getId());
        if (weaponCardNode != null) {
            if (droppedCard == null)
                uri = rotatedAssetsRooms.contains(room) ? view.getWeaponRotatedAsset(GUI.ASSET_ID_HIDDEN_CARD) : view.getWeaponAsset(GUI.ASSET_ID_HIDDEN_CARD);
            else
                uri = rotatedAssetsRooms.contains(room) ? view.getWeaponRotatedAsset(droppedCard.getId()) : view.getWeaponAsset(droppedCard.getId());
            view.applyBackground(weaponCardNode, uri);
        }
    }

    public void movePlayer(String player, Coordinates destination) throws IOException {
        for (BoardSquareGUIController boardSquare : coordinatesBoardSquareGUIControllerMap.values()) {
            if (boardSquare.removePlayer(player)) break;
        }

        playersNodeMap.put(player, coordinatesBoardSquareGUIControllerMap.get(destination).addPlayer(player));
    }

    public void setSelectablePlayers(List<String> players) {
        players.forEach(player -> {
            Node playerNode = playersNodeMap.get(player);
            if (playerNode != null) {
                playerNode.getStyleClass().add(GUI.STYLE_CLASS_AVATAR_SELECTABLE);

                playerNode.getParent().setDisable(false);
                playerNode.setDisable(false);

                playerNode.setOnMouseEntered(event -> view.getOpponentPlayerToGUIControllerMap().get(player).highlight(true));
                playerNode.setOnMouseExited(event -> view.getOpponentPlayerToGUIControllerMap().get(player).highlight(false));
                playerNode.setOnMouseClicked(event -> {
                    disablePlayers();
                    selectedPlayer.set(player);
                    semaphore.release();
                });
            }
        });
    }


    private void disablePlayers() {
        playersNodeMap.forEach((player, node) -> {
            node.getStyleClass().remove(GUI.STYLE_CLASS_AVATAR_SELECTABLE);
            OpponentPlayerGUIController opponentPlayerGUIController = view.getOpponentPlayerToGUIControllerMap().get(player);
            if (opponentPlayerGUIController != null) opponentPlayerGUIController.highlight(false);
            node.setOnMouseEntered(null);
            node.setOnMouseExited(null);
            node.setOnMouseClicked(null);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!initialized) {
            initialized = true;

            super.initialize(location, resources);

            rotatedAssetsRooms.add(Room.BLUE);
            roomWeaponCardSlotsEnumMap.put(Room.BLUE, weaponCardSlots_BLUE);
            roomWeaponCardSlotsEnumMap.put(Room.YELLOW, weaponCardSlots_YELLOW);
            roomWeaponCardSlotsEnumMap.put(Room.RED, weaponCardSlots_RED);

            // Initialize collectible cards slots
            for (Map.Entry<Room, Pane> weaponCardSlot : roomWeaponCardSlotsEnumMap.entrySet()) {
                for (int i = 0; i < Rules.GAME_BOARD_MAX_WEAPONS_ON_SPAWN; i++) {
                    try {
                        Button button = (Button) loadFXML(GUI.FXML_PATH_WEAPON, weaponCardSlot.getValue(), this);
                        if (rotatedAssetsRooms.contains(weaponCardSlot.getKey()))
                            button.getStyleClass().add(GUI.STYLE_CLASS_WEAPON_ROTATED);
                        else
                            button.getStyleClass().add(GUI.STYLE_CLASS_WEAPON_ON_MAP);
                        button.setDisable(true);
                        int slotPosition = i;
                        button.setOnMouseClicked(event -> {
                            if (event.getButton().equals(MouseButton.SECONDARY)) {
                                view.showCardDetails(view.getLocalModel().getWeaponCardByIdOnMap((String) button.getProperties().get(GUI.PROPERTIES_CARD_ID_KEY)));
                            } else {
                                disableWeaponCards();
                                selectedWeapon.set(view.getLocalModel().getGameBoard().getSpawnBoardSquareClientByRoom(weaponCardSlot.getKey()).getWeaponCardsBySlotPosition(slotPosition));
                                semaphore.release();
                            }
                        });
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }
                }
            }

            int nRows = gameBoardGridPane.getRowConstraints().size();
            int nColumns = gameBoardGridPane.getColumnConstraints().size();
            for (int x = 0; x < nRows; x++) {
                for (int y = 0; y < nColumns; y++) {
                    BoardSquareGUIController boardSquareGUIController = new BoardSquareGUIController(view);
                    try {
                        Node boardSquare = loadFXML(GUI.FXML_PATH_BOARD_SQUARE, boardSquareGUIController);
                        int gridPaneY = nRows - x - 1;
                        int gridPaneX = nColumns - y - 1;
                        double rowHeight = gameBoardGridPane.getRowConstraints().get(gridPaneY).prefHeightProperty().doubleValue();
                        double columnWidth = gameBoardGridPane.getColumnConstraints().get(gridPaneX).prefWidthProperty().doubleValue();

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
                        commandsGUIController.loadMyPlayerBoard();

                    } else {
                        OpponentPlayerGUIController opponentPlayerGUIController = new OpponentPlayerGUIController(view);
                        loadFXML(GUI.FXML_PATH_OPPONENT_PLAYER_INFO, playerBoardsVBox, opponentPlayerGUIController);

                        opponentPlayerToGUIControllerMap.put(player, opponentPlayerGUIController);
                        opponentPlayerGUIController.loadPlayerBoard(player);
                    }
                }
                view.setOpponentPlayerToGUIControllerMap(opponentPlayerToGUIControllerMap);
                mapVoteOverlayStackPane.setVisible(false);
                hideOverlay();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

}
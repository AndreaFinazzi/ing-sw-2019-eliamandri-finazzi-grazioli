package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class GUI extends Application implements RemoteView {

    // static constants
    public static final String STYLE_CLASS_MAP_DEFAULT = "map_NONE";
    public static final String STYLE_CLASS_MAP_PREFIX = "map_";
    public static final String STYLE_CLASS_HIGHLIGHT = "highlight";
    public static final String STYLE_CLASS_DISABLE = "disable";
    public static final String STYLE_CLASS_WEAPON_ROTATED = "rotatedWeaponCard";
    public static final String STYLE_CLASS_WEAPON_ON_MAP = "onMapWeaponCard";
    public static final String STYLE_CLASS_WEAPON_MY = "myWeaponCard";
    public static final String STYLE_CLASS_BOARD_SQUARE_SELECTABLE = "selectableBoardSquare";
    public static final String STYLE_CLASS_AVATAR_SELECTABLE = "selectableAvatar";
    public static final String STYLE_CLASS_EFFECT_SELECTABLE = "selectableEffectLabel";
    public static final String STYLE_CLASS_EFFECT_DEFAULT = "effectLabel";
    public static final String STYLE_CLASS_PLAYER_BOARD_DEFAULT = "playerBoard_NONE";
    public static final String STYLE_CLASS_PLAYER_BOARD_PREFIX = "playerBoard_";

    public static final String FXML_PATH_ROOT = "/client/GUI/fxml/";
    public static final String ASSET_PATH_ROOT = "/client/GUI/assets/";
    public static final String ASSET_PATH_CARDS_ROOT = ASSET_PATH_ROOT + "cards/";
    public static final String ASSET_PATH_CARDS_ROTATED_ROOT = ASSET_PATH_CARDS_ROOT + "rotated/";
    public static final String ASSET_PATH_MAPS_ROOT = ASSET_PATH_ROOT + "maps/";
    public static final String ASSET_PATH_AMMO_ROOT = ASSET_PATH_ROOT + "ammo/";
    public static final String ASSET_PATH_PLAYER_BOARDS_ROOT = ASSET_PATH_ROOT + "playerboards/";
    public static final String ASSET_PATH_AVATAR_ROOT = ASSET_PATH_ROOT + "avatars/";
    public static final String ASSET_PATH_ICONS_ROOT = ASSET_PATH_ROOT + "icons/";
    public static final String ASSET_PATH_MARKS_ROOT = ASSET_PATH_ROOT + "marks/";

    public static final String ASSET_FORMAT_CARDS = ".png";
    public static final String ASSET_FORMAT_ICONS = ".png";
    public static final String ASSET_FORMAT_AMMO = ".png";
    public static final String ASSET_PREFIX_POWER_UP = "AD_powerups_IT_";
    public static final String ASSET_PREFIX_WEAPON = "AD_weapons_IT_";
    public static final String ASSET_PREFIX_AMMO = "AD_ammo_";
    public static final String ASSET_PREFIX_AVATAR = "avatar_";
    public static final String ASSET_PREFIX_ICONS_ARROWS = "arrow_";
    public static final String ASSET_PREFIX_MARKS = "mark_";

    public static final String ASSET_ID_HIDDEN_CARD = "02";
    public static final String ASSET_ID_HIDDEN_AMMO = "04";

    public static final String FXML_PATH_LOGIN = FXML_PATH_ROOT + "login.fxml";
    public static final String FXML_PATH_COMMANDS = FXML_PATH_ROOT + "commands.fxml";
    public static final String FXML_PATH_OPPONENT_PLAYER_INFO = FXML_PATH_ROOT + "opponent_player_info.fxml";
    public static final String FXML_PATH_PLAYER_BOARD = FXML_PATH_ROOT + "player_board.fxml";
    public static final String FXML_PATH_BOARD_SQUARE = FXML_PATH_ROOT + "board_square.fxml";
    public static final String FXML_PATH_MAIN = FXML_PATH_ROOT + "main.fxml";
    public static final String FXML_PATH_POWER_UP = FXML_PATH_ROOT + "power_up_card.fxml";
    public static final String FXML_PATH_WEAPON = FXML_PATH_ROOT + "weapon_card.fxml";
    public static final String FXML_PATH_AVATAR = FXML_PATH_ROOT + "avatar.fxml";
    public static final String FXML_PATH_MARK = FXML_PATH_ROOT + "mark.fxml";
    public static final String FXML_PATH_SKULL = FXML_PATH_ROOT + "skull.fxml";

    public static final String PROPERTIES_CARD_ID_KEY = "card_id";
    public static final String PROPERTIES_EFFECT_KEY = "effect_name";
    public static final String PROPERTIES_AVATAR_KEY = "avatar";
    public static final String PROPERTIES_AMMO_KEY = "ammo_name";

    private static GUI instance;
    static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    String[] args;
    Stage primaryStage;

    private Client client;
    private List<Observer> observers = new ArrayList<>();

    private LocalModel localModel;

    private MainGUIController mainGUIController;
    private CommandsGUIController commandsGUIController;
    private LoginGUIController loginGUIController;

    private Semaphore currentSemaphore;

    ///TODO define a setter and verify Map is the correct data structure

    private Map<String, OpponentPlayerGUIController> opponentPlayerToGUIControllerMap;
    private Map<Coordinates, BoardSquareGUIController> coordinatesBoardSquareGUIControllerMap = new HashMap<>();

    private boolean initialized = false;

    public GUI() {
        instance = this;
        localModel = new LocalModel();
    }

    @Override
    public void showSuddenDeadUpdate(String deadPlayer) {
        if (deadPlayer.equals(client.getPlayerName()))
            commandsGUIController.showSuddenDeath();
        else
            opponentPlayerToGUIControllerMap.get(deadPlayer).showSuddenDeath();
    }

    @Override
    public void showPointsUpdate(Map<String, Integer> pointsMap) {
        for (Map.Entry<String, Integer> playerPoints : pointsMap.entrySet()) {
            if (!playerPoints.getKey().equals(client.getPlayerName()))
                opponentPlayerToGUIControllerMap.get(playerPoints.getKey()).updatePoints();
        }
    }

    public synchronized static GUI getInstance(String[] args) {
        if (instance == null) {
            new Thread(() -> {
                // Have to run in a thread because launch doesn't return
                Application.launch(GUI.class, args);
            }).start();
        }

        while (instance == null) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }

        return instance;
    }

    public Map<String, OpponentPlayerGUIController> getOpponentPlayerToGUIControllerMap() {
        return opponentPlayerToGUIControllerMap;
    }

    public void showOverlay() {
        mainGUIController.showOverlay();
    }

    public void hideOverlay() {
        mainGUIController.hideOverlay();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public LocalModel getLocalModel() {
        return localModel;
    }

    @Override
    public Client getClient() {
        return client;
    }

    public void setCommandsGUIController(CommandsGUIController commandsGUIController) {
        this.commandsGUIController = commandsGUIController;
    }

    @Override
    public void showAmmoCollectedUpdate(String player, Ammo ammo, boolean actuallyCollected, boolean lastOfCard) {
        if (lastOfCard) {
            if (!player.equals(client.getPlayerName()))
                opponentPlayerToGUIControllerMap.get(player).showAmmoCollected(ammo, actuallyCollected);
            else
                commandsGUIController.showAmmoCollected(ammo, actuallyCollected);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Adrenaline");
    }

    @Override
    public void showPlayerMovementUpdate(String player, List<Coordinates> path) {
        if (!path.isEmpty()) {
            try {
                if (!player.equals(client.getPlayerName())) {
                    Coordinates nextDestination = path.get(0);
                    mainGUIController.movePlayer(player, nextDestination);
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> {
                        path.remove(0);
                        showPlayerMovementUpdate(player, path);
                    });
                    delay.play();
                } else {
                    mainGUIController.movePlayer(player, path.get(path.size() - 1));
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    @Override
    public void showMessage(Object message) {
        if (commandsGUIController != null)
            commandsGUIController.showMessage(message);
        else if (loginGUIController != null)
            loginGUIController.showMessage(message);
        else
            LOGGER.log(Level.INFO, message.toString());
    }

    @Override
    public void showShotPlayerUpdate(String damagedPlayer, List<DamageMark> damages, List<DamageMark> marks, List<DamageMark> removedMarks) {
        try {
            if (!damagedPlayer.equals(client.getPlayerName())) {
                opponentPlayerToGUIControllerMap.get(damagedPlayer).showDamageReceived(damages, marks);
            } else {
                commandsGUIController.showDamageReceived(damages, marks);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void showPlayerMovedByWeaponUpdate(String attackingPlayer, String playerMoved, String weaponUsed, Coordinates finalPosition) {
        try {
            mainGUIController.movePlayer(playerMoved, finalPosition);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public Room selectRoom(List<Room> rooms) {
        List<Coordinates> selectableCoordinates = new ArrayList<>();
        if (!rooms.isEmpty()) {
            for (Room room : rooms) {
                localModel.getGameBoard().getRoomSquares(room).forEach(boardSquare -> selectableCoordinates.add(boardSquare.getCoordinates()));
            }

        }

        return localModel.getGameBoard().getBoardSquareByCoordinates(selectCoordinates(selectableCoordinates)).getRoom();
    }

    @Override
    public void showPlayerReconnection(String player) {
        try {
            if (!player.equals(client.getPlayerName())) {
                opponentPlayerToGUIControllerMap.get(player).setReconnected();
            } else {
                commandsGUIController.setReconnected();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void showPlayerDisconnection(String player) {
        if (!player.equals(client.getPlayerName())) {
            opponentPlayerToGUIControllerMap.get(player).setDisconnected();
        } else {
            setDisconnected();
        }
    }

    @Override
    public WeaponEffectClient selectWeaponEffect(WeaponCardClient weapon, List<WeaponEffectClient> callableEffects) {
        if (!callableEffects.isEmpty()) {
            AtomicReference<WeaponEffectClient> selectedEffect = new AtomicReference<>();
            Semaphore semaphore = new Semaphore(0);

            currentSemaphore = semaphore;

            try {
                commandsGUIController.setSelectableEffects(semaphore, selectedEffect, weapon, callableEffects);
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

            synchronized (semaphore) {
                commandsGUIController.disableCards();
                return selectedEffect.get();
            }
        }

        return null;
    }

    @Override
    public PlayerAction selectAction() {
        AtomicReference<PlayerAction> chosenAction = new AtomicReference<>();
        Semaphore semaphore = new Semaphore(0);

        currentSemaphore = semaphore;

        do {
            commandsGUIController.setSemaphore(semaphore);
            commandsGUIController.setChosenAction(chosenAction);

            commandsGUIController.setChoseAction();

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

        } while (chosenAction.get() == null);

        synchronized (semaphore) {
            commandsGUIController.setLockedCommands();
            return chosenAction.get();
        }
    }

    @Override
    public WeaponCardClient selectWeaponCardFromHand(List<WeaponCardClient> selectableWeapons) {
        Coordinates coordinates = localModel.getGameBoard().getPlayerPositionByName(client.getPlayerName()).getCoordinates();
        try {
            mainGUIController.movePlayer(client.getPlayerName(), coordinates);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        if (!selectableWeapons.isEmpty()) {

            Semaphore semaphore = new Semaphore(0);
            AtomicReference<WeaponCardClient> selectedWeaponCard = new AtomicReference<>();

            currentSemaphore = semaphore;
            commandsGUIController.setSemaphore(semaphore);

            commandsGUIController.setSelectedWeapon(selectedWeaponCard);
            commandsGUIController.setSelectableWeapon(selectableWeapons);


            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

            synchronized (semaphore) {
                return selectedWeaponCard.get();
            }
        }

        return null;
    }

    //todo implement
    @Override
    public PowerUpCardClient selectPowerUp(List<PowerUpCardClient> selectablePowerUps) {
        if (!selectablePowerUps.isEmpty()) {

            AtomicReference<PowerUpCardClient> selectedPowerUp = new AtomicReference<>();
            Semaphore semaphore = new Semaphore(0);

            currentSemaphore = semaphore;
            commandsGUIController.setSemaphore(semaphore);

            commandsGUIController.setSelectablePowerUp(selectablePowerUps);
            commandsGUIController.setSelectedPowerUp(selectedPowerUp);


            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

            synchronized (semaphore) {
                return selectedPowerUp.get();
            }
        }

        return null;
    }

    @Override
    public String selectPlayer(List<String> players) {
        if (!players.isEmpty()) {
            AtomicReference<String> selectedPlayer = new AtomicReference<>();
            Semaphore semaphore = new Semaphore(0);

            currentSemaphore = semaphore;
            mainGUIController.setSemaphore(semaphore);

            mainGUIController.setSelectedPlayer(selectedPlayer);
            mainGUIController.setSelectablePlayers(players);


            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

            synchronized (semaphore) {
                return selectedPlayer.get();
            }
        }

        return null;
    }

    //todo implement
    @Override
    public WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        if (!reloadableWeapons.isEmpty()) {

        }
        return null;
    }

    @Override
    public void error(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    @Override
    public void showLogin(List<Avatar> availableAvatars) {
        if (!initialized) {
            initialized = true;

            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH_LOGIN));

            StackPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

            loginGUIController = loader.getController();
            loginGUIController.setView(this);
            loginGUIController.setAvailableAvatarsList(availableAvatars);

            Scene scene = new Scene(root);

            Platform.runLater(() -> {
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);

                primaryStage.show();
            });
        } else {
            loginGUIController.setRetry();
        }
    }

    @Override
    public void showLoginSuccessful() {
        loginGUIController.waitForMatchStart();
    }

    @Override
    public void showMapVote(List<MapType> availableMaps) {
        initialize();

        mainGUIController.setVoteMap(availableMaps);
    }

    private void initialize() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXML_PATH_MAIN));
        mainGUIController = new MainGUIController(this);
        loader.setController(mainGUIController);

        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }


        Pane finalRoot = root;
        Platform.runLater(() -> {
            primaryStage.getScene().setRoot(finalRoot);
            primaryStage.setFullScreen(true);
            primaryStage.setMaximized(true);
            primaryStage.setResizable(false);

            primaryStage.fullScreenProperty().addListener((v, o, n) -> {
                if (!primaryStage.isFullScreen()) {
                    primaryStage.setResizable(false);
                    primaryStage.setResizable(true);
                }
            });
        });
    }

    @Override
    public void showBeginMatch() {
        mainGUIController.loadMap();

        showAmmoCardResettingUpdate(localModel.getGameBoard().getCoordinatesAmmoCardMap());
        showWeaponCardResettingUpdate(localModel.getWeaponCardsSetup());
        // show players
        for (Map.Entry<String, BoardSquareClient> positionEntry : localModel.getGameBoard().getPlayersPosition().entrySet())
            showPlayerMovementUpdate(positionEntry.getKey(), new ArrayList<>(Arrays.asList(positionEntry.getValue().getCoordinates())));

        hideOverlay();
    }

    @Override
    public void updatePlayerInfo(String player) {
        client.setPlayerName(player);
    }

    @Override
    public void showMap() {

    }

    @Override
    public void showPowerUpCollectionUpdate(String player, PowerUpCardClient cardCollected, boolean isOpponent) {
        if (isOpponent) {
            try {
                opponentPlayerToGUIControllerMap.get(player).setCardCollected(cardCollected);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        } else
            commandsGUIController.updatePowerUpCards();
    }

    @Override
    public void showWeaponCollectionUpdate(String player, WeaponCardClient collectedCard, WeaponCardClient droppedCard, Room roomColor) {
        if (!player.equals(getClient().getPlayerName())) {
            try {
                opponentPlayerToGUIControllerMap.get(player).setCardCollected(collectedCard);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        } else {
            commandsGUIController.updateWeaponCards();
        }
        mainGUIController.removeWeaponCardFromMap(roomColor, collectedCard, droppedCard);

    }

    @Override
    public void showWeaponCardResettingUpdate(Map<Coordinates, List<WeaponCardClient>> coordinatesWeaponsMap) {
        for (Map.Entry<Coordinates, List<WeaponCardClient>> coordinatesWeaponsEntry : coordinatesWeaponsMap.entrySet()) {
            for (WeaponCardClient weaponCard : coordinatesWeaponsEntry.getValue()) {
                mainGUIController.updateWeaponCardOnMap(weaponCard);
            }
        }
    }

    @Override
    public void showPaymentUpdate(String player, List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos) {
        if (!player.equals(client.getPlayerName())) {
            opponentPlayerToGUIControllerMap.get(player).showPaymentUpdate(powerUpCardClients, ammos);
        } else {
            commandsGUIController.showPaymentUpdate(powerUpCardClients, ammos);
        }
    }

    @Override
    public void showAmmoCardCollectedUpdate(String player, AmmoCardClient ammoCard, Coordinates coordinates) {
        if (!player.equals(getClient().getPlayerName())) {
            try {

                opponentPlayerToGUIControllerMap.get(player).setCardCollected(ammoCard);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
        mainGUIController.updateAmmoCardOnMap(coordinates, null);
    }

    @Override
    public void showAmmoCardResettingUpdate(Map<Coordinates, AmmoCardClient> coordinatesAmmoCardMap) {
        for (Map.Entry<Coordinates, AmmoCardClient> coordinatesAmmoCardEntry : coordinatesAmmoCardMap.entrySet()) {
            mainGUIController.updateAmmoCardOnMap(coordinatesAmmoCardEntry.getKey(), coordinatesAmmoCardEntry.getValue());
        }
    }

    @Override
    public PowerUpCardClient selectPowerUpToKeep(List<PowerUpCardClient> cards) {
        commandsGUIController.setSpawnPowerUpCards(cards);

        AtomicReference<PowerUpCardClient> selectedPowerUp = new AtomicReference<>();
        Semaphore semaphore = new Semaphore(0);

        currentSemaphore = semaphore;
        commandsGUIController.setSemaphore(semaphore);

        commandsGUIController.setSelectedPowerUp(selectedPowerUp);

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        synchronized (semaphore) {
            return selectedPowerUp.get();
        }
    }

    @Override
    public WeaponCardClient selectWeaponCardFromSpawnSquare(Coordinates coordinates, List<WeaponCardClient> selectableWeapons) {
        Semaphore semaphore = new Semaphore(0);

        currentSemaphore = semaphore;
        mainGUIController.setSemaphore(semaphore);

        if (!selectableWeapons.isEmpty()) {
            try {
                mainGUIController.movePlayer(client.getPlayerName(), coordinates);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

            AtomicReference<WeaponCardClient> selectedWeapon = new AtomicReference<>();

            mainGUIController.setSelectableWeaponCards(selectableWeapons);
            mainGUIController.setSelectedWeapon(selectedWeapon);


            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

            synchronized (semaphore) {
                return selectedWeapon.get();
            }
        }

        return null;
    }

    @Override
    public Coordinates selectCoordinates(List<Coordinates> coordinates) {
        if (!coordinates.isEmpty()) {
            AtomicReference<Coordinates> selectedCoordinates = new AtomicReference<>();
            Semaphore semaphore = new Semaphore(0);

            currentSemaphore = semaphore;
            mainGUIController.setSemaphore(semaphore);

            mainGUIController.setSelectedCoordinates(selectedCoordinates);
            mainGUIController.setSelectableCoordinates(coordinates);

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

            synchronized (semaphore) {
                return selectedCoordinates.get();
            }
        }

        return null;
    }

    @Override
    public void showBeginTurnUpdate(String currentPlayer) {

    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public MoveDirection selectDirection(BoardSquareClient currentPose, ArrayList<MoveDirection> availableMoves) {
        AtomicReference<MoveDirection> selectedMove = new AtomicReference<>();
        Semaphore semaphore = new Semaphore(0);


        try {
            mainGUIController.movePlayer(client.getPlayerName(), currentPose.getCoordinates());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        currentSemaphore = semaphore;

        commandsGUIController.setSemaphore(semaphore);
        commandsGUIController.setSelectedMove(selectedMove);

        do {
            commandsGUIController.setAvailableMoves(availableMoves);
            commandsGUIController.setGetMove();

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }

            synchronized (semaphore) {
                commandsGUIController.setLockedCommands();
            }
        } while (!availableMoves.contains(selectedMove.get()));

        return selectedMove.get();

    }

    @Override
    public void showSpawnUpdate(String player, Coordinates spawnPoint, PowerUpCardClient spawnCard, boolean isOpponent) {
        if (player.equals(client.getPlayerName())) {
            commandsGUIController.showRespawn();
        } else {
            opponentPlayerToGUIControllerMap.get(player).showRespawn();
        }
        try {
            mainGUIController.movePlayer(player, spawnPoint);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void showCardDetails(WeaponCardClient weaponCard) {
        try {
            commandsGUIController.showDetails(weaponCard);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String getPowerUpAsset(String id) {
        String uri = ASSET_PATH_CARDS_ROOT + ASSET_PREFIX_POWER_UP + id + ASSET_FORMAT_CARDS;
        return this.getClass().getResource(uri).toExternalForm();

    }

    public String getWeaponAsset(String id) {
        String uri = ASSET_PATH_CARDS_ROOT + ASSET_PREFIX_WEAPON + id + ASSET_FORMAT_CARDS;
        return this.getClass().getResource(uri).toExternalForm();

    }

    public String getAmmoAsset(String id) {
        String uri = ASSET_PATH_AMMO_ROOT + ASSET_PREFIX_AMMO + id + ASSET_FORMAT_AMMO;
        return this.getClass().getResource(uri).toExternalForm();

    }

    public String getWeaponRotatedAsset(String id) {
        String uri = ASSET_PATH_CARDS_ROTATED_ROOT + ASSET_PREFIX_WEAPON + id + ASSET_FORMAT_CARDS;
        return this.getClass().getResource(uri).toExternalForm();

    }

    public String getAvatarAsset(String id) {
        String uri = ASSET_PATH_AVATAR_ROOT + ASSET_PREFIX_AVATAR + id + ASSET_FORMAT_ICONS;
        return this.getClass().getResource(uri).toExternalForm();

    }

    public String getIconAsset(String id) {
        String uri = ASSET_PATH_ICONS_ROOT + id;
        return this.getClass().getResource(uri).toExternalForm();

    }

    @Override
    public boolean selectYesOrNot() {
        return false;
    }

    @Override
    public void setDisconnected() {
        commandsGUIController.setDisconnected();
    }

    public String getMapAsset(MapType mapType) {
        String uri = ASSET_PATH_MAPS_ROOT + Config.CONFIG_CLIENT_GUI_ASSETS_MAP_PREFIX + mapType.name() + Config.CONFIG_CLIENT_GUI_ASSETS_MAP_FORMAT;
        return this.getClass().getResource(uri).toExternalForm();
    }

    public String getMarkAsset(DamageMark damageMark) {
        String uri = ASSET_PATH_MARKS_ROOT + ASSET_PREFIX_MARKS + damageMark.name() + ASSET_FORMAT_ICONS;
        return this.getClass().getResource(uri).toExternalForm();
    }

    public void setOpponentPlayerToGUIControllerMap(Map<String, OpponentPlayerGUIController> opponentPlayerToGUIControllerMap) {
        this.opponentPlayerToGUIControllerMap = opponentPlayerToGUIControllerMap;
    }

    public PowerUpCardClient getPowerUpById(List<PowerUpCardClient> powerUpCards, String id) {
        for (PowerUpCardClient powerUpCard : powerUpCards) {
            if (powerUpCard.getId().equals(id)) return powerUpCard;
        }
        return null;
    }

    @Override
    public void setLocalModel(LocalModel clientModel) {
        localModel = clientModel;
        buildLocalMap(clientModel.getMapType());

        initialize();
    }

    public static Node getChildrenByProperty(List<Node> nodeCollection, Object key, Object value) {
        for (Node node : nodeCollection) {
            if (node.hasProperties()) {
                if (node.getProperties().getOrDefault(key, "").equals(value)) return node;
            }
        }

        return null;
    }

    public static List<Node> getChildrensByProperty(List<Node> nodeCollection, Object key, Object value) {
        List<Node> nodes = new ArrayList<>();
        for (Node node : nodeCollection) {
            if (node.hasProperties()) {
                if (node.getProperties().getOrDefault(key, "").equals(value)) nodes.add(node);
            }
        }

        return nodes;
    }

    public void applyBackground(Node element, String url) {
        Platform.runLater(() -> element.setStyle("-fx-background-image: url('" + url + "'); "));
    }

    public void releaseCurrentSemaphore() {
        currentSemaphore.release();
    }

    public void disableAll() {
        commandsGUIController.disableCards();
    }
}
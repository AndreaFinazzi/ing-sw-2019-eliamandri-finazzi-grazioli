package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers.CommandGUIController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers.LoginGUIController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers.MainGUIController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers.OpponentPlayerGUIController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class GUI extends Application implements RemoteView {

    // static constants
    public static final String MAP_STYLE_CLASS_DEFAULT = "map_NONE";
    public static final String MAP_STYLE_CLASS_PREFIX = "map_";

    public static final String FXML_PATH_ROOT = "/client/GUI/fxml/";
    public static final String ASSET_PATH_ROOT = "/client/GUI/assets/";
    public static final String ASSET_PATH_CARDS_ROOT = ASSET_PATH_ROOT + "cards/";
    public static final String ASSET_PATH_MAPS_ROOT = ASSET_PATH_ROOT + "maps/";
    public static final String ASSET_PATH_AMMO_ROOT = ASSET_PATH_ROOT + "ammo/";
    public static final String ASSET_PATH_PLAYER_BOARDS_ROOT = ASSET_PATH_ROOT + "playerboards/";
    public static final String ASSET_PATH_ICONS_ROOT = ASSET_PATH_ROOT + "icons/";

    public static final String ASSET_FORMAT_CARDS = ".png";
    public static final String ASSET_FORMAT_ICONS = ".png";
    public static final String ASSET_PREFIX_POWER_UP = "AD_powerups_IT_";
    public static final String ASSET_PREFIX_WEAPON = "AD_weapons_IT_";
    public static final String ASSET_PREFIX_ICONS_ARROWS = "arrow_";

    public static final String FXML_PATH_LOGIN = FXML_PATH_ROOT + "login.fxml";
    public static final String FXML_PATH_COMMANDS = FXML_PATH_ROOT + "commands.fxml";
    public static final String FXML_PATH_OPPONENT_PLAYER_INFO = FXML_PATH_ROOT + "opponent_player_info.fxml";
    public static final String FXML_PATH_PLAYER_BOARD = FXML_PATH_ROOT + "player_board.fxml";
    public static final String FXML_PATH_MAIN = FXML_PATH_ROOT + "main.fxml";
    public static final String FXML_PATH_MY_POWER_UP = FXML_PATH_ROOT + "my_power_up.fxml";
    public static final String FXML_PATH_MY_WEAPON = FXML_PATH_ROOT + "my_weapon.fxml";

    private static GUI instance;
    static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    String[] args;
    Stage primaryStage;

    private Client client;
    private List<Observer> observers = new ArrayList<>();

    private LocalModel localModel;

    private MainGUIController mainGUIController;
    private CommandGUIController commandsGUIController;
    private LoginGUIController loginGUIController;
    ///TODO define a setter and verify Map is the correct data structure
    private Map<Avatar, OpponentPlayerGUIController> opponentPlayerGUIControllers;

    private boolean initialized = false;

    public GUI() {
        instance = this;
        localModel = new LocalModel();
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

    public void setCommandsGUIController(CommandGUIController commandsGUIController) {
        this.commandsGUIController = commandsGUIController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Adrenaline");
    }

    @Override
    public void showPlayerMovement(String player, List<Coordinates> path) {

    }

    @Override
    public void showSelectableSquare(List<Coordinates> selectable) {

    }

    @Override
    public void selectSelectableSquare(List<Coordinates> selectable) {

    }

    @Override
    public void selectSelectableEffect(List<String> callableEffects) {

    }

    @Override
    public void showMessage(Object message) {

    }

    @Override
    public PlayerAction choseAction() {
        AtomicReference<PlayerAction> chosenAction = new AtomicReference<>();
        Semaphore semaphore = new Semaphore(0);

        commandsGUIController.setSemaphore(semaphore);
        commandsGUIController.setChosenAction(chosenAction);

        commandsGUIController.setChoseAction();

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        synchronized (semaphore) {
            commandsGUIController.setLockedCommands();
            return chosenAction.get();
        }
    }

    @Override
    public void selectWeaponCard() {

    }

    //todo implement
    @Override
    public PowerUpCardClient selectPowerUp(List<PowerUpCardClient> cards) {
        return null;
    }

    //todo implement
    @Override
    public WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        return null;
    }

    @Override
    public void updatePlayerPosition(String nickname, Coordinates coordinates) {

    }

    @Override
    public void error(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    @Override
    public void showLogin(ArrayList<Avatar> availableAvatars) {
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
            loginGUIController.setLoggable(true);
        }
    }

    @Override
    public void showLoginSuccessful() {
        loginGUIController.waitForMatchStart();
    }

    @Override
    public void showMapVote(ArrayList<MapType> availableMaps) {
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
        });

        mainGUIController.setVoteMap(availableMaps);
    }

    @Override
    public void showBeginMatch() {
        mainGUIController.loadMap();
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
    public void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates) {

    }

    @Override
    public PowerUpCardClient selectPowerUpToKeep(List<PowerUpCardClient> cards) {
        AtomicReference<PowerUpCardClient> selectedPowerUp = new AtomicReference<>();

        commandsGUIController.setPowerUpCards(cards);
        commandsGUIController.setSelectedPowerUp(selectedPowerUp);

        Semaphore semaphore = new Semaphore(0);
        commandsGUIController.setSemaphore(semaphore);

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
    public void showBeginTurn(String currentPlayer) {

    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public MoveDirection getMoveFromUser(BoardSquareClient currentPose, ArrayList<MoveDirection> availableMoves) {
        AtomicReference<MoveDirection> selectedMove = new AtomicReference<>();
        Semaphore semaphore = new Semaphore(0);

        commandsGUIController.setSemaphore(semaphore);
        commandsGUIController.setAvailableMoves(availableMoves);
        commandsGUIController.setSelectedMove(selectedMove);

        commandsGUIController.setGetMove();

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        synchronized (semaphore) {
            commandsGUIController.setLockedCommands();
            return selectedMove.get();
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

    public String getIconAsset(String id) {
        String uri = ASSET_PATH_ICONS_ROOT + id;
        return GUI.class.getResource(uri).toExternalForm();

    }

    public String getMapFileName(MapType mapType) {
        return "/client/GUI/assets/maps/" + Config.CONFIG_CLIENT_GUI_ASSETS_MAP_PREFIX + mapType.name() + Config.CONFIG_CLIENT_GUI_ASSETS_MAP_FORMAT;
    }
}
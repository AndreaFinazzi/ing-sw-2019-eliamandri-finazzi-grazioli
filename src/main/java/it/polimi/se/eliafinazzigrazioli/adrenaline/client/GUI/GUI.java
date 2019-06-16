package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers.LoginGUIController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers.MainGUIController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.LocalModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
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
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class GUI extends Application implements RemoteView {

    private static GUI instance;
    static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    String[] args;
    Stage primaryStage;

    private Client client;
    private List<Observer> observers = new ArrayList<>();

    private LocalModel localModel;

    private MainGUIController mainGUIController;
    private LoginGUIController loginGUIController;

    private boolean initialized = false;

    public GUI() {
        instance = this;
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
            }
        }

        return instance;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void showOverlay() {
        mainGUIController.showOverlay();
    }

    public void hideOverlay() {
        mainGUIController.hideOverlay();
    }

    @Override
    public String getPlayer() {
        return client.getPlayerName();
    }

    @Override
    public int getClientID() {
        return client.getClientID();
    }

    @Override
    public void setClientID(int clientID) {
        client.setClientID(clientID);
    }

    @Override
    public LocalModel getLocalModel() {
        return localModel;
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
    public int choseAction() {
        return -1;
    }

    @Override
    public void selectWeaponCard() {

    }

    @Override
    public void updatePlayerPosition(String nickname, Coordinates coordinates) {

    }

    @Override
    public void error(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    @Override
    public void login(ArrayList<Avatar> availableAvatars) {
        if (!initialized) {
            initialized = true;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/GUI/fxml/login.fxml"));

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
    public void loginSuccessful() {
        loginGUIController.waitForMatchStart();
    }

    @Override
    public void mapVote(ArrayList<MapType> availableMaps) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/GUI/fxml/main.fxml"));

        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        mainGUIController = loader.getController();
        mainGUIController.setView(this);

        primaryStage.getScene().setRoot(root);

        Platform.runLater(() -> {
            primaryStage.setFullScreen(true);
        });

        mainGUIController.setVoteMap(availableMaps);
    }

    @Override
    public void updatePlayerInfo(String player) {
        client.setPlayerName(player);
    }

    @Override
    public void updateMatchPlayers(HashMap<String, Avatar> playerToAvatarMap) {

    }

    @Override
    public void buildLocalMap(MapType mapType) {

    }

    @Override
    public void showMap() {

    }

    @Override
    public void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates) {

    }

    @Override
    public PowerUpCard selectPowerUpToKeep(List<PowerUpCard> cards) {
        return null;
    }

    @Override
    public void collectWeapon(String collectedWeapon, String dropOfWeapon) {

    }

    @Override
    public void showBeginTurn(String currentPlayer) {

    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public List<Coordinates> getPathFromUser(int maxSteps) {
        ArrayList<Coordinates> selectedPath = new ArrayList<>();
        Semaphore semaphore = new Semaphore(0);

//        mainGUIController.setSemaphore(semaphore);
//        mainGUIController.setSelectedPath(selectedPath);

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        synchronized (semaphore) {
            return selectedPath;
        }

    }
}

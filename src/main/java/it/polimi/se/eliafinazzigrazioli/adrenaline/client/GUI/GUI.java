package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.LocalModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GUI extends Application implements RemoteView {

    static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    String[] args;
    Stage primaryStage;

    private Client client;
    private List<Observer> observers = new ArrayList<>();

    private LocalModel localModel;

    public GUI(String[] args, Client client) {
        this.args = args;
        this.client = client;
    }

    @Override
    public String getPlayer() {
        return client.getPlayerName();
    }

    @Override
    public int getClientID() {
        return client.getClientID();
    }

    public void setClientID(int clientID) {
        client.setClientID(clientID);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Adrenaline");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/GUI/fxml/login.fxml"));
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public LocalModel getLocalModel() {
        return localModel;
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

    }

    @Override
    public void mapVote(ArrayList<MapType> availableMaps) {

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
    public void showMessage(String message) {

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
    public void run() {
        launch(args);
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public List<Coordinates> getPathFromUser(int maxSteps) {

        return null;
    }
}

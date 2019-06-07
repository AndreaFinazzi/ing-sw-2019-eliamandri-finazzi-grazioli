package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class GUI extends Application implements RemoteView {

    String[] args;
    Stage primaryStage;


    private Client client;

    public GUI(String[] args, Client client) {
        this.args = args;
        this.client = client;
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
    public void login() {

    }

    @Override
    public void showPlayerMovement(String playerName, List<Coordinates> path) {

    }

    @Override
    public void showBeginTurn(BeginTurnEvent event) {

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
    public void choseAction() {

    }

    @Override
    public void selectWeaponCard() {

    }

    @Override
    public void updatePlayerPosition(String nickname, Coordinates coordinates) {

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
    public void run() {
        launch(args);
    }

}

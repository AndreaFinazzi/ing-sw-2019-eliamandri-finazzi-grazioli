package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
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


    private int clientID;

    public GUI(String[] args) {
        this.args = args;
    }

    @Override
    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
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
    public void showBeginTurn(BeginTurnEvent event) {

    }

    @Override
    public void showSelectableEvent(List<Coordinates> selectable) {

    }

    @Override
    public void run() {
        launch(args);
    }

}

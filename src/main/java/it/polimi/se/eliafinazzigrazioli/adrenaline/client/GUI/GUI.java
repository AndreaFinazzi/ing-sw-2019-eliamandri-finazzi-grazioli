package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class GUI extends Application implements RemoteView {

    public GUI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Adrenaline");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/GUI/fxml/login.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void showBeginTurn(BeginTurnEvent event) {

    }
}

package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

public class AbstractGUIController implements Initializable {
    protected GUI view;
    protected Semaphore semaphore;

    public AbstractGUIController() {

    }

    public void setView(GUI view) {
        this.view = view;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    AbstractGUIController loadFXML(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.load();
        AbstractGUIController guiController = loader.getController();
        guiController.setView(view);

        return guiController;
    }

    AbstractGUIController loadFXML(String path, Pane parent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.load();
        AbstractGUIController guiController = loader.getController();
        guiController.setView(view);

        Platform.runLater(() -> parent.getChildren().add(loader.getRoot()));

        return guiController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

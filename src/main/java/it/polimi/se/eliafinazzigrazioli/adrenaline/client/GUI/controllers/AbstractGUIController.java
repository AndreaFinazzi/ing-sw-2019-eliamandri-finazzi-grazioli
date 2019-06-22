package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class AbstractGUIController implements Initializable {
    static final Logger LOGGER = Logger.getLogger(AbstractGUIController.class.getName());

    protected boolean initialized = false;

    protected GUI view;
    protected Semaphore semaphore;

    public AbstractGUIController() {

    }

    public AbstractGUIController(GUI view) {
        this();
        this.view = view;
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

    Node loadFXML(String path, Pane parent, AbstractGUIController guiController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(guiController);
        loader.load();

        Platform.runLater(() -> parent.getChildren().add(loader.getRoot()));

        return loader.getRoot();
    }

    Node loadFXML(String path, AbstractGUIController guiController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(guiController);
        loader.load();

        return loader.getRoot();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

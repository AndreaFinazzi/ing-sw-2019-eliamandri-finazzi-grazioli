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

/**
 * The type Gui controller.
 */
public class GUIController implements Initializable {
    /**
     * The Logger.
     */
    static final Logger LOGGER = Logger.getLogger(GUIController.class.getName());

    /**
     * The Initialized.
     */
    protected boolean initialized = false;

    /**
     * The View.
     */
    protected GUI view;
    /**
     * The Semaphore.
     */
    protected Semaphore semaphore;

    /**
     * Instantiates a new Gui controller.
     */
    public GUIController() {

    }

    /**
     * Instantiates a new Gui controller.
     *
     * @param view the view
     */
    public GUIController(GUI view) {
        this();
        this.view = view;
    }

    /**
     * Sets view.
     *
     * @param view the view
     */
    public void setView(GUI view) {
        this.view = view;
    }

    /**
     * Sets semaphore.
     *
     * @param semaphore the semaphore
     */
    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    /**
     * Load fxml gui controller.
     *
     * @param path the path
     * @return the gui controller
     * @throws IOException the io exception
     */
    GUIController loadFXML(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(view.getResource(path));
        loader.load();
        GUIController guiController = loader.getController();
        guiController.setView(view);

        return guiController;
    }

    /**
     * Load fxml gui controller.
     *
     * @param path the path
     * @param parent the parent
     * @return the gui controller
     * @throws IOException the io exception
     */
    GUIController loadFXML(String path, Pane parent) throws IOException {
        FXMLLoader loader = new FXMLLoader(view.getResource(path));
        loader.load();
        GUIController guiController = loader.getController();
        guiController.setView(view);

        Platform.runLater(() -> parent.getChildren().add(loader.getRoot()));

        return guiController;
    }

    /**
     * Load fxml node.
     *
     * @param path the path
     * @param parent the parent
     * @param guiController the gui controller
     * @return the node
     * @throws IOException the io exception
     */
    Node loadFXML(String path, Pane parent, GUIController guiController) throws IOException {
        FXMLLoader loader = new FXMLLoader(view.getResource(path));
        loader.setController(guiController);
        loader.load();

        Platform.runLater(() -> parent.getChildren().add(loader.getRoot()));

        return loader.getRoot();
    }

    /**
     * Load fxml node.
     *
     * @param path the path
     * @param guiController the gui controller
     * @return the node
     * @throws IOException the io exception
     */
    Node loadFXML(String path, GUIController guiController) throws IOException {
        FXMLLoader loader = new FXMLLoader(view.getResource(path));
        loader.setController(guiController);
        loader.load();

        return loader.getRoot();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

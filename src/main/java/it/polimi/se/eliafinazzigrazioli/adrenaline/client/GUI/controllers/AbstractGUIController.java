package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import javafx.fxml.Initializable;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

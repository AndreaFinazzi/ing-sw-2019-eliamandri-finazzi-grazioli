package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AbstractGUIController implements Initializable {
    protected GUI view;

    public AbstractGUIController() {

    }

    public void setView(GUI view) {
        this.view = view;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

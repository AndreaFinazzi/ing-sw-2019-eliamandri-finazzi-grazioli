package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BoardSquareGUIController extends AbstractGUIController {

    public BoardSquareGUIController(GUI view) {
        super(view);
    }

    @FXML
    private ImageView ammoCard;

    private Map<String, Node> playersToNodeMap = new HashMap<>();


    public void setAmmo(AmmoCardClient ammoCard) {
        String uri = view.getAmmoAsset(ammoCard.getId());
        Platform.runLater(() -> this.ammoCard.setImage(new Image(uri)));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);


    }
}

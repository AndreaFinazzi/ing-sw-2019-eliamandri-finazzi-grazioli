package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerBoardGUIController extends AbstractGUIController {
    private static final String PLAYER_BOARD_STYLE_CLASS_DEFAULT = "playerBoard_NONE";
    private static final String PLAYER_BOARD_STYLE_CLASS_PREFIX = "playerBoard_";

    @FXML
    private GridPane playerBoardGridPane;

    private Avatar avatar;

    public PlayerBoardGUIController(GUI view, Avatar avatar) {
        super(view);
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        playerBoardGridPane.getStyleClass().remove(PLAYER_BOARD_STYLE_CLASS_DEFAULT);
        playerBoardGridPane.getStyleClass().add(PLAYER_BOARD_STYLE_CLASS_PREFIX + avatar.getDamageMark().name());

    }
}

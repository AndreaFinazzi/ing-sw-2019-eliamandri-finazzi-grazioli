package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class BoardSquareGUIController extends AbstractGUIController {

    @FXML
    private TilePane boardSquare;

    @FXML
    private ImageView ammoCardSlot;
    private List<String> players;

    public BoardSquareGUIController(GUI view) {
        super(view);
        players = new ArrayList<>();
    }


    public void setAmmoCard(AmmoCardClient ammoCard) {

        if (ammoCard != null) {
            String uri = view.getAmmoAsset(ammoCard.getId());
            Platform.runLater(() -> ammoCardSlot.setImage(new Image(uri)));
        } else
            Platform.runLater(() -> ammoCardSlot.setImage(null));
    }

    public synchronized Node addPlayer(String player) throws IOException {
        if (!hasPlayer(player)) {
            players.add(player);
            ImageView avatarNode = (ImageView) loadFXML(GUI.FXML_PATH_AVATAR, boardSquare, this);
            avatarNode.getProperties().put(GUI.PROPERTIES_KEY_AVATAR, player);

            String uri = view.getAvatarAsset(view.getLocalModel().getPlayersAvatarMap().get(player).getDamageMark().name());
            Platform.runLater(() -> avatarNode.setImage(new Image(uri)));
            return avatarNode;
        }

        return null;
    }

    public synchronized boolean removePlayer(String player) {

        Iterator<Node> childrenIterator = boardSquare.getChildren().iterator();

        while (childrenIterator.hasNext()) {
            Node child = childrenIterator.next();
            if (child.hasProperties() && child.getProperties().get(GUI.PROPERTIES_KEY_AVATAR).equals(player)) {
                Platform.runLater(childrenIterator::remove);
                players.remove(player);
                return true;
            }
        }
        return false;
    }

    public boolean hasPlayer(String player) {
        if (players.contains(player)) return true;
        return false;
    }

    public Node makeSelectable(EventHandler<MouseEvent> onMouseClicked) {
        Platform.runLater(() -> {
            boardSquare.getStyleClass().add(GUI.STYLE_CLASS_BOARD_SQUARE_SELECTABLE);
            boardSquare.setOnMouseClicked(onMouseClicked);
        });

        return boardSquare;
    }

    public void makeAvatarSelectable(Avatar avatar) {
        boardSquare.getChildren().forEach(item -> {
            if (item.hasProperties() && item.getProperties().get(GUI.PROPERTIES_KEY_AVATAR).equals(avatar)) {

            }
        });
    }

    public void makeUnselectable() {
        Platform.runLater(() -> {
            boardSquare.getStyleClass().remove(GUI.STYLE_CLASS_BOARD_SQUARE_SELECTABLE);
            boardSquare.setOnMouseClicked(null);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

    }
}

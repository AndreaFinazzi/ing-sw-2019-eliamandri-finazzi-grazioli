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

/**
 * The type Board square gui controller.
 */
public class BoardSquareGUIController extends GUIController {

    @FXML
    private TilePane boardSquare;

    @FXML
    private ImageView ammoCardSlot;
    private List<String> players;

    /**
     * Instantiates a new Board square gui controller.
     *
     * @param view the view
     */
    public BoardSquareGUIController(GUI view) {
        super(view);
        players = new ArrayList<>();
    }


    /**
     * Sets ammo card.
     *
     * @param ammoCard the ammo card
     */
    public void setAmmoCard(AmmoCardClient ammoCard) {

        if (ammoCard != null) {
            String uri = view.getAmmoAsset(ammoCard.getId());
            Platform.runLater(() -> ammoCardSlot.setImage(new Image(uri)));
        } else
            Platform.runLater(() -> ammoCardSlot.setImage(null));
    }

    /**
     * Add player node.
     *
     * @param player the player
     * @return the node
     * @throws IOException the io exception
     */
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

    /**
     * Remove player boolean.
     *
     * @param player the player
     * @return the boolean
     */
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

    /**
     * Has player boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public boolean hasPlayer(String player) {
        if (players.contains(player)) return true;
        return false;
    }

    /**
     * Make selectable node.
     *
     * @param onMouseClicked the on mouse clicked
     * @return the node
     */
    public Node makeSelectable(EventHandler<MouseEvent> onMouseClicked) {
        Platform.runLater(() -> {
            boardSquare.getStyleClass().add(GUI.STYLE_CLASS_BOARD_SQUARE_SELECTABLE);
            boardSquare.setOnMouseClicked(onMouseClicked);
        });

        return boardSquare;
    }

    /**
     * Make avatar selectable.
     *
     * @param avatar the avatar
     */
    public void makeAvatarSelectable(Avatar avatar) {
        boardSquare.getChildren().forEach(item -> {
            if (item.hasProperties() && item.getProperties().get(GUI.PROPERTIES_KEY_AVATAR).equals(avatar)) {

            }
        });
    }

    /**
     * Make unselectable.
     */
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

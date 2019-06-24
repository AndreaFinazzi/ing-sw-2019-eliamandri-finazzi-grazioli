package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.controllers;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class BoardSquareGUIController extends AbstractGUIController {

    public BoardSquareGUIController(GUI view) {
        super(view);
    }

    @FXML
    private TilePane boardSquare;
    @FXML
    private ImageView ammoCardSlot;


    public void setAmmo(AmmoCardClient ammoCard) {

        if (ammoCard != null) {
            String uri = view.getAmmoAsset(ammoCard.getId());
            Platform.runLater(() -> ammoCardSlot.setImage(new Image(uri)));
        } else
            Platform.runLater(() -> ammoCardSlot.setImage(null));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

    }

    public void addAvatar(Avatar avatar) throws IOException {
        ImageView avatarNode = (ImageView) loadFXML(GUI.FXML_PATH_AVATAR, boardSquare, this);
        avatarNode.getProperties().put(GUI.PROPERTIES_AVATAR_KEY, avatar);

        String uri = view.getAvatarAsset(avatar.getDamageMark().name());
        Platform.runLater(() -> avatarNode.setImage(new Image(uri)));
    }

    public boolean removeAvatar(Avatar avatar) {
        Iterator<Node> childrenIterator = boardSquare.getChildren().iterator();

        while (childrenIterator.hasNext()) {
            Node child = childrenIterator.next();
            if (child.hasProperties() && child.getProperties().get(GUI.PROPERTIES_AVATAR_KEY) == avatar) {
                Platform.runLater(childrenIterator::remove);
                return true;
            }
        }

        return false;
    }
}

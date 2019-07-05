package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.LocalModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;

import java.util.ArrayList;

/**
 * The type Login response event.
 */
public class LoginResponseEvent extends AbstractModelEvent {

    private boolean successful = false;
    private boolean reconnection = false;

    private ArrayList<Avatar> availableAvatars;

    private Avatar assignedAvatar;

    private LocalModel clientModel;

    /**
     * Instantiates a new Login response event.
     *
     * @param clientID the client id
     */
    public LoginResponseEvent(int clientID) {
        super(clientID);
        this.privateEvent = true;
    }

    /**
     * Is successful boolean.
     *
     * @return the boolean
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Sets success.
     *
     * @param successful the successful
     */
    public void setSuccess(boolean successful) {
        this.successful = successful;
    }

    /**
     * Sets player.
     *
     * @param playerName the player name
     */
    public void setPlayer(String playerName) {
        this.player = playerName;
    }

    /**
     * Sets available avatars.
     *
     * @param availableAvatars the available avatars
     */
    public void setAvailableAvatars(ArrayList<Avatar> availableAvatars) {
        this.availableAvatars = availableAvatars;
    }

    /**
     * Gets available avatars.
     *
     * @return the available avatars
     */
    public ArrayList<Avatar> getAvailableAvatars() {
        return availableAvatars;
    }

    /**
     * Gets assigned avatar.
     *
     * @return the assigned avatar
     */
    public Avatar getAssignedAvatar() {
        return assignedAvatar;
    }

    /**
     * Sets assigned avatar.
     *
     * @param assignedAvatar the assigned avatar
     */
    public void setAssignedAvatar(Avatar assignedAvatar) {
        this.assignedAvatar = assignedAvatar;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Is reconnection boolean.
     *
     * @return the boolean
     */
    public boolean isReconnection() {
        return reconnection;
    }

    /**
     * Sets reconnection.
     *
     * @param reconnection the reconnection
     */
    public void setReconnection(boolean reconnection) {
        this.reconnection = reconnection;
    }

    /**
     * Gets client model.
     *
     * @return the client model
     */
    public LocalModel getClientModel() {
        return clientModel;
    }

    /**
     * Sets client model.
     *
     * @param clientModel the client model
     */
    public void setClientModel(LocalModel clientModel) {
        this.clientModel = clientModel;
    }
}

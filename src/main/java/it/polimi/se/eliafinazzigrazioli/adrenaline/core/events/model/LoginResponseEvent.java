package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;

import java.util.ArrayList;

public class LoginResponseEvent extends AbstractModelEvent {

    private boolean successful = false;

    private ArrayList<Avatar> availableAvatars;

    private Avatar assignedAvatar;

    public LoginResponseEvent(int clientID) {
        super(clientID);
        this.privateEvent = true;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccess(boolean successful) {
        this.successful = successful;
    }

    public void setPlayer(String playerName) {
        this.player = playerName;
    }

    public void setAvailableAvatars(ArrayList<Avatar> availableAvatars) {
        this.availableAvatars = availableAvatars;
    }

    public ArrayList<Avatar> getAvailableAvatars() {
        return availableAvatars;
    }

    public Avatar getAssignedAvatar() {
        return assignedAvatar;
    }

    public void setAssignedAvatar(Avatar assignedAvatar) {
        this.assignedAvatar = assignedAvatar;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}

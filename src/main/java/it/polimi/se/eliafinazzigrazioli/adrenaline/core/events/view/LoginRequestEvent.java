package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;

public class LoginRequestEvent extends AbstractViewEvent {

    private Avatar chosenAvatar;

    public LoginRequestEvent(int clientID, String player, Avatar chosenAvatar) {
        super(player);
        this.clientID = clientID;
        this.chosenAvatar = chosenAvatar;
    }

    public Avatar getChosenAvatar() {
        return chosenAvatar;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}

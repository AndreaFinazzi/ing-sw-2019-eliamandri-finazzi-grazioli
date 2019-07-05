package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;

/**
 * The type Login request event.
 */
public class LoginRequestEvent extends AbstractViewEvent {

    private Avatar chosenAvatar;

    /**
     * Instantiates a new Login request event.
     *
     * @param clientID the client id
     * @param player the player
     * @param chosenAvatar the chosen avatar
     */
    public LoginRequestEvent(int clientID, String player, Avatar chosenAvatar) {
        super(clientID, player);
        this.chosenAvatar = chosenAvatar;
    }

    /**
     * Gets chosen avatar.
     *
     * @return the chosen avatar
     */
    public Avatar getChosenAvatar() {
        return chosenAvatar;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}

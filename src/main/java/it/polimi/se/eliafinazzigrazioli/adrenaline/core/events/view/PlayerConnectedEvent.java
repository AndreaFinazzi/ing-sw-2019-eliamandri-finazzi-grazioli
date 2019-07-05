package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type Player connected event.
 */
public class PlayerConnectedEvent extends AbstractViewEvent {

    private int chosenMap;
    private String avatar;

    /**
     * Instantiates a new Player connected event.
     *
     * @param clientID the client id
     * @param player the player
     */
    public PlayerConnectedEvent(int clientID, String player) {
        super(clientID, player);
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets chosen map.
     *
     * @return the chosen map
     */
    public int getChosenMap() {
        return chosenMap;
    }

    /**
     * Sets chosen map.
     *
     * @param chosenMap the chosen map
     */
    public void setChosenMap(int chosenMap) {
        this.chosenMap = chosenMap;
    }

    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Sets avatar.
     *
     * @param avatar the avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}


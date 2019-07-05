package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type Card selected event.
 */
public class CardSelectedEvent extends AbstractViewEvent {

    private String card;

    /**
     * Instantiates a new Card selected event.
     *
     * @param clientID the client id
     * @param player the player
     * @param card the card
     */
    public CardSelectedEvent(int clientID, String player, String card) {
        super(clientID, player);
        this.card = card;
    }

    /**
     * Gets card.
     *
     * @return the card
     */
    public String getCard() {
        return card;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}

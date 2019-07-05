package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.AmmoCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

/**
 * Is to be generated only if the last square of the path is a generic square. The different case in which a weapon has to
 * be collected must be managed by the client.
 */
public class AmmoCardCollectedEvent extends AbstractModelEvent {

    private AmmoCardClient ammoCardCollected;
    private Coordinates boardSquare;

    /**
     * Instantiates a new Ammo card collected event.
     *
     * @param player the player
     * @param ammoCardCollected the ammo card collected
     * @param boardSquare the board square
     */
    public AmmoCardCollectedEvent(Player player, AmmoCard ammoCardCollected, Coordinates boardSquare) {
        super(player);
        this.ammoCardCollected = new AmmoCardClient(ammoCardCollected);
        this.boardSquare = boardSquare;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets ammo card collected.
     *
     * @return the ammo card collected
     */
    public AmmoCardClient getAmmoCardCollected() {
        return ammoCardCollected;
    }

    /**
     * Gets board square.
     *
     * @return the board square
     */
    public Coordinates getBoardSquare() {
        return boardSquare;
    }
}

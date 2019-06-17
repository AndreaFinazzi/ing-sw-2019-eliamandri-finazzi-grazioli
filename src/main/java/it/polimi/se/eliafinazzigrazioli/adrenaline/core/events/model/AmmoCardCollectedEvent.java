package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.AmmoCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

/**
 * Is to be generated only if the last square of the path is a generic square. The different case in which a weapon has to
 * be collected must be managed by the client.
 */
public class AmmoCardCollectedEvent extends AbstractModelEvent {

    private AmmoCard ammoCardCollected;
    private Coordinates boardSquare;

    public AmmoCardCollectedEvent(Player player, AmmoCard ammoCardCollected, Coordinates boardSquare) {
        super(player);
        this.ammoCardCollected = ammoCardCollected;
        this.boardSquare = boardSquare;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public AmmoCard getAmmoCardCollected() {
        return ammoCardCollected;
    }

    public Coordinates getBoardSquare() {
        return boardSquare;
    }
}

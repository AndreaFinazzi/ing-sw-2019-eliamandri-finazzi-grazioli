package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Spawn selection request event.
 */
public class SpawnSelectionRequestEvent extends AbstractModelEvent {

    /**
     * The Selectable cards.
     */
    List<PowerUpCardClient> selectableCards;
    /**
     * The First spawn.
     */
    boolean firstSpawn;

    /**
     * Instantiates a new Spawn selection request event.
     *
     * @param player the player
     * @param selectableCards the selectable cards
     * @param firstSpawn the first spawn
     */
    public SpawnSelectionRequestEvent(Player player, List<PowerUpCard> selectableCards, boolean firstSpawn) {
        super(true, true, player);
        this.selectableCards = new ArrayList<>();
        for (PowerUpCard powerUpCard: selectableCards)
            this.selectableCards.add(new PowerUpCardClient(powerUpCard));
        this.firstSpawn = firstSpawn;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets selectable cards.
     *
     * @return the selectable cards
     */
    public List<PowerUpCardClient> getSelectableCards() {
        return selectableCards;
    }

    /**
     * Is first spawn boolean.
     *
     * @return the boolean
     */
    public boolean isFirstSpawn() {
        return firstSpawn;
    }
}

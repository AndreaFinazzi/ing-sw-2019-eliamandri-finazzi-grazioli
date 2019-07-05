package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;

/**
 * The type Selected map event.
 */
public class SelectedMapEvent extends AbstractModelEvent {

    private MapType mapType;

    /**
     * Instantiates a new Selected map event.
     *
     * @param player the player
     * @param mapType the map type
     */
    public SelectedMapEvent(String player, MapType mapType) {
        super(player);
        privateEvent = false;
        this.mapType = mapType;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets map type.
     *
     * @return the map type
     */
    public MapType getMapType() {
        return mapType;
    }
}

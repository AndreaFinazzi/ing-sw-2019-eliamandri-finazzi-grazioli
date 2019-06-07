package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;

public class SelectedMapEvent extends AbstractModelEvent {

    private MapType mapType;

    public SelectedMapEvent(String player, MapType mapType) {
        super(player);
        privateEvent = false;
        this.mapType = mapType;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public MapType getMapType() {
        return mapType;
    }
}

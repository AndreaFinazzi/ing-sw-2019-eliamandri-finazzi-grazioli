package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;

import java.util.Map;

public class BeginMatchEvent extends AbstractModelEvent {

    private MapType mapType;

    private Map<String, Avatar> playerToAvatr;

    public BeginMatchEvent(MapType mapType) {
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

package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;

public abstract class AbstractModelEvent extends AbstractEvent {
    public abstract String getPlayer();
}

package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;

public interface Observer {

    void update(AbstractEvent event);
}
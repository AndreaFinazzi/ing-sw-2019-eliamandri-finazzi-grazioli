package it.polimi.se.eliafinazzigrazioli.adrenaline.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;

public interface Observer {

    void update(AbstractEvent event);
}
package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;

public abstract class AbstractViewEvent extends AbstractEvent {
    abstract String getPlayer();
}

package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

/**
 * The interface Observer.
 */
public interface Observer {

    /**
     * Update.
     *
     * @param event the event
     */
    void update(AbstractViewEvent event);

    /**
     * Update.
     *
     * @param event the event
     */
    void update(AbstractModelEvent event);
}
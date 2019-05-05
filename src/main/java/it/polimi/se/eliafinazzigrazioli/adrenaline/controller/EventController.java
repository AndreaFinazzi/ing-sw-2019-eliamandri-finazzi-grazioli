package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.ListenerNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EventController implements Observer {
    private HashMap<Class<? extends AbstractViewEvent>, ArrayList<EventListenerInterface>> listenerMap;
    private MatchController matchController;

    private static final Logger LOGGER = Logger.getLogger(EventController.class.getName());

    public EventController(MatchController matchController) {
        listenerMap = new HashMap<>();
        this.matchController = matchController;
    }

    @Override
    public void update(AbstractEvent event) {
        ArrayList<EventListenerInterface> listeners = listenerMap.get(event.getClass());

        if (listeners == null) return;

        listeners.forEach(listener -> {
                    try {
                        event.handle(listener, matchController);
                    } catch (HandlerNotImplementedException e) {
                        LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
        );
    }

    public void addEventListener(Class<? extends AbstractViewEvent> key, EventListenerInterface value) {
        ArrayList<EventListenerInterface> listeners = listenerMap.get(key);

        if (listeners != null) {
            listeners.add(value);
        } else {
            listeners = new ArrayList<EventListenerInterface>();
            listeners.add(value);
            listenerMap.put(key, listeners);
        }
    }

    public void removeEventListener(Class<? extends AbstractViewEvent> key, EventListenerInterface value) throws ListenerNotFoundException {
        ArrayList<EventListenerInterface> listeners = listenerMap.get(key);
        if (!listeners.remove(value)) throw new ListenerNotFoundException();
    }
}
